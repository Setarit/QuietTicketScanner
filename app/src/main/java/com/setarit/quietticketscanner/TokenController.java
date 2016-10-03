package com.setarit.quietticketscanner;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.setarit.quietticketscanner.view.listeners.TokenSubmitListener;

public class TokenController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token_controller);
        setTitle(R.string.token);
        setTokenInputListeners();
    }

    private void setTokenInputListeners() {
        EditText input = ((EditText) findViewById(R.id.accessTokenField));
        if(input != null){
            input.setOnEditorActionListener(new TokenSubmitListener(this));
        }
    }

    public void showErrorMessage(String message){
        TextView errorMessage = (TextView) findViewById(R.id.tokenValidationMessage);
        if(errorMessage != null) {
            hideKeyboard();
            (findViewById(R.id.accessTokenField)).clearFocus();
            (findViewById(R.id.accessTokenField)).getBackground().mutate().setColorFilter(ContextCompat.getColor(this, R.color.colorErrorMessage), PorterDuff.Mode.SRC_ATOP);
            errorMessage.setVisibility(View.VISIBLE);
            errorMessage.setText(message);
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void showScanActivity() {

    }
}
