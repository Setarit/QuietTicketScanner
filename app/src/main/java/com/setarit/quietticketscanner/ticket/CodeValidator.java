package com.setarit.quietticketscanner.ticket;

import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.domain.parse.VisitorsFromJsonParser;
import com.setarit.quietticketscanner.domain.parse.VistorsToJsonParser;
import com.setarit.quietticketscanner.preferences.Preferences_;

import java.util.List;

/**
 * Created by Setarit on 22/10/2017.
 * Verifies if a ticket or vistor is valid
 */

public abstract class CodeValidator implements TicketValidatable {
    private Preferences_ preferences;
    protected List<Visitor> visitors;
    protected ScanStatus scanStatus;


    public CodeValidator(Preferences_ preferences) {
        this.preferences = preferences;
        loadVisitors();
    }

    private void loadVisitors() {
        VisitorsFromJsonParser parser = new VisitorsFromJsonParser(preferences.visitorsJson().get());
        this.visitors = parser.convertFromJson();
    }


    /**
     * Updates the visitor JSON in the preferences
     */
    protected void updateVisitorsJsonInPreferences(){
        VistorsToJsonParser parser = new VistorsToJsonParser(visitors);
        String visitorsAsString = parser.convertToJson();
        preferences.visitorsJson().put(visitorsAsString);
    }

    /**
     * Updates the hasScanned flag in the preferences
     */
    protected void updateHasScannedInPreferences(){
        preferences.hasScanned().put(true);
    }

    /**
     * Updates the in-memory list of the visitor
     * Updates the scanned or went field
     * @param visitor The visitor to update
     */
    protected void updateVisitorList(Visitor visitor) {
        int updateIndex = visitors.indexOf(visitor);
        visitors.set(updateIndex, visitor);
    }

    protected void updateScanStatus(ScanStatus scanStatus){
        this.scanStatus = scanStatus;
    }

    public ScanStatus getLastScanStatus(){
        return this.scanStatus;
    }
}
