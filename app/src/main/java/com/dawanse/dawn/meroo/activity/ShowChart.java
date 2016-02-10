package com.dawanse.dawn.meroo.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ShowChart extends AppCompatActivity {

    private ExpenseDB mExpenseDB;

    private FrameLayout frameChart;
    private BarChart barChart;

    public static final String PREF_NAME = "user_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_chart);

        mExpenseDB = new ExpenseDB(this);

        frameChart = (FrameLayout) findViewById(R.id.frameChart);
        barChart = new BarChart(this);

        frameChart.addView(barChart);
        frameChart.setBackgroundColor(Color.LTGRAY);

        addData();

    }

    private void addData(){

        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();

        for (int i = 0; i < mExpenseDB.queryYData().size(); i++)
            yVals.add(new BarEntry(mExpenseDB.queryYData().get(i), i));

        ArrayList<String> xVals = new ArrayList<String>();
        for(int i = 0; i < mExpenseDB.queryXData().size(); i++)
            xVals.add(mExpenseDB.queryXData().get(i));

        BarDataSet dataSet = new BarDataSet(yVals, "expense values");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData data = new BarData(xVals, dataSet);

        SharedPreferences mSharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        float limitdata = mSharedPreferences.getInt("expense", 0) / 30;
        LimitLine line = new LimitLine(limitdata, "average daily expense");
        line.setTextSize(12f);
        line.setLineWidth(4f);
        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.addLimitLine(line);

        barChart.setData(data);
        barChart.setDescription("The expenses chart.");
        barChart.animateY(2000);

    }

}
