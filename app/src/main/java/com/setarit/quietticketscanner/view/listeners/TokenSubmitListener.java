package com.setarit.quietticketscanner.view.listeners;

import android.view.KeyEvent;
import android.widget.TextView;

import com.setarit.quietticketscanner.TokenController;
import com.setarit.quietticketscanner.network.verification.TokenVerificator;

import static android.view.inputmethod.EditorInfo.IME_ACTION_SEND;

/**
 * Created by Setarit on 02/10/2016.
 * Listens for the enter button in the token view
 */

public class TokenSubmitListener implements TextView.OnEditorActionListener {
    private TokenController context;
    
    public TokenSubmitListener(TokenController context){
        this.context = context;
    }
    
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == IME_ACTION_SEND){
            TokenVerificator verificator = new TokenVerificator(context);
            verificator.verify(v.getText().toString());
            return true;
        }
        return false;
    }
}
