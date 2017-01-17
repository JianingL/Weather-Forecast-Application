package com.example.jianingliu.myweather;

import android.app.ActionBar;
import android.app.LocalActivityManager;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by JianingLiu on 11/18/15.
 */
public class DetailsActivity extends TabActivity {
    public final static String MSG = "com.example.jianingliu.myweather.MSG";
    public final static String DEGREE = "com.example.jianingliu.myweather.DEGREE";
    public final static String SRC = "com.example.jianingliu.myweather.SRC";

    private TextView head;

    TabHost tabHost;
    LocalActivityManager mLocalActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details);

        initTabs(savedInstanceState);
    }
    @Override
    protected void onResume() {
        mLocalActivityManager.dispatchResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mLocalActivityManager.dispatchPause(isFinishing());
        super.onPause();
    }
    private void initTabs(Bundle savedInstanceState) {
        Resources res = getResources();                     // Resource object to get Drawables
        final TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);    // The activity TabHost
        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        Intent intent;
        TabHost.TabSpec spec;
        intent = getIntent();

        String message = intent.getStringExtra(ResultActivity.MESSAGE);
        String city = intent.getStringExtra(ResultActivity.CITY);
        String state = intent.getStringExtra(ResultActivity.STATE);
        String degree = intent.getStringExtra(ResultActivity.DEGREE);
        String src = intent.getStringExtra(ResultActivity.SRC);

        head = (TextView) findViewById(R.id.head);
        head.setText("More details for " + city + ", " + state);



        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

            public void onTabChanged(String arg0) {
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    tabHost.getTabWidget().getChildAt(i)
                            .setBackgroundColor(Color.parseColor("#d6d8df")); // unselected
                }
                tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab())
                        .setBackgroundColor(Color.parseColor("#89d6f9")); // selected
            }
        });
        // 24 tab
        Intent intent24 = new Intent().setClass(this, Tab1Activity.class);
        intent24.putExtra(MSG,message);
        intent24.putExtra(DEGREE,degree);
        intent24.putExtra(SRC,src);
        TabHost.TabSpec tabSpec24 = tabHost
                .newTabSpec("next 24 hours")
                .setIndicator("NEXT 24 HOURS")
                .setContent(intent24);

        // 7 tab
        Intent intent7 = new Intent().setClass(this, Tab2Activity.class);
        intent7.putExtra(MSG,message);
        intent7.putExtra(DEGREE,degree);
        intent7.putExtra(SRC,src);
        TabHost.TabSpec tabSpec7 = tabHost
                .newTabSpec("next 7 days")
                .setIndicator("NEXT 7 DAYS")
                .setContent(intent7);


        // add all tabs
        tabHost.addTab(tabSpec24);
        tabHost.addTab(tabSpec7);

        //set Windows tab as default (zero based)
        tabHost.setCurrentTab(2);

    }

}




