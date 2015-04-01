package com.cuca.example;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ImagesAdapter extends BaseAdapter {

    ArrayList<ImageItem> imageItems;
    LayoutInflater inflater;

    public ImagesAdapter(Context context, ArrayList<ImageItem> imageItems){
        this.imageItems = imageItems;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {

        if(imageItems != null){
            return imageItems.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        return imageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = inflater.inflate(R.layout.image_item, null);
        }

        ImageItem imageItem = imageItems.get(position);

        TextView text = (TextView) convertView.findViewById(R.id.text);
        ImageView image = (ImageView) convertView.findViewById(R.id.imageView);

        image.setImageBitmap(null);

        text.setText(imageItem.getText());
        ImageLoader.getInstance().displayImage(imageItem.getImageURL(), image);

        return convertView;
    }
}
