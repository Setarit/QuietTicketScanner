package com.setarit.quietticketscanner.ticket;

import android.os.Bundle;

import com.setarit.quietticketscanner.R;

public class ValidTicketController extends TicketStatusController {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valid_ticket_controller);
        super.setServerMessage(getIntent().getStringExtra("seat"));
        super.setToValid();
        super.activateResumeScanningButton();
    }
}
