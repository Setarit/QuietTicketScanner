package com.setarit.quietticketscanner.view.listeners;

import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * Created by Setarit on 02/10/2016.
 * Listens for the enter button in the token view
 */

public class TokenSubmitListener implements TextView.OnEditorActionListener {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEND){

            return true;
        }
        return false;
    }
}
