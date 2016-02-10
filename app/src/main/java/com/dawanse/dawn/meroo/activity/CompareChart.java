package com.dawanse.dawn.meroo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.dawanse.dawn.meroo.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class CompareChart extends AppCompatActivity {

    private FrameLayout chartFrame;
    private PieChart mChart;

    //raw data
    private float[] yData = {5, 10, 15, 30, 40};
    private String[] xData = {"Huawei", "Sony", "Nexus", "Samsung", "Apple"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_chart);

        chartFrame = (FrameLayout) findViewById(R.id.chartFrame);
        mChart = new PieChart(this);

        //add pie chart to layout
        chartFrame.addView(mChart);
        chartFrame.setBackgroundColor(Color.LTGRAY);

        //configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setDescription("Smartphone market share");

        //enable hole and configure
        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColorTransparent(true);
        mChart.setHoleRadius(7);
        mChart.setTransparentCircleRadius(10);

        //enable rotation of chart by touch
        mChart.setRotationEnabled(true);
        mChart.setRotation(0);

        //set chart value selected listener
        mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, int i, Highlight highlight) {
                //display msg when value selected
                if (entry == null)
                    return;
                Toast.makeText(CompareChart.this,
                        xData[entry.getXIndex()] + " = " + entry.getVal() + "%",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                // add data
                addData();
            }
        });

        //customize legends

        Legend legend = mChart.getLegend();
        legend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        legend.setXEntrySpace(7);
        legend.setYEntrySpace(5);

    }

    private void addData(){
        ArrayList<Entry> yVals = new ArrayList<Entry>();
        for (int i = 0; i < yData.length; i++){
            yVals.add(new Entry(yData[i], i));
        }

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < xData.length; i++){
            xVals.add(xData[i]);
        }

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals, "Market Share");
        dataSet.setSliceSpace(3);
        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        //instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        //undo all highlights
        mChart.highlightValue(null);

        //update pie chart
        mChart.invalidate();


    }
}
