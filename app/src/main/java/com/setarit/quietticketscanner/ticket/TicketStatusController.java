package com.setarit.quietticketscanner.ticket;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.setarit.quietticketscanner.R;
import com.setarit.quietticketscanner.view.listeners.ContinueScanningButtonClickListener;

/**
 * Created by Setarit on 23/10/2016.
 * Base class fot controllers that show the result of scanning a ticket
 */
public class TicketStatusController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.scan);
    }

    /**
     * Sets the server message in the view
     * This message contains the seat number or error message
     * @param message The message to display
     */
    public void setServerMessage(String message) {
        TextView serverMessage = (TextView) findViewById(R.id.ticket_server_message);
        if(serverMessage != null){
            serverMessage.setText(message);
        }
    }

    /**
     * Changes the view to the invalid status
     * changes the background color and status image
     */
    protected void setToInvalid(){
        findViewById(R.id.ticket_server_response_container).setBackgroundColor(ContextCompat.getColor(this, R.color.colorErrorMessage));
        ((ImageView) findViewById(R.id.status_image)).setImageResource(R.drawable.image_nok);
    }

    /**
     * Changes the view to the valid status
     * changes the background color and status image
     */
    protected void setToValid(){
        findViewById(R.id.ticket_server_response_container).setBackgroundColor(ContextCompat.getColor(this, R.color.colorSuccessMessage));
        ((ImageView) findViewById(R.id.status_image)).setImageResource(R.drawable.image_ok);
    }

    /**
     * Changes the view to the warning status
     * changes the background color and status image
     */
    protected void setToWarning(){
        findViewById(R.id.ticket_server_response_container).setBackgroundColor(ContextCompat.getColor(this, R.color.colorWarningMessage));
        ((ImageView) findViewById(R.id.status_image)).setImageResource(R.drawable.image_nok);
    }

    public void activateResumeScanningButton() {
        if(findViewById(R.id.continue_scanning) != null) {
            findViewById(R.id.continue_scanning).setOnClickListener(new ContinueScanningButtonClickListener(this));
        }
    }

    public void resumeScanning() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        resumeScanning();
    }
}
