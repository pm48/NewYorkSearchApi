package com.example.android.newyorkarticlesearch.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.newyorkarticlesearch.DialogFragments.DatePickerFragment;
import com.example.android.newyorkarticlesearch.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FilteredActivity extends AppCompatActivity  implements DatePickerDialog.OnDateSetListener {
    TextView tvDate;
    TextView tvSort;
    TextView tvNews;
    Spinner spinner;
    EditText etDate;
    Button btnSave;
    CheckBox cbArts;
    CheckBox cbFashion;
    CheckBox cbSport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_filter);
        setSupportActionBar(toolbar);
        setupViews();
        btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra("date", etDate.getText().toString());
                data.putExtra("sort", spinner.getSelectedItem().toString());
                ArrayList<String> news = new ArrayList<String>();
               if(cbArts.isChecked()) {
                   news.add("Arts");
               }
                if(cbFashion.isChecked()){
                   news.add("Health");
               }
                if(cbSport.isChecked()){
                   news.add("Sports");
               }
                data.putStringArrayListExtra("news", news);

                setResult(RESULT_OK, data);
                finish();

            }
        });

    }

    private void setupViews() {
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvSort = (TextView) findViewById(R.id.tvSort);
        tvNews = (TextView) findViewById(R.id.tvNewsDeskValues);
        etDate = (EditText) findViewById(R.id.etDate);
        spinner = (Spinner) findViewById(R.id.spinner);
        btnSave = (Button) findViewById(R.id.btnSave);
        cbArts = (CheckBox) findViewById(R.id.checkbox_arts);
        cbFashion = (CheckBox) findViewById(R.id.checkbox_fashion);
        cbSport = (CheckBox) findViewById(R.id.checkbox_sports);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_array, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        spinner_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(spinner_adapter);
        Intent intent = getIntent();
        Bundle extras = getIntent().getExtras();
        if(intent!=null && extras!=null) {
            String date = extras.getString("date");
            List<String>  news = extras.getStringArrayList("news");
            String order = extras.getString("order");

            if (news.contains("Arts")) {
                cbArts.setChecked(true);
            }
            if (news.contains("Health")) {
                cbFashion.setChecked(true);
            }
            if (news.contains("Sports")) {
                cbSport.setChecked(true);
            }


            etDate.setText(date);

            if (order.equals("oldest")) {
                spinner.setSelection(0);
            } else {
                spinner.setSelection(1);
            }
        }
    }

    // attach to an onclick handler to show the date picker
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(),"datePicker");
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String month = "";
        if(monthOfYear <= 9){
             month = "0" + Integer.valueOf(monthOfYear+1);
        }
        etDate.setText(new StringBuilder().append(year)
                .append(month).append(dayOfMonth));
    }
}
