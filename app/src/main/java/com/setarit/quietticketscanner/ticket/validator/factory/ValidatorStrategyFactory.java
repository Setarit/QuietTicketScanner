package com.setarit.quietticketscanner.ticket.validator.factory;

import com.setarit.quietticketscanner.domain.resource.SeatListSharedResource;
import com.setarit.quietticketscanner.ticket.CodeType;
import com.setarit.quietticketscanner.ticket.ValidationStrategy;
import com.setarit.quietticketscanner.ticket.validator.SeatCodeValidator;
import com.setarit.quietticketscanner.ticket.validator.VisitorCodeValidator;

/**
 * Created by Setarit on 27/10/2017.
 * Factory for creating the validator
 */

public class ValidatorStrategyFactory {
    /**
     * Creates the correct validator
     * @param codeType The type of the code passed
     * @param sharedResource The shared resource where the code should be found
     * @return The validation strategy
     */
    public static ValidationStrategy create(CodeType codeType, SeatListSharedResource sharedResource){
        ValidationStrategy created = null;
        switch (codeType){
            case SEAT_CODE:
                created = new SeatCodeValidator(sharedResource);
                break;
            case VISITOR_CODE:
                created = new VisitorCodeValidator(sharedResource);
                break;
            default:
                break;
        }
        return created;
    }
}
