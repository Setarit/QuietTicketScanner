package com.setarit.quietticketscanner.ticket;

import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.preferences.Preferences_;

import java.util.HashMap;

/**
 * Created by Setarit on 22/10/2017.
 * Validates the global ticket code
 */

public class VisitorCodeValidator extends CodeValidator {
    private HashMap<Long, Visitor> visitorMap;

    public VisitorCodeValidator(Preferences_ preferences) {
        super(preferences);
        mapToHashMap();
    }

    private void mapToHashMap() {
        this.visitorMap = new HashMap<Long, Visitor>(visitors.size());
        for (Visitor visitor: visitors){
            visitorMap.put(visitor.getBankingCode(), visitor);
        }
    }

    @Override
    public boolean isValid(String code) {
        boolean valid = false;
        Visitor visitor = visitorMap.get(Long.parseLong(code));
        if(visitor != null){
            if(!visitor.isWent()) {
                handleValidCode(visitor);
                valid = true;
            }else{
                scanStatus = ScanStatus.ALREADY_SCANNED;
            }
        }else{
            scanStatus = ScanStatus.INVALID;
        }
        return valid;
    }

    private void handleValidCode(Visitor visitor) {
        visitor.setWent(true);
        visitor.setAllSeatsScanned();
        updateVisitorList(visitor);
        scanStatus = ScanStatus.VALID;
        updateHasScannedInPreferences();
    }
}
