package com.example.amymc.accioweather.ui;

import android.os.Bundle;
import android.app.Activity;

import com.example.amymc.accioweather.R;

public class HourlyForecastActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_forecast);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
