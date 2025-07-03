package com.example.maplocation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import android.*;
import android.widget.Button;
import android.widget.EditText;

// change language

public class MapLocation extends AppCompatActivity {
    private final String TAG = "MapLocation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Edit language
        LocaleHelper.setLocale(this, "en");

        // Set content view
        setContentView(R.layout.main);

        EdgeToEdge.enable(this);

        // Initialize UI elements
        final EditText addrText = (EditText) findViewById(R.id.location);
        final Button button = (Button) findViewById(R.id.mapButton);

        // Link UI elements to actions in code
        button.setOnClickListener(new OnClickListener() {
            // Called when user clicks the Show Map button
            public void onClick(View v) {
                try {
                    // Process text for network transmission
                    String address = addrText.getText().toString();
                    address = address.replace(' ', '+');

                    // Tạo một Intent với hành động ACTION_VIEW
                    // yêu cầu mở một liên kết Google Maps application.
                    // "geo:0,0?q=" + address là một lệnh để Google Maps tìm kiếm địa điểm
                    // geo:0,0 nghĩa là không chỉ định tọa độ cụ thể, chỉ đơn giản tìm kiếm địa chỉ.
                    Intent geoIntent = new Intent(
                            android.content.Intent.ACTION_VIEW, Uri
                            .parse("geo:0,0?q=" + address));

                    // Use the Intent to start Google Maps application using Activity.startActivity()
                    // Mở Google Maps
                    startActivity(geoIntent);

                } catch (Exception e) {
                    // Log any error messages to LogCat using Log.e()
                    Log.e(TAG, e.toString());
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.RelativeLayout1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "The activity is visible and about to be started.");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "The activity is visible and about to be restarted.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "The activity is and has focus (it is now \"resumed\")");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,
                "Another activity is taking focus (this activity is about to be \"paused\")");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "The activity is no longer visible (it is now \"stopped\")");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "The activity is about to be destroyed.");
    }
}



