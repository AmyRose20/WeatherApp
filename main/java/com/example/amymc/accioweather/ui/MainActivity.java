package com.example.amymc.accioweather.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.amymc.accioweather.weather.CurrentWeather;
import com.example.amymc.accioweather.R;
import com.example.amymc.accioweather.databinding.ActivityMainBinding;
import com.example.amymc.accioweather.weather.Forecast;
import com.example.amymc.accioweather.weather.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends Activity
{
    public static final String TAG = MainActivity.class.getSimpleName();
    private Forecast forecast;

    public ImageView iconImageView;

    final double latitude =  53.3498; // 23.5505;
    final double longitude = 6.2603; //46.6333;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude)
    {
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this,
                R.layout.activity_main);

        iconImageView = findViewById(R.id.weather_icon);

        String apiKey = "26a78eabb06e4ec754986e6d84c1abc0";

        String apiURL = "https://api.darksky.net/forecast/"
                + apiKey + "/" + latitude +"," + longitude;

        if(isNetworkAvailable())
        {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(apiURL).build();

            Call call = client.newCall(request);
            call.enqueue(new Callback()
            {
                @Override
                public void onFailure(Call call, IOException e)
                {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException
                {
                    try
                    {
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful())
                        {
                            forecast  = parseForecastData(jsonData);

                            CurrentWeather currentWeather = forecast.getCurrentWeather();

                            final CurrentWeather displayWeather = new CurrentWeather(
                                    currentWeather.getLocationLabel(),
                                    currentWeather.getIcon(),
                                    currentWeather.getTime(),
                                    currentWeather.getTemperature(),
                                    currentWeather.getHumidity(),
                                    currentWeather.getPrecipChance(),
                                    currentWeather.getSummary(),
                                    currentWeather.getTimeZone()
                            );

                            binding.setWeather(displayWeather);

                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    Drawable drawable = getResources().getDrawable(displayWeather.getIconId());
                                    iconImageView.setImageDrawable(drawable);
                                }
                            });
                        }
                        else
                        {
                            alertUserAboutError();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        Log.e(TAG, "IO Exception caught: ", e);
                    }
                    catch (JSONException e)
                    {
                        Log.e(TAG, "JSON exception caught: ", e);
                    }
                }
            });
        }
    }

    private Forecast parseForecastData(String jsonData) throws JSONException
    {
        Forecast forecast = new Forecast();

        forecast.setCurrentWeather(getCurrentDetails(jsonData));
        forecast.setHourlyForecast(getHourlyForecast(jsonData));

        return forecast;
    }

    private Hour[] getHourlyForecast(String jsonData) throws JSONException
    {
        JSONObject forecast = new JSONObject(jsonData);
        String timeZone = forecast.getString("timezone");

        JSONObject hourly =forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");

        Hour[] hours = new Hour[data.length()];

        for(int i = 0; i < data.length(); i++)
        {
            JSONObject jsonHour = data.getJSONObject(i);
            Hour hour = new Hour();

            hour.setSummary(jsonHour.getString("summary"));
            hour.setIcon(jsonHour.getString("icon"));
            hour.setTemperature(jsonHour.getDouble("temperature"));
            hour.setTime(jsonHour.getLong("time"));
            hour.setTimeZone(timeZone);

            hours[i] = hour;
        }
        return hours;
    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException
    {
            JSONObject forecast = new JSONObject(jsonData);
            String timezone = forecast.getString("timezone");
            Log.i(TAG, "From JSON: " + timezone);

            JSONObject currently = forecast.getJSONObject("currently");

            CurrentWeather currentWeather = new CurrentWeather();
            currentWeather.setHumidity(currently.getDouble("humidity"));
            currentWeather.setTime(currently.getLong("time"));
            currentWeather.setIcon(currently.getString("icon"));
            currentWeather.setLocationLabel("Dublin, Ireland");
            currentWeather.setPrecipChance(currently.getDouble("precipProbability"));
            currentWeather.setSummary(currently.getString("summary"));
            currentWeather.setTemperature(currently.getDouble("temperature"));
            currentWeather.setTimeZone(timezone);

            Log.d(TAG, currentWeather.getFormattedTime());

            return currentWeather;
    }

    private boolean isNetworkAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if(networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;
        }
        else
        {
            Toast.makeText(this, getString(R.string.network_unavailable_message), Toast.LENGTH_LONG).show();
        }
        return isAvailable;
    }

    private void alertUserAboutError()
    {
        AlertDialogFragment dialog = new AlertDialogFragment();
        // dialog.show(getSupportFragmentManager(), "error_dialog");
    }

    public void refreshOnClick(View view)
    {
        Toast.makeText(this, "Refreshing data", Toast.LENGTH_LONG);
        getForecast(latitude, longitude);
    }

    public void hourlyOnClick(View view)
    {
        Intent intent = new Intent(this, HourlyForecastActivity.class);
        startActivity(intent);
    }
}
