package com.dawanse.dawn.meroo.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;

public class TodayFragmentCursorAdapter extends CursorAdapter {
    public TodayFragmentCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.custom_today_fragment_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView todayId = (TextView) view.findViewById(R.id.todayFragId);
        TextView todayItem = (TextView) view.findViewById(R.id.todayFragItem);
        TextView todayPrice = (TextView) view.findViewById(R.id.todayFragPrice);

//        todayId.setText(cursor.getString(cursor.getColumnIndexOrThrow(cursor.getColumnName(0))));
        todayItem.setText(cursor.getString(cursor.getColumnIndexOrThrow("item")));
        todayPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow("price")));
    }
}
