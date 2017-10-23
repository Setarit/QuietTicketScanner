package com.setarit.quietticketscanner;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.setarit.quietticketscanner.ticket.ScanStatus;
import com.setarit.quietticketscanner.ticket.SeatCodeValidator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_seat_scan_result_controller)
@Fullscreen
public class SeatScanResultController extends FragmentActivity {
    @ViewById(R.id.scanResultContainer)
    public FrameLayout container;
    @ViewById
    public TextView seatName;
    @ViewById
    public TextView scanResult;

    private SeatCodeValidator seatCodeValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.seatCodeValidator = (SeatCodeValidator) getIntent().getSerializableExtra("VALIDATOR");
    }

    @AfterViews
    public void showResult(){
        changeBackgroundColor();
        showSeatName();
        showScanResult();
    }

    @Click
    public void resumeScanning(){
        finish();
    }

    private void showScanResult() {
        switch (seatCodeValidator.getLastScanStatus()){
            case ALREADY_SCANNED:
                scanResult.setText(R.string.ticketAlreadyScanned);
                break;
            case VALID:
                scanResult.setText(R.string.ticketValid);
                break;
            default:
                scanResult.setText(R.string.ticketInvalid);
                break;
        }
    }

    private void showSeatName() {
        seatName.setText(seatCodeValidator.getLastScannedSeat().getName());
    }

    private void changeBackgroundColor() {
        if(seatCodeValidator.getLastScanStatus() == ScanStatus.VALID){
            container.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccessMessage));
        }else{
            container.setBackgroundColor(ContextCompat.getColor(this, R.color.colorErrorMessage));
        }
    }


}
