package com.example.android.networkconnect.home.home.view.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.networkconnect.R;
import com.example.android.networkconnect.home.home.model.RickAndMortyCharacter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kevin Morales on 2/27/23.
 */
public class RickAndMortyAdapter extends BaseAdapter {

    private Activity activity;
    private ArrayList<RickAndMortyCharacter> itemList;

    public RickAndMortyAdapter(Activity activity, ArrayList<RickAndMortyCharacter> itemList) {
        super();
        this.activity = activity;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        int count = 0;

        if (itemList.size() > 0) {
            count = itemList.size();
        }

        return count;
    }

    @Override
    public Object getItem(int i) {
        return itemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ItemRow row = new ItemRow();
        LayoutInflater inflater = activity.getLayoutInflater();

        if (view == null) {
            view = inflater.inflate(R.layout.rick_and_morty_item, viewGroup, false);
            row.imageChar = (ImageView) view.findViewById(R.id.image_avatar_id);
            row.nameChar = (TextView) view.findViewById(R.id.name_avatar_id);

            view.setTag(row);

        } else {
            row = (ItemRow) view.getTag();
        }

        RickAndMortyCharacter item = itemList.get(i);

        if (item != null) {
            Picasso.get()
                    .load(item.getImageUrl())
                    .resize(100,100)
                    .centerCrop()
                    .into(row.imageChar);
            row.nameChar.setText(item.getName());
        }

        return view;
    }

    public static class ItemRow {
        ImageView imageChar;
        TextView nameChar;
    }
}

