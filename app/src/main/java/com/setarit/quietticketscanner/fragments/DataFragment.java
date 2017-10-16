package com.setarit.quietticketscanner.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;

import com.setarit.quietticketscanner.domain.ScanFile;

/**
 * Created by Setarit on 16/10/2017.
 * Holds the data over multiple activities
 */

public class DataFragment extends Fragment {
    private ScanFile scanFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public ScanFile getScanFile() {
        return scanFile;
    }

    public void setScanFile(ScanFile scanFile) {
        this.scanFile = scanFile;
    }
}
