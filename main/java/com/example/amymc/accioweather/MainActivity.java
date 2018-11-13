package com.example.amymc.accioweather;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity
{
    public static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String apiKey = "26a78eabb06e4ec754986e6d84c1abc0";

        double latitude = 37.8267;
        double longitude = -122.4233;

        String apiURL = "https://api.darksky.net/forecast/"
                + apiKey + "/" + latitude +"," + longitude;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(apiURL).build();

        Call call = client.newCall(request);

        try
        {
            Response reponse = call.execute();
            if(reponse.isSuccessful())
            {
                Log.v(TAG, reponse.body().string());
            }
        } catch (IOException e)
        {
            e.printStackTrace();
            Log.e(TAG, "IO Exception caught: ", e);
        }
    }
}
