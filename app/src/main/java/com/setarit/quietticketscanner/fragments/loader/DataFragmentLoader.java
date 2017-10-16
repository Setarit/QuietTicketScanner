package com.setarit.quietticketscanner.fragments.loader;

import android.app.FragmentManager;

import com.setarit.quietticketscanner.fragments.DataFragment;

/**
 * Created by Setarit on 16/10/2017.
 * Loads the data fragment
 */

public class DataFragmentLoader {
    private final FragmentManager fragmentManager;

    private static final String FRAGMENT_TAG = "DataFragment";

    public DataFragmentLoader(FragmentManager manager) {
        this.fragmentManager = manager;
    }

    /**
     * Loads the DataFragment
     * @return DataFragment The loaded data fragment
     */
    public DataFragment loadDataFragment(){
        DataFragment dataFragment = (DataFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG);
        if(dataFragment == null){//create if first time
            dataFragment = new DataFragment();
            fragmentManager.beginTransaction()
                    .add(dataFragment, FRAGMENT_TAG)
                    .commit();
        }
        return dataFragment;
    }
}
