package com.example.hl4350hb.map_intent;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Sets global variable.
    static final String TAG = "Map Intent";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Gets references to widgets.
        final EditText mapSearchBox = (EditText) findViewById(R.id.map_search_box);
        Button mapSearchButton = (Button) findViewById(R.id.map_search_button);

        // Event listener for button.
        mapSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieves text from widget.
                String mapSearchString = mapSearchBox.getText().toString();

                // Validates something is entered.
                if (mapSearchString.length() == 0) {
                    Snackbar.make(findViewById(android.R.id.content), "Enter a location", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                // Creates new geocoder.
                Geocoder geocoder = new Geocoder(MainActivity.this);

                // Exception handler.
                try {
                    // Creates list of something.
                    List<Address> addressList = geocoder.getFromLocationName(mapSearchString, 1);
                    // Checks only the first result will be returned.
                    if (addressList.size() == 1) {
                        Address firstAddress = addressList.get(0);

                        Log.d(TAG, "First address is " + firstAddress);

                        // Formats string.
                        String geoUriString = String.format("geo:%f,%f", firstAddress.getLatitude(), firstAddress.getLongitude());
                        Log.d(TAG, "Geo URI string is " + geoUriString);

                        // Converts variable.
                        Uri geoUri = Uri.parse(geoUriString);
                        // Creates new Intent to map.
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, geoUri);

                        // Displays a Toast.
                        Toast.makeText(MainActivity.this, "Launching Map", Toast.LENGTH_LONG);
                        startActivity(mapIntent);
                    } else {
                        Snackbar.make(findViewById(android.R.id.content), "No results found for that location", Snackbar.LENGTH_LONG).show();
                    }
                }
                // Exception for entry errors.
                catch (IOException ioe) {
                    Log.e(TAG, "Error during geocoding", ioe);
                    Snackbar.make(findViewById(android.R.id.content), "Sorry, an error occurred", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }
}
