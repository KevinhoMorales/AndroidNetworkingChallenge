package com.example.android.networkconnect.home.home.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.support.v4.app.FragmentActivity;
import android.widget.ListView;

import com.example.android.networkconnect.home.home.model.RickAndMortyCharacter;
import com.example.android.networkconnect.home.home.view.adapter.RickAndMortyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kevin Morales on 2/28/23.
 */
public class HomeViewModel extends ViewModel {
    public ListView rickAndMortyList;

    public RickAndMortyAdapter fetchDataByParseObject(String json, FragmentActivity view) {
        ArrayList<RickAndMortyCharacter> characterArrayList = new ArrayList<RickAndMortyCharacter>();
        try {
            JSONObject objectResponse = new JSONObject(json);
            JSONArray charactersArrayJson = objectResponse.getJSONArray("results");
            for (int i = 0; i < charactersArrayJson.length(); i++) {
                JSONObject itemObject = charactersArrayJson.getJSONObject(i);
                RickAndMortyCharacter rmCharacter = new RickAndMortyCharacter();
                rmCharacter.setImageUrl(itemObject.getString("image"));
                rmCharacter.setName(itemObject.getString("name"));
                characterArrayList.add(rmCharacter);
            }

            RickAndMortyAdapter adapter = new RickAndMortyAdapter(view, characterArrayList);
            return adapter;
        } catch (JSONException e) {
            RickAndMortyAdapter adapter = new RickAndMortyAdapter(view, characterArrayList);
            return adapter;
        }
    }
}
