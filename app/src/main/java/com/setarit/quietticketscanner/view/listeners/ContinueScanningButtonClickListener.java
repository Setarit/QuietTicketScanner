package com.setarit.quietticketscanner.view.listeners;

import android.view.View;

import com.setarit.quietticketscanner.ticket.TicketStatusController;

/**
 * Created by Setarit on 23/10/2016.
 * Listens if the continue button is clicked after showing the scan result from the server
 */
public class ContinueScanningButtonClickListener implements View.OnClickListener {
    private final TicketStatusController ticketStatusController;

    public ContinueScanningButtonClickListener(TicketStatusController ticketStatusController) {
        this.ticketStatusController = ticketStatusController;
    }

    @Override
    public void onClick(View v) {
        ticketStatusController.resumeScanning();
    }
}
