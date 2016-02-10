package com.dawanse.dawn.meroo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;

public class ExpenseCursorAdapter extends CursorAdapter {
    public ExpenseCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        return inflater.inflate(R.layout.custom_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //for main activity, i.e for showing in main activity xml
        TextView tvId = (TextView) view.findViewById(R.id.tvId);
        TextView tvDate = (TextView) view.findViewById(R.id.tvDate);
        TextView tvDetails = (TextView) view.findViewById(R.id.tvDetails);
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);


        tvId.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        tvDate.setText(cursor.getString(cursor.getColumnIndexOrThrow("enter_date")));
        tvDetails.setText(cursor.getString(cursor.getColumnIndexOrThrow("item")));
        tvPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow("price")));

    }


}

