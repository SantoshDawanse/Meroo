package com.dawanse.dawn.meroo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dawanse.dawn.meroo.R;

public class SettingsActivity extends AppCompatActivity {
    Spinner mSpinner;
    CheckBox mNotifyCheck;
    EditText etMonthlyIncome, etMonthlyExpense, etCriticalExpense;
    TextView tvRemainingExpense;
    Button buttonSave, buttonClear;

    Button incomeBtn, expenseBtn, criticalBtn;

    int compareExpense;

    public static final String PREF_NAME = "user_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //invisible type buttons
        incomeBtn = (Button) findViewById(R.id.incomeBtn);
        expenseBtn = (Button) findViewById(R.id.expenseBtn);
        criticalBtn = (Button) findViewById(R.id.criticalBtn);

        etMonthlyIncome = (EditText) findViewById(R.id.etMonthlyIncome);
        etMonthlyExpense = (EditText) findViewById(R.id.etMonthlyExpense);
        etCriticalExpense = (EditText) findViewById(R.id.etCriticalExpense);
        tvRemainingExpense = (TextView) findViewById(R.id.tvRemainingExpense);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonClear = (Button) findViewById(R.id.buttonClear);
        mNotifyCheck = (CheckBox) findViewById(R.id.checkBox);

        mSpinner = (Spinner) findViewById(R.id.spinner);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.exchange_rate, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        getPrefValues();

    }

    public void onClickSave(View btnSave) {

        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (etMonthlyIncome.length() != 0 || etMonthlyExpense.length() != 0 || etCriticalExpense.length() != 0) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString("currency", mSpinner.getSelectedItem().toString());
            editor.putInt("income", Integer.parseInt(etMonthlyIncome.getText().toString()));
            editor.putInt("expense", Integer.parseInt(etMonthlyExpense.getText().toString()));
            editor.putInt("critical", Integer.parseInt(etCriticalExpense.getText().toString()));
            editor.putBoolean("notify", mNotifyCheck.isChecked());
            editor.apply();

            assert etMonthlyIncome != null;
            etMonthlyIncome.setVisibility(View.GONE);
            etMonthlyExpense.setVisibility(View.GONE);
            etCriticalExpense.setVisibility(View.GONE);

            incomeBtn.setVisibility(View.VISIBLE);
            expenseBtn.setVisibility(View.VISIBLE);
            criticalBtn.setVisibility(View.VISIBLE);


            buttonSave.setVisibility(View.GONE);
            buttonClear.setVisibility(View.VISIBLE);

            //set values to buttons
            getPrefValues();


        } else {
            Toast.makeText(SettingsActivity.this, "Fill all fields first.", Toast.LENGTH_LONG).show();
        }

    }

    public void onClickClear(View btnClear) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure to clear all data?");
        builder.setMessage("This will clear all your settings. Click 'Yes' to proceed and 'No' to cancel.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences mPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = mPreferences.edit();
                editor.remove("currency");
                editor.remove("income");
                editor.remove("expense");
                editor.remove("critical");
                editor.remove("notify");
                editor.apply();

                etMonthlyIncome.setVisibility(View.VISIBLE);
                etMonthlyExpense.setVisibility(View.VISIBLE);
                etCriticalExpense.setVisibility(View.VISIBLE);

                incomeBtn.setVisibility(View.GONE);
                expenseBtn.setVisibility(View.GONE);
                criticalBtn.setVisibility(View.GONE);

                buttonSave.setVisibility(View.VISIBLE);
                buttonClear.setVisibility(View.GONE);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create();
        builder.show();

    }

    private void getPrefValues() {

        SharedPreferences mPreferences = getApplicationContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        incomeBtn.setText(String.valueOf(mPreferences.getInt("income", 0)));
        expenseBtn.setText(String.valueOf(mPreferences.getInt("expense", 0)));
        criticalBtn.setText(String.valueOf(mPreferences.getInt("critical", 0)));
        mNotifyCheck.setChecked(mPreferences.getBoolean("notify", false));

    }

}
