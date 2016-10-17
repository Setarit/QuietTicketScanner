package com.setarit.quietticketscanner.permission;

/**
 * Created by Setarit on 17/10/2016.
 */

public interface PermissionRequestable {
    void requestPermission();
    /**
     * Checks if the app has the required permission
     * @return true If the app has the required permission
     */
    boolean hasPermission();
}
