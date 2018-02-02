package com.setarit.quietticketscanner.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.setarit.quietticketscanner.R;
import com.setarit.quietticketscanner.SaveFileController_;
import com.setarit.quietticketscanner.ScanController_;
import com.setarit.quietticketscanner.ScanFileLoaderController_;
import com.setarit.quietticketscanner.preferences.Preferences_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.sharedpreferences.Pref;

@EFragment(R.layout.fragment_step)
public class StepFragment extends Fragment {
    @Pref
    public Preferences_ preferences;

    public StepFragment() {
        // Required empty public constructor
    }

    @Click
    public void openJsonButton(){
        Intent intent = new Intent(getActivity(), ScanFileLoaderController_.class);
        getActivity().startActivity(intent);
        finishParentActivity();
    }

    @Click
    public void scanButton(){
        if(preferences.seatsJson().exists()){
            Intent intent = new Intent(getActivity(), ScanController_.class);
            getActivity().startActivity(intent);
        }
    }

    @Click
    public void saveJsonButton(){
        if(preferences.hasScanned().get()) {
            Intent intent = new Intent(getActivity(), SaveFileController_.class);
            getActivity().startActivity(intent);
            finishParentActivity();
        }
    }

    private void finishParentActivity(){
        getActivity().finish();
    }
}
