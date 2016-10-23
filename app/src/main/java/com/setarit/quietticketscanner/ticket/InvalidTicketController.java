package com.setarit.quietticketscanner.ticket;

import android.os.Bundle;

import com.setarit.quietticketscanner.R;

public class InvalidTicketController extends TicketStatusController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invalid_ticket_controller);
        super.setServerMessage(getIntent().getStringExtra("message"));
        super.setToInvalid();
        super.activateResumeScanningButton();
    }
}
