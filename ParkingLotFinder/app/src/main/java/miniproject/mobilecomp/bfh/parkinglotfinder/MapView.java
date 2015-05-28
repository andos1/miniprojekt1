package miniproject.mobilecomp.bfh.parkinglotfinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class MapView extends Activity {

    // Google Map
    private GoogleMap googleMap;
    private double longitude, latitude;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapview);
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        longitude=intent.getDoubleExtra("longitude", 7);
        latitude=intent.getDoubleExtra("latitude",47);

        try {
            // Loading map
            initializeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initializeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            else{
                googleMap.setMyLocationEnabled(true);
                MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude,longitude)).title(name);
                googleMap.addMarker(marker);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent=getIntent();
        name=intent.getStringExtra("name");
        longitude=intent.getDoubleExtra("longitude", 7);
        latitude=intent.getDoubleExtra("latitude",47);
        initializeMap();
    }
}
