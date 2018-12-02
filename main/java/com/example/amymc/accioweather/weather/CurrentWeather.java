package com.example.amymc.accioweather.weather;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeather
{
    // Data
    private String locationLabel;
    private String icon;
    private long time;
    private double temperature;
    private double humidity;
    private double precipChance;
    private String summary;
    private String timeZone;

    // Constructors
    public CurrentWeather()
    {

    }

    public CurrentWeather(String locationLabel, String icon, long time, double temperature, double humidity, double precipChance, String summary, String timeZone)
    {
        this.locationLabel = locationLabel;
        this.icon = icon;
        this.time = time;
        this.temperature = temperature;
        this.humidity = humidity;
        this.precipChance = precipChance;
        this.summary = summary;
        this.timeZone = timeZone;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public double getPrecipChance()
    {
        return precipChance;
    }

    public void setPrecipChance(double precipChance)
    {
        this.precipChance = precipChance;
    }

    public double getHumidity()
    {
        return humidity;
    }

    public void setHumidity(double humidity)
    {
        this.humidity = humidity;
    }

    public double getTemperature()
    {
        return temperature;
    }

    public void setTemperature(double temperature)
    {
        this.temperature = temperature;
    }

    public String getIcon()
    {
        return icon;
    }

    public void setIcon(String icon)
    {
        this.icon = icon;
    }

    //TEST
    public int getIconId()
    {
        return Forecast.getIconId(icon);
    }

    // TEST
    public String getFormattedTime()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));

        Date dateTime = new Date(time * 1000);
        // Pass in a Java date object here
        return formatter.format(dateTime);
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getLocationLabel()
    {
        return locationLabel;
    }

    public void setLocationLabel(String locationLabel)
    {
        this.locationLabel = locationLabel;
    }

    public String getTimeZone()
    {
        return timeZone;
    }

    public void setTimeZone(String timeZone)
    {
        this.timeZone = timeZone;
    }
}
