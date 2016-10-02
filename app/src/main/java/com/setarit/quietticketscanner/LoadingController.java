package com.setarit.quietticketscanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.setarit.quietticketscanner.async.AsyncServerConnectionChecker;

public class LoadingController extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_controller);
        setTitle(R.string.title_activity_loading_controller);
        verifyServerConnection();
    }

    private void verifyServerConnection() {
        AsyncServerConnectionChecker serverConnectionChecker = new AsyncServerConnectionChecker(this);
        serverConnectionChecker.execute();
    }

    public void showNoNetworkConnectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.network);
        builder.setMessage(R.string.cannotConnectToServer);
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();

        dialog.show();
    }


    public void toScanningCodeActivity() {
        Intent intent = new Intent();
        startActivity(new Intent(this, TokenController.class));
        finish();
    }
}
