package com.example.listviewrecyclervier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TruongHop5_ThumbnailAdapter  extends ArrayAdapter<TruongHop5_Thumbnail> {
    public TruongHop5_ThumbnailAdapter(Activity context, TruongHop5_Thumbnail[] thumbnails) {
        super(context, android.R.layout.simple_spinner_item, thumbnails);
    }




    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.truonghop5_thumbnail, parent, false);

        TruongHop5_Thumbnail thumbnail = getItem(position);
        ImageView imageView = convertView.findViewById(R.id.thumbnail_image);
        TextView textView = convertView.findViewById(R.id.thumbnail_name);


        imageView.setImageResource(thumbnail.getImg());
        textView.setText(thumbnail.getName());

        textView.setSelected(true);

        return convertView;
    }
}