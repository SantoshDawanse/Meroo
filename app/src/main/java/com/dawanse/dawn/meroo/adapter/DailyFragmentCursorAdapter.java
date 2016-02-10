package com.dawanse.dawn.meroo.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;
import com.dawanse.dawn.meroo.fragments.DailyFragment;

public class DailyFragmentCursorAdapter extends CursorAdapter {

    private Context mContext;
    private DailyFragment mDailyFragment;
    private ExpenseDB mExpenseDB;

    public static final String PREF_NAME = "user_settings";

    public DailyFragmentCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
        mExpenseDB = new ExpenseDB(context);
        mDailyFragment = new DailyFragment();
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.custom_daily_frag_row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvYear = (TextView) view.findViewById(R.id.tvYear);
        TextView tvMonth = (TextView) view.findViewById(R.id.tvMonth);
        TextView tvPrice = (TextView) view.findViewById(R.id.tvPrice);

        tvMonth.setText(cursor.getString(cursor.getColumnIndexOrThrow("daily_date")));
        tvPrice.setText(cursor.getString(cursor.getColumnIndexOrThrow("daily_total")));

        /*if(compareValue() > mExpenseDB.getTotalTodayFragmentData()){
            tvPrice.setTextColor(view.getResources().getColor(R.color.below_average));
        }else {
            tvPrice.setTextColor(view.getResources().getColor(R.color.above_average));
        }*/

    }

    /*public int compareValue(){
        SharedPreferences mPreferences = mContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int e = mPreferences.getInt("expense", 0);
        return e / 30;
    }*/
}
