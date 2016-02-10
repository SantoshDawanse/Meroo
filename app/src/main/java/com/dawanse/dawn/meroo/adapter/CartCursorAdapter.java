package com.dawanse.dawn.meroo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;

public class CartCursorAdapter extends CursorAdapter {


    public CartCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.custom_cart_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvCartId = (TextView) view.findViewById(R.id.tvCartId);
        TextView tvCartItem = (TextView) view.findViewById(R.id.tvCartItem);
        TextView tvCartDate = (TextView) view.findViewById(R.id.tvCartDate);

        tvCartId.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        tvCartItem.setText(cursor.getString(cursor.getColumnIndexOrThrow("cart_item")));
        tvCartDate.setText(cursor.getString(cursor.getColumnIndexOrThrow("cart_date")));

    }
}
