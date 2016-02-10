package com.dawanse.dawn.meroo.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;


public class MonthlyFragment extends Fragment {

    ListView mListView;
    TextView mTextView;

    ExpenseDB mExpenseDB;

    public MonthlyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mExpenseDB = new ExpenseDB(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_monthly, container, false);
        mListView = (ListView) view.findViewById(R.id.monthlyListView);
        mTextView = (TextView) view.findViewById(R.id.tvPrice);

        mTextView.setText("Rs " + "\n" + mExpenseDB.getTotalMonthlyFragmentData());
        return view;
    }


}
