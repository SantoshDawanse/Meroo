package com.dawanse.dawn.meroo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.adapter.ExpenseCursorAdapter;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;
import com.dawanse.dawn.meroo.fragments.ChartFragment;
import com.dawanse.dawn.meroo.fragments.MainFragment;
import com.dawanse.dawn.meroo.fragments.SettingsFragment;
import com.dawanse.dawn.meroo.fragments.ShoppingFragment;
import com.dawanse.dawn.meroo.fragments.TotalFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView mDay, mWeekDay, mMonth, mYear;
    ListView listView;

    ExpenseDB mExpenseDB;
    ExpenseCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
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

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set fragment initally
        MainFragment fragment = new MainFragment();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_container, fragment);
        fragmentTransaction.commit();

        /*mExpenseDB = new ExpenseDB(this);

        mDay = (TextView) findViewById(R.id.tvDay);
        mWeekDay = (TextView) findViewById(R.id.tvWeekDay);
        mMonth = (TextView) findViewById(R.id.tvMonth);
        mYear = (TextView) findViewById(R.id.tvYear);

        listView = (ListView) findViewById(R.id.listView);
//        listView.setLongClickable(true);
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
        });*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment = new MainFragment();
        if (id == R.id.pocket_manager) {
            fragment = new MainFragment();
            /*android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();*/
        } else if (id == R.id.total_review) {
            fragment = new TotalFragment();
            /*android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();*/
        } else if (id == R.id.compare) {
            fragment = new ChartFragment();
            /*android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();*/
        } else if (id == R.id.shopping_list) {
            fragment = new ShoppingFragment();
            /*android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();*/
        } else if (id == R.id.settings) {
            fragment = new SettingsFragment();
            /*android.support.v4.app.FragmentTransaction fragmentTransaction =
                    getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_container, fragment);
            fragmentTransaction.commit();*/
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
