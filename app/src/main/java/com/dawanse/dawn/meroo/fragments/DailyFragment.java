package com.dawanse.dawn.meroo.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.adapter.DailyFragmentCursorAdapter;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;

public class DailyFragment extends Fragment {
    ListView mListView;
    ExpenseDB mExpenseDB;
    DailyFragmentCursorAdapter mDailyFragmentCursorAdapter;

    public DailyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExpenseDB = new ExpenseDB(getContext());
        mExpenseDB.createDailyFrag();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily, container, false);
        mListView = (ListView) view.findViewById(R.id.listView);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                mDailyFragmentCursorAdapter = new DailyFragmentCursorAdapter(getContext(), mExpenseDB.getDailyFragmentData());
                mDailyFragmentCursorAdapter.notifyDataSetChanged();
                mListView.setAdapter(mDailyFragmentCursorAdapter);
                mExpenseDB.close();
            }
        });
        return view;
    }

}
