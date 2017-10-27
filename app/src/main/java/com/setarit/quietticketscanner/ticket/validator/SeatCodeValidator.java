package com.setarit.quietticketscanner.ticket.validator;

import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.resource.SeatListSharedResource;
import com.setarit.quietticketscanner.ticket.ScanStatus;
import com.setarit.quietticketscanner.ticket.ValidationStrategy;

import java.util.Collections;

/**
 * Created by Setarit on 27/10/2017.
 * Validates a seat code
 */

public class SeatCodeValidator implements ValidationStrategy {
    private SeatListSharedResource seatListSharedResource;
    private ScanStatus status;

    public SeatCodeValidator(SeatListSharedResource seatListSharedResource) {
        this.seatListSharedResource = seatListSharedResource;
    }

    @Override
    public boolean isValid(String code) {
        Seat found = seatListSharedResource.findSeat(code);
        seatListSharedResource.setLastScannedSeats(Collections.singletonList(found));
        boolean valid = false;
        if(found == null){
            status = ScanStatus.INVALID;
        }else{
            if(found.isScanned()){
                status = ScanStatus.ALREADY_SCANNED;
            }else{
                status = ScanStatus.VALID;
                valid = true;
            }
        }
        return valid;
    }

    @Override
    public ScanStatus getLastScanStatus() {
        return status;
    }
}
