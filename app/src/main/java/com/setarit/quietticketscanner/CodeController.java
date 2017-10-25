package com.setarit.quietticketscanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.setarit.quietticketscanner.preferences.Preferences_;
import com.setarit.quietticketscanner.ticket.SeatCodeValidator;
import com.setarit.quietticketscanner.ticket.VisitorCodeValidator;

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
    private SeatCodeValidator seatCodeValidator;
    private VisitorCodeValidator visitorCodeValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadValidators();
    }

    @Background
    public void loadValidators() {
        seatCodeValidator = new SeatCodeValidator(preferences.visitorsJson().get());
        visitorCodeValidator = new VisitorCodeValidator(preferences.visitorsJson().get());
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

    private void verifyCode() {//TODO: extract reusable class
        String rawCode = code.getText().toString();
        if(rawCode.startsWith("SQT-")){
            rawCode = rawCode.substring(4);
        }
        boolean isSeatCode = true;
        try{
            Long.parseLong(rawCode);
            isSeatCode = false;
            visitorCodeValidator.isValid(rawCode);
        }catch (NumberFormatException e){
            seatCodeValidator.isValid(rawCode);
        }finally {
            Intent intent = null;
            if(isSeatCode){
                intent = new Intent(this, SeatScanResultController_.class);
            }else{
                intent = new Intent(this, VisitorScanResultController_.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }
}