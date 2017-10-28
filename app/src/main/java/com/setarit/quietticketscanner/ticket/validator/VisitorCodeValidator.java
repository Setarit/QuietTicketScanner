package com.setarit.quietticketscanner.ticket.validator;

import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.domain.resource.SeatListSharedResource;
import com.setarit.quietticketscanner.ticket.ScanStatus;
import com.setarit.quietticketscanner.ticket.ValidationStrategy;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Setarit on 27/10/2017.
 * Validates a visitor code
 */

public class VisitorCodeValidator implements ValidationStrategy, Serializable {
    private SeatListSharedResource seatListSharedResource;
    private ScanStatus scanStatus;

    public VisitorCodeValidator(SeatListSharedResource seatListSharedResource) {
        this.seatListSharedResource = seatListSharedResource;
    }

    @Override
    public boolean isValid(String code) {
        boolean valid = false;
        Visitor found = seatListSharedResource.findVisitor(Long.parseLong(code));
        if(found == null){
            scanStatus = ScanStatus.INVALID;
        }else{
            if(found.isWent()){
                scanStatus = ScanStatus.ALREADY_SCANNED;
            }else{
                seatListSharedResource.setLastScannedVisitor(found);
                seatListSharedResource.setLastScannedSeats(seatListSharedResource.setAllSeatsScanned(found));
                valid = true;
                scanStatus = ScanStatus.VALID;
            }
        }
        return valid;
    }

    @Override
    public ScanStatus getLastScanStatus() {
        return scanStatus;
    }
}
