package com.example.jianingliu.myweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Created by JianingLiu on 12/8/15.
 */
public class Tab1Activity extends Activity {
    private TableRow row;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(DetailsActivity.MSG);
        String degree = intent.getStringExtra(DetailsActivity.DEGREE);
        int src = Integer.parseInt(intent.getStringExtra(DetailsActivity.SRC));


        ScrollView scrollView = new ScrollView(this);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setMinimumWidth(800);
        tableLayout.setStretchAllColumns(true);


        TableRow tableRow0 = new TableRow(this);
        TextView h1 = new TextView(this);
        h1.setText("Time");
        h1.setTextSize(17);
        h1.setTextColor(Color.BLACK);
        h1.setGravity(Gravity.CENTER);

        TextView h2 = new TextView(this);
        h2.setText("Summary");
        h2.setTextSize(17);
        h2.setTextColor(Color.BLACK);
        h2.setGravity(Gravity.CENTER);

        TextView h3 = new TextView(this);
        h3.setText("Temp(" + degree + ")");
        h3.setTextSize(17);
        h3.setTextColor(Color.BLACK);
        h3.setGravity(Gravity.CENTER);

        tableRow0.addView(h1);
        tableRow0.addView(h2);
        tableRow0.addView(h3);
        tableRow0.setBackgroundColor(Color.parseColor("#04b9fc"));
        tableRow0.setPadding(5, 5, 5, 5);
        tableRow0.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, 70));
        tableLayout.addView(tableRow0);


        try {
            JSONObject obj = new JSONObject(message);
            JSONObject hourly = obj.getJSONObject("hourly");
            JSONArray hourlydata = hourly.getJSONArray("data");

            for(int i = 0; i< 24; i++ ){
                JSONObject data = hourlydata.getJSONObject(i);
                TableRow tableRow = new TableRow(this);
                if((i%2)==0){
                    tableRow.setBackgroundColor(Color.parseColor("#9c9c9c"));
                }

                //time
                long time = data.getLong("time");
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                sdf.setTimeZone(TimeZone.getTimeZone(obj.getString("timezone")));
                String t = sdf.format(time * 1000);

                TextView timeshow = new TextView(this);
                timeshow.setText(t);
                timeshow.setTextColor(Color.BLACK);
                timeshow.setGravity(Gravity.CENTER);

                //summary
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(src);
                imageView.setAdjustViewBounds(true);
                imageView.setMaxWidth(10);
                imageView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 70));


                Log.e("para", String.valueOf(imageView.getHeight()));
                //temp
                double temper = data.getDouble("temperature");
                String temp = Integer.toString((int)Math.round(temper));
                TextView tempshow = new TextView(this);
                tempshow.setText(temp);
                tempshow.setTextColor(Color.BLACK);
                tempshow.setGravity(Gravity.CENTER);
                //row
                tableRow.addView(timeshow);
                tableRow.addView(imageView);
                tableRow.addView(tempshow);
                tableRow.setPadding(5,5,5,5);
                tableLayout.addView(tableRow);
            }
            final TableRow tableRowbutton = new TableRow(this);
            Button btn = new Button(this);
            btn.setBackgroundColor(Color.parseColor("#04b9fc"));
            btn.setText("+");
            Log.e("size", String.valueOf(btn.getTextSize()));
            btn.setTextSize(30);
            btn.setPadding(-15, -15, -15, -15);
            btn.setLayoutParams(new TableRow.LayoutParams(60, 60));


            View.OnClickListener listener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    tableRowbutton.setVisibility(View.GONE);
                    for(int i = 24; i< 48; i++){
                        int id = i;
                        row = (TableRow) findViewById(id);
                        row.setVisibility(View.VISIBLE);
                    }

                }
            };
            btn.setOnClickListener(listener);
            tableRowbutton.addView(btn);
            tableRowbutton.setGravity(Gravity.CENTER);
            tableRowbutton.setBackgroundColor(Color.parseColor("#9c9c9c"));
            tableLayout.addView(tableRowbutton);



            for(int i = 24; i< 48; i++ ){
                JSONObject data = hourlydata.getJSONObject(i);
                TableRow tableRow = new TableRow(this);
                if((i%2)==0){
                    tableRow.setBackgroundColor(Color.parseColor("#9c9c9c"));
                }

                //time
                long time = data.getLong("time");
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
                sdf.setTimeZone(TimeZone.getTimeZone(obj.getString("timezone")));
                String t = sdf.format(time * 1000);

                TextView timeshow = new TextView(this);
                timeshow.setText(t);
                timeshow.setTextColor(Color.BLACK);
                timeshow.setGravity(Gravity.CENTER);

                //summary
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(src);
                imageView.setAdjustViewBounds(true);
                imageView.setMaxWidth(10);
                imageView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 70));

                //temp
                double temper = data.getDouble("temperature");
                String temp = Integer.toString((int)Math.round(temper));
                TextView tempshow = new TextView(this);
                tempshow.setText(temp);
                tempshow.setTextColor(Color.BLACK);
                tempshow.setGravity(Gravity.CENTER);
                //row
                tableRow.addView(timeshow);
                tableRow.addView(imageView);
                tableRow.addView(tempshow);
                tableRow.setVisibility(View.GONE);
                int id = i;
                tableRow.setId(id);
                tableLayout.addView(tableRow);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        scrollView.addView(tableLayout);
        setContentView(scrollView);
    }

}
