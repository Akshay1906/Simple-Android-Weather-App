package com.example.fetchweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button;
    ImageView imageView;

    TextView country_yt, city_yt, temp_yt,time;
    TextView feels_like,latitude,longitude,humidity,sunrise,sunset,pressure,windspeed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextTextPersonName);
        button = findViewById(R.id.button);
        country_yt = findViewById(R.id.country);
        city_yt = findViewById(R.id.city_name);
        temp_yt = findViewById(R.id.textView3);
        time = findViewById(R.id.time);

        feels_like = findViewById(R.id.feels);
        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        humidity = findViewById(R.id.humidity);
        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        pressure = findViewById(R.id.pressure);
        windspeed = findViewById(R.id.wind);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                findWeather();

            }
        });

    }

    private void findWeather() {
        String city = editText.getText().toString();

        String url="http://api.openweathermap.org/data/2.5/weather?q=+"+city+"&units=metric&appid=48ddbf115473b0f91037a53017daa85a";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    //fetch country
                    JSONObject obj1 = jsonObject.getJSONObject("sys");
                    String country_find = obj1.getString("country");
                    country_yt.setText(country_find);

                    //fetch city
                    String city_fetch = jsonObject.getString("name");
                    city_yt.setText(city_fetch);

                    //fetch Temperature
                    JSONObject obj2 = jsonObject.getJSONObject("main");
                    double temp = obj2.getDouble("temp");
                    temp_yt.setText(temp+" °C");

                    //binding image icon

                    /*JSONArray jsonArray = jsonObject.getJSONArray("weather");
                    JSONObject obj3 = jsonArray.getJSONObject(0);
                    String icon = obj3.getString("icon");
                    Picasso.get().load("http://openweathermap.org/img/wn/"+icon+"@2x.png").into(imageView);*/

                    //find Date and Time
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat std = new  SimpleDateFormat("dd/MM/yyyy \nHH:mm:ss");
                    String date = std.format(calendar.getTime());
                    time.setText(date);


                    //fetching latitude
                    JSONObject obj3 = jsonObject.getJSONObject("coord");
                    double lat_find=  obj3.getDouble("lat");
                    latitude.setText(lat_find+"° N");

                    //fetching longitude
                    JSONObject obj4 = jsonObject.getJSONObject("coord");
                    double lon_find = obj4.getDouble("lon");
                    longitude.setText(lon_find+"° E");


                    //fetching humidity
                    JSONObject obj5 = jsonObject.getJSONObject("main");
                    double humid_find = obj5.getDouble("humidity");
                    humidity.setText(humid_find+" %");

                    //Sunrise
                    JSONObject obj6 = jsonObject.getJSONObject("sys");
                    String sunrise_find = obj6.getString("sunrise");
                    sunrise.setText(sunrise_find+" AM");

                    //Sunset
                    JSONObject obj7 = jsonObject.getJSONObject("sys");
                    String sunset_find = obj6.getString("sunset");
                    sunset.setText(sunset_find+" PM");

                    //pressure
                    JSONObject obj8 = jsonObject.getJSONObject("main");
                    String press_find = obj8.getString("pressure");
                    pressure.setText(press_find+" hpa");

                    //windspeed
                    JSONObject obj9 = jsonObject.getJSONObject("wind");
                    String speed_find = obj9.getString("speed");
                    windspeed.setText(speed_find+" km/hr");

                    //feels_like
                    JSONObject obj10 = jsonObject.getJSONObject("main");
                    String feels_find = obj10.getString("feels_like");
                    feels_like.setText(feels_find+" °C");





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}