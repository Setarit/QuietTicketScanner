package com.setarit.quietticketscanner.ticket.helper;

import com.setarit.quietticketscanner.ticket.CodeType;

/**
 * Created by Setarit on 27/10/2017.
 * Checks the type of the code
 */

public class CodeTypeHelper {
    private String code;
    private CodeType type;

    public CodeTypeHelper(String code) {
        this.code = code;
        sanitizeCode();
        determineCodeType();
    }

    private void sanitizeCode() {
        code = code.toLowerCase();
        if(code.startsWith("sqt-")){
            code = code.substring(4);
        }
        code = code.replaceAll("-","");
    }

    private void determineCodeType(){
        try{
            Long.parseLong(code);
            type = CodeType.VISITOR_CODE;
        }catch (NumberFormatException e){
            type = CodeType.SEAT_CODE;
        }
    }

    /**
     * Getter
     * @return The sanitized code
     */
    public String getSanitizedCode() {
        return code;
    }

    public CodeType getType() {
        return type;
    }
}
