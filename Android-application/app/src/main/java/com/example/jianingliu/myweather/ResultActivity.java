package com.example.jianingliu.myweather;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by JianingLiu on 11/18/15.
 */
public class ResultActivity extends Activity{
    public final static String LAT = "com.example.jianingliu.myweather.LAT";
    public final static String LNG = "com.example.jianingliu.myweather.LNG";
    public final static String MESSAGE = "com.example.jianingliu.myweather.MESSAGE";
    public final static String CITY = "com.example.jianingliu.myweather.CITY";
    public final static String STATE = "com.example.jianingliu.myweather.STATE";
    public final static String DEGREE = "com.example.jianingliu.myweather.DEGREE";
    public final static String SRC = "com.example.jianingliu.myweather.SRC";

    String lat;
    String lng;
    String msg;
    String city;
    String state;
    String degree;
    String iconsrc;

    private ImageView resultIcon;
    private TextView resultSummary;
    private TextView resultTemp;
    private TextView resultLH;
    private TextView precipitation;
    private TextView chance;
    private TextView wind;
    private TextView dew;
    private TextView humidity;
    private TextView visibility;
    private TextView sunrise;
    private TextView sunset;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.result);

        initResult(savedInstanceState);
    }
    private void initResult(Bundle savedInstanceState){
        //define variables
        resultIcon = (ImageView) findViewById(R.id.resultIcon);
        resultSummary = (TextView) findViewById(R.id.resultSummary);
        resultTemp = (TextView) findViewById(R.id.resultTemp);
        resultLH = (TextView) findViewById(R.id.resultLH);
        precipitation = (TextView) findViewById(R.id.precipitation);
        chance = (TextView) findViewById(R.id.chance);
        wind = (TextView) findViewById(R.id.wind);
        dew = (TextView) findViewById(R.id.dew);
        humidity = (TextView) findViewById(R.id.humidity);
        visibility = (TextView) findViewById(R.id.visibility);
        sunrise = (TextView) findViewById(R.id.sunrise);
        sunset = (TextView) findViewById(R.id.sunset);
        //get JSON
        Intent intent = getIntent();

        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        msg = message;
        String d = intent.getStringExtra(MainActivity.EXTRA_DEGREE);
        String c = intent.getStringExtra(MainActivity.EXTRA_CITY);
        city = c;
        String s = intent.getStringExtra(MainActivity.EXTRA_STATE);
        state = s;
        String deg = "";
        String w = "";
        String v = "";
        String p = "";
        String sum = "";
        double tem = 0;
        String sr = "";

        if (d.equals("Celsius")) {
            deg = "\u0020\u2103";
            w = " m/s";
            v = " km";
        } else if (d.equals("Fahrenheit")) {
            deg = "\u0020\u2109";
            w = " mph";
            v = " mi";
        }
        degree = deg;

        try {
            JSONObject obj = new JSONObject(message);
            JSONObject currently = obj.getJSONObject("currently");
            JSONObject daily = obj.getJSONObject("daily");
            JSONArray dailydata = daily.getJSONArray("data");
            JSONObject dailydata0 = dailydata.getJSONObject(0);
            lat = obj.getString("latitude");
            lng = obj.getString("longitude");
            double temp = currently.getDouble("temperature");
            String icon = currently.getString("icon");
            String summary = currently.getString("summary");
            sum = summary;
            tem = temp;
            double pre = currently.getDouble("precipIntensity");
            double cha = currently.getDouble("precipProbability");
            long sunrisetime = dailydata0.getLong("sunriseTime");
            long sunsettime = dailydata0.getLong("sunsetTime");

            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
            sdf.setTimeZone(TimeZone.getTimeZone(obj.getString("timezone")));
            String sunr = sdf.format(sunrisetime * 1000);
            String suns = sdf.format(sunsettime * 1000);
            int src = R.drawable.clear;
            //set result
            if (icon.equals("clear-day")) {
                src = R.drawable.clear;
                sr = "clear.png";
            } else if (icon.equals("clear-night")) {
                src = R.drawable.clear_night;
                sr = "clear_night.png";
            } else if (icon.equals("rain")) {
                src = R.drawable.rain;
                sr = "rain.png";
            } else if (icon.equals("snow")) {
                src = R.drawable.snow;
                sr = "snow.png";
            } else if (icon.equals("sleet")) {
                src = R.drawable.sleet;
                sr = "sleet.png";
            } else if (icon.equals("wind")) {
                src = R.drawable.wind;
                sr = "wind.png";
            } else if (icon.equals("fog")) {
                src = R.drawable.fog;
                sr = "fog.png";
            } else if (icon.equals("cloudy")) {
                src = R.drawable.cloudy;
                sr = "cloudy.png";
            } else if (icon.equals("partly-cloudy-day")) {
                src = R.drawable.cloud_day;
                sr = "cloud_day.png";
            } else if (icon.equals("partly-cloudy-night")) {
                src = R.drawable.cloud_night;
                sr = "cloud_night.png";
            }
            iconsrc = String.valueOf(src);

            resultIcon.setImageResource(src);
            resultSummary.setText(summary + " in " + c + ", " + s);
            resultTemp.setText(Integer.toString((int) Math.round(temp)) + deg);
            resultLH.setText("L: " + Math.round(dailydata0.getDouble("temperatureMin")) + "\u00B0" + " | H: " + Math.round(dailydata0.getDouble("temperatureMax")) + "\u00B0");

            if (d.equals("Fahrenheit")) {
                if (pre >= 0 && pre < 0.02) {
                    precipitation.setText("None");
                } else if (pre >= 0.002 && pre < 0.017) {
                    precipitation.setText("Very Light");
                } else if (pre >= 0.017 && pre < 0.1) {
                    precipitation.setText("Light");
                } else if (pre >= 0.1 && pre < 0.4) {
                    precipitation.setText("Moderate");
                } else if (pre >= 0.4) {
                    precipitation.setText("Heavy");
                }
            } else if (d.equals("Celsius")) {
                if (pre >= 0 && pre < 0.0508) {
                    precipitation.setText("None");
                } else if (pre >= 0.0508 && pre < 0.4318) {
                    precipitation.setText("Very Light");
                } else if (pre >= 0.4318 && pre < 2.54) {
                    precipitation.setText("Light");
                } else if (pre >= 2.54 && pre < 10.16) {
                    precipitation.setText("Moderate");
                } else if (pre >= 10.16) {
                    precipitation.setText("Heavy");
                }
            }

            chance.setText((Math.round(cha)) * 100 + "%");
            wind.setText(String.format("%.2f", (currently.getDouble("windSpeed"))) + w);
            dew.setText(String.format("%.2f", (currently.getDouble("dewPoint"))) + deg);
            humidity.setText(Math.round(currently.getDouble("humidity") * 100) + "%");
            visibility.setText(String.format("%.2f", (currently.getDouble("visibility"))) + v);
            sunrise.setText(sunr);
            sunset.setText(suns);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //facebook

        ShareButton shareButton = (ShareButton) findViewById(R.id.fb_share_button);

        callbackManager = CallbackManager.Factory.create();
        shareButton.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>(){


            @Override
            public void onSuccess(Sharer.Result result) {
                if(result.getPostId()!=null){
                    Toast.makeText(getApplicationContext(),
                            "Facebook Post Successful", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Post Cancelled", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),
                        "Post Cancelled", Toast.LENGTH_LONG).show();
                Log.e("cel","cancel");
                Log.e("fb", "error");

            }
        });

        ShareLinkContent content = new ShareLinkContent.Builder()

                .setContentUrl(Uri.parse("http://forecast.io"))
                .setContentTitle("Current Weather in " + c + ", " + s)
                .setContentDescription(sum + ", " + Integer.toString((int) Math.round(tem)) + deg)
                .setImageUrl(Uri.parse("http://cs-server.usc.edu:45678/hw/hw8/images/" + sr))
                .build();
        shareButton.setShareContent(content);
        LoginManager.getInstance().logOut();




    }

    @Override
    protected void onActivityResult(final int requestCode,final int resultCode,final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void goToMap(View view){
        Intent intent = new Intent(ResultActivity.this, MapActivity.class);
        String la = lat;
        String ln = lng;
        intent.putExtra(LAT,la);
        intent.putExtra(LNG,ln);

        startActivity(intent);
    }
    public void goToDetails(View view){
        Intent intent = new Intent(ResultActivity.this, DetailsActivity.class);
        String m = msg;
        String ci = city;
        String st = state;
        String deg = degree;
        String isrc = iconsrc;
        intent.putExtra(MESSAGE,m);
        intent.putExtra(CITY,ci);
        intent.putExtra(STATE,st);
        intent.putExtra(DEGREE,deg);
        intent.putExtra(SRC,isrc);
        startActivity(intent);
    }
}
