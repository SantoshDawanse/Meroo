package com.dawanse.dawn.meroo.adapter;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;

public class MonthlyFragmentCursorAdapter extends CursorAdapter {
    public MonthlyFragmentCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.custom_monthly_fragment_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvYear = (TextView) view.findViewById(R.id.tvYear);
        TextView tvMonth = (TextView) view.findViewById(R.id.tvMonth);
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);

//        tvYear.setText(cursor.getString(cursor.getColumnIndexOrThrow("")));
//        tvMonth.setText(cursor.getString(cursor.getColumnIndexOrThrow("")));
//        tvPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow("")));

    }
}
