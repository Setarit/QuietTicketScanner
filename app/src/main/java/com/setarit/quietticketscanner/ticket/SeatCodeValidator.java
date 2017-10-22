package com.setarit.quietticketscanner.ticket;

import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.preferences.Preferences_;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Setarit on 22/10/2017.
 * Validates a single seat ticket QR-code
 */

public class SeatCodeValidator extends CodeValidator {
    private HashMap<String, Seat> seats;

    public SeatCodeValidator(Preferences_ preferences) {
        super(preferences);
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
                scanStatus = ScanStatus.VALID;
                updateHasScannedInPreferences();
                Visitor visitor = findVisitorOfSeat(seat);
                Seat visitorSeat = null;
                Iterator<Seat> seatIterator = visitor.getSeats().iterator();
                while (seatIterator.hasNext()){
                    Seat current = seatIterator.next();
                    if(current.equals(seat)){
                        visitorSeat = seat;
                        break;
                    }
                }
                visitorSeat.setScanned(true);
                visitor.setWent(true);
                updateVisitorList(visitor);
            }else {
                scanStatus = ScanStatus.ALREADY_SCANNED;
            }
        }else{
            scanStatus = ScanStatus.INVALID;
        }
        return false;
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
}
