package com.setarit.quietticketscanner.ticket;

import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.domain.parse.VisitorsFromJsonParser;
import com.setarit.quietticketscanner.domain.parse.VistorsToJsonParser;
import com.setarit.quietticketscanner.preferences.Preferences_;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Setarit on 22/10/2017.
 * Verifies if a ticket or vistor is valid
 */

public abstract class CodeValidator implements TicketValidatable, Serializable {    
    protected List<Visitor> visitors;
    protected ScanStatus scanStatus;
    protected Visitor lastScannedVisitor;
    private boolean hasScanned;


    public CodeValidator(String visitorJson) {
        VisitorsFromJsonParser parser = new VisitorsFromJsonParser(visitorJson);
        this.visitors = parser.convertFromJson();
    }

    /**
     * Updates the hasScanned flag in the preferences
     */
    protected void updateHasScanned(){
        this.hasScanned = true;
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

    public ScanStatus getLastScanStatus(){
        return this.scanStatus;
    }
    public Visitor getLastScannedVisitor() {return this.lastScannedVisitor; }

    public boolean hasScanned() {
        return hasScanned;
    }

    public List<Visitor> getVisitors() {
        return visitors;
    }
}
