package com.dawanse.dawn.meroo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dawanse.dawn.meroo.R;
import com.dawanse.dawn.meroo.adapter.ExpenseCursorAdapter;
import com.dawanse.dawn.meroo.dbhelper.ExpenseDB;

import java.sql.SQLException;

public class AddActivity extends AppCompatActivity {

    EditText etItem, etPrice;
    Button buttonSave;

    ExpenseDB mExpenseDB;
    ExpenseCursorAdapter mCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etItem = (EditText) findViewById(R.id.etItem);
        etPrice = (EditText) findViewById(R.id.etPrice);
        buttonSave = (Button) findViewById(R.id.buttonAdd);

        mExpenseDB = new ExpenseDB(this);
        mCursorAdapter = new ExpenseCursorAdapter(AddActivity.this, mExpenseDB.getAllData());

    }

    public void onClickAdd(View btnAdd) {
        String item = etItem.getText().toString();
        String price = etPrice.getText().toString();

        if (item.length() != 0 && price.length() != 0) {
            if (item.length() < 20 && price.length() < 6) {

                try {
                    mExpenseDB.open();
                    mExpenseDB.createEntry(item, Float.parseFloat(price));
                    mExpenseDB.close();
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setClass(this, MainActivity.class);
                    startActivity(intent);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                if (price.length() > 5) {
                    Toast.makeText(AddActivity.this, "Don't waste too much money at a time.",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(AddActivity.this, "Out of limit text, type short as much as possible. Text limit is 20 characters.",
                            Toast.LENGTH_LONG).show();
                }
                etItem.setText(null);
                etPrice.setText(null);
            }
        } else {
            Toast.makeText(AddActivity.this, "enter both fields to proceed.", Toast.LENGTH_SHORT).show();
        }
    }

}
