package miniproject.mobilecomp.bfh.parkinglotfinder;


import android.app.Application;
import android.util.Log;

import com.baasbox.android.BaasBox;
import com.baasbox.android.BaasDocument;
import com.baasbox.android.BaasHandler;
import com.baasbox.android.BaasResult;
import com.baasbox.android.BaasUser;
import com.baasbox.android.RequestToken;

import java.util.HashMap;
import java.util.List;

/**
 * Created by stefan on 22.05.15.
 */
public class Backend extends Application {

    private BaasBox client;
    private String domain = "vmbackend.bfh.ch",
            appcode = "2501",
            username = "admin",
            pw = "mobile15";

    private FrontViewController a;
    private HashMap<String,Location> locations=new HashMap<>();
    private  RequestToken token;
    private RequestToken mRefresh;
    private void login(){


        BaasUser user= BaasUser.withUserName(username);
        user.setPassword(pw);
        token = user.login(onComplete);
    }

    private final BaasHandler<BaasUser> onComplete =
            new BaasHandler<BaasUser>() {
                @Override
                public void handle(BaasResult<BaasUser> result) {
                    token = null;
                    if (result.isFailed()) {
                        Log.d("ERROR", "ERROR", result.error());
                    }
                    else{
                        Log.d("WIN", "WIN", result.error());
                    }
                }
            };

    public void getLocationsNear(android.location.Location location,FrontViewController a){

        this.a=a;


        BaasHandler handler = new BaasHandler<List<BaasDocument>>() {
            @Override
            public void handle(BaasResult<List<BaasDocument>> listBaasResult) {
                if(listBaasResult.isSuccess())
                    for(BaasDocument doc:listBaasResult.value()) {
                        Backend.this.addLocation(doc.getString("name"),doc.getString("latitude"),doc.getString("longitude"));
                        Backend.this.a.addString(doc.getString("name"));

                    }
                else
                    Log.d("ERROR", "FOOBAR", listBaasResult.error());

            }
        };


        BaasBox.Builder b = new BaasBox.Builder(a);
        client = b.setApiDomain(domain)
                .setAppCode(appcode)
                .setAuthentication(BaasBox.Config.AuthType.SESSION_TOKEN)
                .setPort(9000)
                .init();

        login();



        token = mRefresh =BaasDocument.fetchAll("group-gerber",handler);



       /* client.rest(HttpRequest.GET,
                "distance(latitude,longitude,"+location.getLatitude()+"," +location.getLongitude()+" < 5000",
                new JsonArray(),
                true,
                handler
           );*/

    }

    private void addLocation(String name, String latitude, String longitude) {
        locations.put(name,new Location(name,Double.parseDouble(latitude),Double.parseDouble(longitude)));
    }
    public Location getLocation(String s){
        return locations.get(s);
    }

}