package com.setarit.quietticketscanner.ticket;

import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.Visitor;

import java.util.HashMap;

/**
 * Created by Setarit on 22/10/2017.
 * Validates a single seat ticket QR-code
 */

public class SeatCodeValidator extends CodeValidator {
    private HashMap<String, Seat> seats;
    private Seat lastScannedSeat;

    public SeatCodeValidator(String visitorJson) {
        super(visitorJson);
        loadSeatsToMap();
    }


    private void loadSeatsToMap() {
        this.seats = new HashMap<String, Seat>();
        for(Visitor visitor: visitors){
            for(Seat seat: visitor.getSeats()){
                seats.put(seat.getId(), seat);
            }
        }
    }

    @Override
    public boolean isValid(String code) {
        boolean valid = false;
        Seat seat = seats.get(code);
        if(seat != null){
            if(seat.isScanned()){
                handleValidCode(seat);
                valid = true;
            }else {
                scanStatus = ScanStatus.ALREADY_SCANNED;
            }
        }else{
            scanStatus = ScanStatus.INVALID;
        }
        return valid;
    }

    private void handleValidCode(Seat seat) {
        scanStatus = ScanStatus.VALID;
        lastScannedSeat = seat;
        updateHasScanned();
        Visitor visitor = findVisitorOfSeat(seat);
        Seat visitorSeat = null;
        for (Seat current : visitor.getSeats()) {
            if (current.equals(seat)) {
                visitorSeat = seat;
                break;
            }
        }
        if(visitorSeat != null) {
            lastScannedVisitor = visitor;
            visitorSeat.setScanned(true);
            updateVisitorList(visitor);
        }
    }

    private Visitor findVisitorOfSeat(Seat seat) {
        Visitor foundVisitor = null;
        for(Visitor visitor: visitors){
            if(visitor.getSeats().contains(seat)){
                foundVisitor = visitor;
                break;
            }
        }
        return foundVisitor;
    }

    public Seat getLastScannedSeat() {
        return lastScannedSeat;
    }
}
