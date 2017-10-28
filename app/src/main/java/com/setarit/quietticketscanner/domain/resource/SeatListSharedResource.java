package com.setarit.quietticketscanner.domain.resource;

import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.Visitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Setarit on 27/10/2017.
 * Shared resource holding the seat list
 */

public class SeatListSharedResource implements Serializable {
    private Map<String, Seat> seatMap;
    private Visitor lastScannedVisitor;
    private List<Seat> lastScannedSeats;

    public SeatListSharedResource(List<Seat> seats) {
        loadSeatMap(seats);
    }

    private void loadSeatMap(List<Seat> seats) {
        seatMap = new HashMap<>(seats.size());
        for(Seat seat: seats){
            seatMap.put(seat.getId(), seat);
        }
    }

    /**
     * Returns the latest version of the list of seats
     * @return The list of seats
     */
    public synchronized Collection<Seat> getAllSeats(){
        return seatMap.values();
    }

    /**
     * Searches for the visitor based on the bankingCode
     * @param bankingCode The banking code
     * @return Null if none found or the visitor if found
     */
    public Visitor findVisitor(long bankingCode){
        Visitor result = null;
        for(Seat seat: seatMap.values()){
            result = seat.getVisitor();
            if(result != null && result.getBankingCode() == bankingCode){
                break;
            }
        }
        return result;
    }

    /**
     * Sets all the seats of a visitor to scanned
     * @param visitor The visitor whose seats should be set to scanned
     * @return List of the seats linked to the visitor
     */
    public List<Seat> setAllSeatsScanned(Visitor visitor){
        Visitor current = null;
        List<Seat> seatsOfVisitor = new ArrayList<>();
        for(Seat seat: seatMap.values()){
            current = seat.getVisitor();
            if(current != null && current.equals(visitor)){
                seat.setScanned(true);
                current.setWent(true);
                seatsOfVisitor.add(seat);
            }
        }
        return seatsOfVisitor;
    }

    public Visitor getLastScannedVisitor() {
        return lastScannedVisitor;
    }

    public void setLastScannedVisitor(Visitor lastScannedVisitor) {
        this.lastScannedVisitor = lastScannedVisitor;
    }

    public List<Seat> getLastScannedSeats() {
        return lastScannedSeats;
    }

    public void setLastScannedSeats(List<Seat> lastScannedSeats) {
        this.lastScannedSeats = lastScannedSeats;
    }

    /**
     * Searches for a code
     * @param code The code of the seat we are searching for
     * @return The seat if found or null if not found
     */
    public Seat findSeat(String code) {
        return seatMap.get(code);
    }
}
