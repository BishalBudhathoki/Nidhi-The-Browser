package com.erbisdev.mbrow;

/**
 * Created by ER.Bishal on 2/22/2018.
 */

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapterModel extends ArrayAdapter<String>{
    int groupid;
    ArrayList<String> titles;
    ArrayList<String> urls;
    ArrayList<Bitmap> bitmaps;
    Context context;
    String path;
    public ListAdapterModel(Context context, int vg, int id, ArrayList<String> titles,ArrayList<String> urls,ArrayList<Bitmap> bitmaps){
        super(context,vg, id, titles);
        this.context=context;
        groupid=vg;
        this.titles=titles;
        this.urls=urls;
        this.bitmaps=bitmaps;

    }
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(groupid, parent, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.icon);
        imageView.setImageBitmap(bitmaps.get(position));
        TextView textTitle= (TextView) itemView.findViewById(R.id.hbtitle);
        String title=titles.get(position);
        textTitle.setText(title);
        TextView textURL = (TextView) itemView.findViewById(R.id.hburl);
        String url=urls.get(position);
        textURL.setText(url);
        //make the url clickable
        Linkify.addLinks(textURL, Linkify.ALL);
        return itemView;
    }


}

