package com.setarit.quietticketscanner.fragments;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.setarit.quietticketscanner.R;
import com.setarit.quietticketscanner.ScanFileLoaderController;
import com.setarit.quietticketscanner.ScanFileLoaderController_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_step)
public class StepFragment extends Fragment {

    public StepFragment() {
        // Required empty public constructor
    }

    @Click
    public void openJsonButton(){
        Intent intent = new Intent(getActivity(), ScanFileLoaderController_.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        getActivity().startActivity(intent);
    }
}
