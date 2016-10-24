package com.setarit.quietticketscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class InfoController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_controller);
        setTitle(R.string.info);
    }
}
