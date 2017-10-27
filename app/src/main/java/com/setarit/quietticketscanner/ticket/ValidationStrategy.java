package com.setarit.quietticketscanner.ticket;

import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.preferences.Preferences;

/**
 * Created by Setarit on 22/10/2017.
 * Interface for verifying a code
 */

public interface ValidationStrategy {
    /**
     * Checks if the code is valid
     * @param code The code to validate
     * @return True if valid, else false
     */
    boolean isValid(String code);
    ScanStatus getLastScanStatus();
}
