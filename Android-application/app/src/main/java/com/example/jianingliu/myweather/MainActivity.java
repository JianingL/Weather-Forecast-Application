package com.example.jianingliu.myweather;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Downloader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {
    private EditText street;
    private EditText city;
    private Spinner state;
    private RadioGroup degree;
    private RadioButton f;
    private RadioButton c;
    private TextView error;
    public Button search;

    public final static String EXTRA_MESSAGE = "com.example.jianingliu.myweather.MESSAGE";
    public final static String EXTRA_DEGREE = "com.example.jianingliu.myweather.DEGREE";
    public final static String EXTRA_CITY = "com.example.jianingliu.myweather.CITY";
    public final static String EXTRA_STATE = "com.example.jianingliu.myweather.STATE";
    @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        street = (EditText) findViewById(R.id.street);
        city = (EditText) findViewById(R.id.city);
        state = (Spinner) findViewById(R.id.state);
        degree = (RadioGroup) findViewById(R.id.degree);

        f = (RadioButton) findViewById(R.id.fahrenheit);
        c = (RadioButton) findViewById(R.id.celsius);
        error = (TextView) findViewById(R.id.error);
        search = (Button) findViewById(R.id.search);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = street.getText().toString();
                String cit = city.getText().toString();
                String sta = state.getSelectedItem().toString();

                if(str.equals("")){
                    error.setText("Please\tenter\ta\tStreet");
                }else if(cit.equals("")){
                    error.setText("Please\tenter\ta\tCity");
                }else if(sta.equals("Select state")){
                    error.setText("Please\tselect\ta\tState");
                }else{
                    goToSearch(v);

                }
            }
        });
    }


    public void goToSearch(View view){
        String str = street.getText().toString();
        String cit = city.getText().toString();
        String sta = state.getSelectedItem().toString();
        String deg = "";
        int degreeId = degree.getCheckedRadioButtonId();
        RadioButton radioButton = (RadioButton) findViewById(degreeId);
        if(radioButton.getText().equals("Celsius")){
            deg = "celsius";
        }else if(radioButton.getText().equals("Fahrenheit")){
            deg = "fahrenheit";
        }

        new GetJson().execute(str, cit, sta, deg);
    }
    public class GetJson extends AsyncTask<String, String, String> {

        HttpURLConnection urlConnection;

        @Override
        protected String doInBackground(String... args) {

            StringBuilder result = new StringBuilder();


            try {

                String street = URLEncoder.encode(args[0], "utf-8");
                String city = URLEncoder.encode(args[1],"utf-8");
                String state = URLEncoder.encode(args[2],"utf-8");
                String degree = URLEncoder.encode(args[3],"utf-8");
                URL url = new URL("http://jianingapplication-env.elasticbeanstalk.com/?address="+street+"&city="+city+"&state="+state+"&degree="+degree);
                Log.e("tag",url.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(in));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
            }catch( Exception e) {
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            int degreeId = degree.getCheckedRadioButtonId();
            RadioButton radioButton = (RadioButton) findViewById(degreeId);
            String message = result;
            String d = radioButton.getText().toString();
            String c = city.getText().toString();
            String s = state.getSelectedItem().toString();

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            intent.putExtra(EXTRA_CITY,c);
            intent.putExtra(EXTRA_STATE,s);
            intent.putExtra(EXTRA_DEGREE,d);

            startActivity(intent);

        }

    }
    public void clear(View view){
        street.setText("");
        city.setText("");
        state.setSelection(0);
        f.setChecked(true);
        c.setChecked(false);
        error.setText("");
    }
    public void goToWeb(View view){
        Uri uriUrl = Uri.parse("http://forecast.io");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW,uriUrl);
        startActivity(launchBrowser);
    }
    public void goToAbout(View view){
        Intent intent = new Intent(MainActivity.this, AboutActivity.class);

        startActivity(intent);
    }


}
