package com.setarit.quietticketscanner.ticket;

import android.os.Bundle;

import com.setarit.quietticketscanner.R;

public class TicketAlreadyScannedController extends TicketStatusController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_already_scanned_controller);
        super.setServerMessage(getIntent().getStringExtra("message"));
        super.setToWarning();
        super.activateResumeScanningButton();
    }
}
