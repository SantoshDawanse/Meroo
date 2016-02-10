package com.dawanse.dawn.meroo.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.activity.SettingsActivity;
import com.dawanse.dawn.meroo.adapter.TodayFragmentCursorAdapter;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;

public class TodayFragment extends Fragment {

    ListView mListView;
    TodayFragmentCursorAdapter mTodayFragmentCursorAdapter;
    ExpenseDB mExpenseDB;
    SettingsActivity mSettings;

    TextView tvShowTotal;

    public static final String PREF_NAME = "user_settings";

    public TodayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExpenseDB = new ExpenseDB(getContext());
        mSettings = new SettingsActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today, container, false);
        mListView = (ListView) view.findViewById(R.id.todayListView);
        tvShowTotal = (TextView) view.findViewById(R.id.tvShow);

        tvShowTotal.setText("Rs " + mExpenseDB.getTotalTodayFragmentData());
        if(compareValue() > mExpenseDB.getTotalTodayFragmentData()){
            tvShowTotal.setTextColor(getResources().getColor(R.color.below_average));
        }else{
            tvShowTotal.setTextColor(getResources().getColor(R.color.above_average));
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mTodayFragmentCursorAdapter = new TodayFragmentCursorAdapter(getContext(), mExpenseDB.getTodayFragmentData());
                mTodayFragmentCursorAdapter.notifyDataSetChanged();
                mListView.setAdapter(mTodayFragmentCursorAdapter);
            }
        });

        return view;
    }

    private int compareValue(){
        SharedPreferences mPreferences = getContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int e = mPreferences.getInt("income", 0);
        return e / 30;
    }

}
