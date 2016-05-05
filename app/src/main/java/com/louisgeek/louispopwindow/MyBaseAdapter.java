package com.louisgeek.louispopwindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by louisgeek on 2016/5/5.
 */
public class MyBaseAdapter extends BaseAdapter {
    List<String> stringList;
    Context context;
    public MyBaseAdapter(List<String> stringList,Context context) {
        this.stringList = stringList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

            View  view = LayoutInflater.from(context).inflate(R.layout.grid_item, null, false);
            TextView id_tv = ButterKnife.findById(view, R.id.id_tv);
            id_tv.setText(stringList.get(position).toString());


        return view;
    }
}
