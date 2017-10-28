package com.setarit.quietticketscanner;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.service.ValidationFacade;
import com.setarit.quietticketscanner.ticket.ScanStatus;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_result_controller)
@Fullscreen
public class ResultController extends AppCompatActivity {
    @ViewById
    public TextView visitorName;
    @ViewById
    public TextView statusMessage;
    @ViewById
    public LinearLayout seatContainer;
    @ViewById
    public RelativeLayout statusScreen;

    private ValidationFacade facade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facade = (ValidationFacade) getIntent().getSerializableExtra("FACADE");
    }

    @AfterViews
    public void loadStatus(){
        setBackgroundColor();
        setVisitorName();
        if(facade.getLastScanStatus() == ScanStatus.VALID) {
            loadSeats();
        }
    }

    private void loadSeats() {
        for(Seat seat: facade.getLastScannedSeats()){
            LinearLayout view = new LinearLayout(this);
            view.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view.setOrientation(LinearLayout.HORIZONTAL);

            ImageView seatIcon = new ImageView(this);
            seatIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.seat));
            seatIcon.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.25f));

            TextView seatName = new TextView(this);
            seatName.setText(seat.getName()+" - "+seat.getPriceName());
            seatName.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 0.75f));

            view.addView(seatIcon);
            view.addView(seatName);

            seatContainer.addView(view);
        }
    }

    private void setVisitorName() {
        Visitor visitor = facade.getLastScannedVisitor();
        if(visitor == null){
            visitorName.setText("");
        }else{
            visitorName.setText(visitor.getFirstName()+" "+visitor.getLastName());
        }
    }

    private void setBackgroundColor() {
        switch (facade.getLastScanStatus()){
            case VALID:
                statusScreen.setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccessMessage));
                statusMessage.setText(this.getText(R.string.ticketValid));
                break;
            case INVALID:
                statusScreen.setBackgroundColor(ContextCompat.getColor(this, R.color.colorErrorMessage));
                statusMessage.setText(this.getText(R.string.ticketInvalid));
                break;
            default:
                statusScreen.setBackgroundColor(ContextCompat.getColor(this, R.color.colorWarningMessage));
                statusMessage.setText(this.getText(R.string.ticketAlreadyScanned));
                break;
        }
    }

    @Click
    public void finishResultActivity(){
        finish();
    }
}
