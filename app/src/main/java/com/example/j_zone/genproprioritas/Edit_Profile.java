package com.example.j_zone.genproprioritas;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Edit_Profile extends AppCompatActivity {
    Spinner SpFirst, SpSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SpFirst = (Spinner)findViewById(R.id.SpFirst);
        SpSecond = (Spinner)findViewById(R.id.SpSecond);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SpFirst.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = SpFirst.getSelectedItem().toString();
                int idSpinner = getResources().getIdentifier(name, "array", Edit_Profile.this.getPackageName());

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Edit_Profile.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(idSpinner));
                SpSecond.setAdapter(spinnerArrayAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void save(View view) {
        Intent intent = new Intent(Edit_Profile.this, Menu_main.class);
        startActivity(intent);
    }
}
