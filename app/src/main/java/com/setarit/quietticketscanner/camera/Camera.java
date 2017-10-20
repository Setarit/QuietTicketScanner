package com.setarit.quietticketscanner.camera;

import android.annotation.TargetApi;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Setarit on 20/10/2017.
 * Wrapper for handling the camera
 */

public class Camera {
    private CameraManager cameraManager;

    public Camera(CameraManager cameraManager) {
        this.cameraManager = cameraManager;
    }


    public String[] getCameras() throws CameraAccessException {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ){
            return getCameraList();
        }else{
            return getDeprecatedCameraList();
        }

    }

    @TargetApi(21)
    private String[] getCameraList() throws CameraAccessException {
        return cameraManager.getCameraIdList();
    }


    private String[] getDeprecatedCameraList() {
        int numberOfCameras = android.hardware.Camera.getNumberOfCameras();
        String[] cameraIds = new String[numberOfCameras];
        for (int current = 0; current < numberOfCameras; current++){
            cameraIds[current] = ""+current;
        }
        return cameraIds;
    }
}
