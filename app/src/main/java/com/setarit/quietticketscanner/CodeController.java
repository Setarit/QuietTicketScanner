package com.setarit.quietticketscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.setarit.quietticketscanner.preferences.Preferences_;
import com.setarit.quietticketscanner.service.ValidationFacade;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EActivity(R.layout.activity_code_controller)
public class CodeController extends AppCompatActivity {
    @ViewById
    public RelativeLayout container;
    @ViewById
    public EditText code;

    @Pref
    public Preferences_ preferences;
    private ValidationFacade validationFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadValidators();
    }

    @Background
    public void loadValidators() {
        validationFacade = new ValidationFacade(preferences.seatsJson().get());
    }

    @Click(R.id.returnToScanActivity)
    public void finishCurrentActivity() {
        finish();
    }

    @AfterViews
    public void executeAfterLoadingView(){
        loadCodeInputField();
    }

    private void loadCodeInputField() {
        code.setFocusableInTouchMode(true);
        code.requestFocus();
        code.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyEvent.getAction() == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                    verifyCode();         
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        validationFacade.saveCurrentSeatListToPreferences(preferences);
    }

    private void verifyCode() {
        preferences.hasScanned().put(true);
        validationFacade.validate(code.getText().toString());
        Intent intent = new Intent(this, ResultController_.class);
        intent.putExtra("FACADE", validationFacade);
        startActivity(intent);
    }
}