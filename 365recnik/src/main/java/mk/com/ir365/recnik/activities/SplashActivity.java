package mk.com.ir365.recnik.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;

import mk.com.ir365.recnik.R;

public class SplashActivity extends Activity{

    private static final int DURATION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {

            @SuppressWarnings("deprecation")
            @Override
            public void run() {



                if (isNetworkAvailable()) {
                    finish();
                    startActivity(new Intent(SplashActivity.this,
                            MainActivity.class));

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(
                            SplashActivity.this).create();

                    // Setting Dialog Title
                    alertDialog.setTitle("Речник 365");

                    // Setting Dialog Message
                    alertDialog
                            .setMessage("Потребна е интернет конекција за да работи апликацијата");

                    // Setting OK Button
                    alertDialog.setButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    finish();
                                }
                            });

                    // Showing Alert Message
                    alertDialog.show();
                }

            }
        }, DURATION);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
