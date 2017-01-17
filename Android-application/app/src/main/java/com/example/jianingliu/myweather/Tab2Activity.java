package com.example.jianingliu.myweather;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
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
import java.util.Date;
import java.util.TimeZone;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by JianingLiu on 12/8/15.
 */
public class Tab2Activity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String message = intent.getStringExtra(DetailsActivity.MSG);
        String degree = intent.getStringExtra(DetailsActivity.DEGREE);
        int src = Integer.parseInt(intent.getStringExtra(DetailsActivity.SRC));

        String[] colors = {"#ffc0cb","#ffc000","#ffff80","#b7ffaf","#33ddff","#c9e9ff","#c9ceff"};
        LinearLayout l = new LinearLayout(this);
        l.removeAllViews();
        ScrollView scrollView2 = new ScrollView(this);
        scrollView2.scrollTo(0, 0);
        scrollView2.setVerticalScrollbarPosition(0);
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        try {
            JSONObject obj = new JSONObject(message);
            JSONObject daily = obj.getJSONObject("daily");
            JSONArray dailydata = daily.getJSONArray("data");

            for(int i = 1; i< 8; i++ ){
                JSONObject data = dailydata.getJSONObject(i);

                TableLayout tableLayout = new TableLayout(this);
                tableLayout.setMinimumWidth(800);
                tableLayout.setStretchAllColumns(true);
                LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
                llp.setMargins(0, 0, 0, 20);
                tableLayout.setLayoutParams(llp);
                tableLayout.setBackgroundColor(Color.parseColor(colors[i-1]));

                //row1
                TableRow tableRow1 = new TableRow(this);
                tableRow1.setPadding(10,10,10,10);
                //date
                long time = data.getLong("time");

                SimpleDateFormat sdf = new SimpleDateFormat("EEEE, MMM d");
                sdf.setTimeZone(TimeZone.getTimeZone(obj.getString("timezone")));
                String t = sdf.format(time * 1000);


                TextView dateshow = new TextView(this);
                dateshow.setText(t);
                dateshow.setTextSize(18);
                dateshow.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                dateshow.setTextColor(Color.BLACK);

                //image
                ImageView imageView = new ImageView(this);
                imageView.setImageResource(src);
                imageView.setAdjustViewBounds(true);
                imageView.setMaxWidth(10);
                TableRow.LayoutParams trl = new TableRow.LayoutParams(60, 70);
                //trl.leftMargin = 30;
                //trl.gravity = Gravity.RIGHT;
                imageView.setLayoutParams(trl);

                Log.e("para", String.valueOf(trl));

                tableRow1.addView(dateshow);
                tableRow1.addView(imageView);

                //row2
                TableRow tableRow2 = new TableRow(this);
                tableRow2.setPadding(10, 10, 10, 50);
                //temp
                String min = String.valueOf(Math.round(data.getDouble("temperatureMin")));
                String max = String.valueOf(Math.round(data.getDouble("temperatureMax")));

                TextView tempshow = new TextView(this);
                tempshow.setText("Min: " + min + " " + degree + " | Max: " + max + " " + degree);
                tempshow.setTextSize(16);
                tempshow.setTextColor(Color.BLACK);

                tableRow2.addView(tempshow);
                tableRow2.setPadding(10,10,10,10);

                //row

                tableLayout.addView(tableRow1);
                tableLayout.addView(tableRow2);

                linearLayout.addView(tableLayout);
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }

        scrollView2.addView(linearLayout);
        l.addView(scrollView2);
        setContentView(l);
    }
}
