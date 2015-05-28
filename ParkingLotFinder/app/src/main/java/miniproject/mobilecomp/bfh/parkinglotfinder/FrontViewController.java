package miniproject.mobilecomp.bfh.parkinglotfinder;

import android.app.Activity;
import android.content.Intent;
import android.location.Criteria;
import android.location.LocationManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stefan on 01.05.15.
 */
public class FrontViewController extends Activity {

    private TextView currentLocationTv;
    private ListView locationList;
    private List<String> strList;
    private ArrayAdapter<String> adapter;
    private Backend back;
    @Override
    protected void onStart(){
        super.onStart();

        strList=new ArrayList<>();
        adapter= new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,strList);
        android.location.Location location = getCurrentLocation();
        List<String> locations;

        //load xml layout file
        setContentView(R.layout.frontview);

        currentLocationTv=(TextView)findViewById(R.id.currentLocation);

        currentLocationTv.append("\n"+location.toString());

        locationList=(ListView)findViewById(R.id.locationlist);


        back= new Backend();
        locationList.setAdapter(adapter);
        locationList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(FrontViewController.this, MapView.class);
                Location loc=back.getLocation(((TextView)view).getText().toString());
                intent.putExtra("name",loc.toString());
                intent.putExtra("longitude", loc.getLongitude());
                intent.putExtra("latitude", loc.getLatitude());
                startActivity(intent);
            }
        });

        back.getLocationsNear(location,this);



    }

    public android.location.Location getCurrentLocation(){
        LocationManager lManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        String foo = lManager.getBestProvider(criteria,true);

        android.location.Location location = null;

        if(foo!=null) {
            location = lManager.getLastKnownLocation(foo);
        }

        return location;
    }

    public void addString(String s){


        strList.add(s);
        //adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,strList);
        adapter.notifyDataSetChanged();
    }



}