package com.setarit.quietticketscanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.setarit.quietticketscanner.view.listeners.TokenSubmitListener;

public class TokenController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_controller);
        setTitle(R.string.token);
        //((EditText) findViewById(R.id.accessTokenField)).setOnEditorActionListener(new TokenSubmitListener());
    }
}
