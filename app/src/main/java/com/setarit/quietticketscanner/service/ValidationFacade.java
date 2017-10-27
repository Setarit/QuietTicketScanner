package com.setarit.quietticketscanner.service;

import com.setarit.quietticketscanner.domain.Seat;
import com.setarit.quietticketscanner.domain.Visitor;
import com.setarit.quietticketscanner.domain.parse.FromJsonParser;
import com.setarit.quietticketscanner.domain.parse.SeatsFromJsonParser;
import com.setarit.quietticketscanner.domain.resource.SeatListSharedResource;
import com.setarit.quietticketscanner.ticket.CodeType;
import com.setarit.quietticketscanner.ticket.ScanStatus;
import com.setarit.quietticketscanner.ticket.ValidationStrategy;
import com.setarit.quietticketscanner.ticket.helper.CodeTypeHelper;
import com.setarit.quietticketscanner.ticket.validator.factory.ValidatorStrategyFactory;

import java.util.List;

/**
 * Created by Setarit on 27/10/2017.
 * Facade for the validation
 */

public class ValidationFacade {
    private SeatListSharedResource sharedResource;
    private ValidationStrategy strategy;

    public ValidationFacade(String seatsJson) {
        loadSeatsFromString(seatsJson);
    }

    private void loadSeatsFromString(String seatsJson) {
        SeatsFromJsonParser parser = new SeatsFromJsonParser(seatsJson);
        sharedResource = new SeatListSharedResource(parser.convertFromJson());
    }

    public boolean validate(String code){
        CodeTypeHelper helper = new CodeTypeHelper(code);
        strategy = ValidatorStrategyFactory.create(helper.getType(), sharedResource);
        return  strategy.isValid(helper.getSanitizedCode());
    }

    public Visitor getLastScannedVisitor(){
        return sharedResource.getLastScannedVisitor();
    }

    public List<Seat> getLastScannedSeats(){
        return sharedResource.getLastScannedSeats();
    }

    public boolean isVisitorCode(String code){
        CodeTypeHelper helper = new CodeTypeHelper(code);
        return helper.getType() == CodeType.VISITOR_CODE;
    }

    public ScanStatus getLastScanStatus(){
        return strategy.getLastScanStatus();
    }
}
