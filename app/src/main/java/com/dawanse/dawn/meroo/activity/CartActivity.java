package com.dawanse.dawn.meroo.activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.adapter.CartCursorAdapter;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;
import com.dawanse.dawn.meroo.notification.NotificationReceiver;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CartActivity extends AppCompatActivity {

    EditText etCartItem;
    Button buttonAddToCart, buttonCartDate;
    ListView listViewCart;
    CartCursorAdapter mCartCursorAdapter;
    Calendar myCalendar;

    ExpenseDB mExpenseDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        etCartItem = (EditText) findViewById(R.id.etCartItem);
        buttonAddToCart = (Button) findViewById(R.id.buttonAddToCart);
        buttonCartDate = (Button) findViewById(R.id.buttonDatePicker);
        listViewCart = (ListView) findViewById(R.id.listViewCart);

        mExpenseDB = new ExpenseDB(this);
        mCartCursorAdapter = new CartCursorAdapter(this, mExpenseDB.getCartData());
        listViewCart.setAdapter(mCartCursorAdapter);

        listViewCart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                TextView tci = (TextView) view.findViewById(R.id.tvCartId);
                final int item_id = Integer.parseInt(tci.getText().toString());

                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(CartActivity.this);
                mBuilder.setMessage("Choose your action!!!");
                mBuilder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mExpenseDB.deleteCartRow(item_id);
                        mCartCursorAdapter.changeCursor(mExpenseDB.getCartData());
                        mCartCursorAdapter.notifyDataSetChanged();
                        listViewCart.setAdapter(mCartCursorAdapter);
                    }
                });

                mBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                mBuilder.create().show();
            }
        });

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                myCalendar.set(Calendar.MINUTE, minute);
                updateLabel();
            }
        };

        buttonCartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(CartActivity.this, time, myCalendar.get(Calendar.HOUR_OF_DAY),
                        myCalendar.get(Calendar.MINUTE), true).show();
                new DatePickerDialog(CartActivity.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String itemCart = etCartItem.getText().toString();
                String dateCart = buttonCartDate.getText().toString();

                if (itemCart.length() != 0 && dateCart.length() != 0) {
                    try {
                        mExpenseDB.open();
                        mExpenseDB.createCart(itemCart, dateCart);
                        mCartCursorAdapter.changeCursor(mExpenseDB.getCartData());
                        mExpenseDB.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        Toast.makeText(CartActivity.this, "Item saved.", Toast.LENGTH_SHORT).show();
                        //notification
                        buildNotification();
                        etCartItem.setText(null);
                        buttonCartDate.setText(null);
                    }
                } else {
                    Toast.makeText(CartActivity.this, "Item field is empty. Enter all item to proceed.", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void updateLabel() {
        //date format
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy HH:mm:ss");
        buttonCartDate.setText(sdf.format(myCalendar.getTimeInMillis()));
    }

    private void buildNotification(){

        Intent intent = new Intent(CartActivity.this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CartActivity.this, 0, intent, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, myCalendar.getTimeInMillis(), pendingIntent);

    }
}