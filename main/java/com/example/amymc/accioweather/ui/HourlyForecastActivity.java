package com.example.amymc.accioweather.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import com.example.amymc.accioweather.R;
import com.example.amymc.accioweather.adapters.HourlyAdapter;
import com.example.amymc.accioweather.databinding.ActivityHourlyForecastBinding;
import com.example.amymc.accioweather.weather.Hour;
import java.util.ArrayList;
import java.util.List;

public class HourlyForecastActivity extends Activity
{
    private HourlyAdapter adapter;
    private ActivityHourlyForecastBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        List<Hour> hoursList = (ArrayList<Hour>) intent.getSerializableExtra("HourlyList");

        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_hourly_forecast);

        adapter = new HourlyAdapter(hoursList, this);

        binding.hourlyListItems.setAdapter(adapter);
        // setHasFixedSize() improves RecyclerView performance
        binding.hourlyListItems.setHasFixedSize(true);
        // Add dividers between the bound data list items
        binding.hourlyListItems.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        binding.hourlyListItems.setLayoutManager(new LinearLayoutManager(this));
    }
}
