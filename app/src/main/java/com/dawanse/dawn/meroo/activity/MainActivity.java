package com.dawanse.dawn.meroo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.adapter.ExpenseCursorAdapter;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView mDay, mWeekDay, mMonth, mYear;
    ListView listView;

    ExpenseDB mExpenseDB;
    ExpenseCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Under Construction", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                startActivity(new Intent(MainActivity.this, AddActivity.class));
            }
        });

        mExpenseDB = new ExpenseDB(this);

        mDay = (TextView) findViewById(R.id.tvDay);
        mWeekDay = (TextView) findViewById(R.id.tvWeekDay);
        mMonth = (TextView) findViewById(R.id.tvMonth);
        mYear = (TextView) findViewById(R.id.tvYear);

        listView = (ListView) findViewById(R.id.listView);
        listView.setLongClickable(true);
        listView.isSmoothScrollbarEnabled();

        final Calendar calendar = Calendar.getInstance();

        mDay.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        mWeekDay.setText(String.valueOf(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)));
        mMonth.setText(String.valueOf(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)));
        mYear.setText(String.valueOf(calendar.get(Calendar.YEAR)));


        new Handler().post(new Runnable() {
            @Override
            public void run() {
                listView.invalidateViews();
                mCursorAdapter = new ExpenseCursorAdapter(MainActivity.this, mExpenseDB.getAllData());
                listView.setAdapter(mCursorAdapter);
                listView.isSmoothScrollbarEnabled();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //new added lines
                TextView tvPrice = (TextView) view.findViewById(R.id.tvId);
                final int rid = Integer.parseInt(tvPrice.getText().toString());

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                mBuilder.setMessage("Are you sure to delete?");
                mBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //delete action here
                        mExpenseDB.delete(rid);
                        mCursorAdapter.changeCursor(mExpenseDB.getAllData());
                        mCursorAdapter.notifyDataSetInvalidated();
                        mCursorAdapter.notifyDataSetChanged();
                        listView.setAdapter(mCursorAdapter);
                    }
                });
                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // cancel action here
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = mBuilder.create();
                dialog.show();

                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //handling click events
        switch (item.getItemId()) {
            case R.id.menu_cart:
                startActivity(new Intent(this, CartActivity.class));
                return true;

            case R.id.menu_total:
                startActivity(new Intent(this, TotalActivity.class));
                return true;

            case R.id.menu_chart:
                startActivity(new Intent(this, CompareChart.class));
                return true;

            case R.id.menu_bar_chart:
                startActivity(new Intent(this, ShowChart.class));
                return true;

            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
