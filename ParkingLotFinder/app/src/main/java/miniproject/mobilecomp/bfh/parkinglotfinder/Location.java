package miniproject.mobilecomp.bfh.parkinglotfinder;

/**
 * Created by Tina on 28.05.2015.
 */
public class Location {
    private String name;
    private double longitude;
    private double latitude;

    public Location(String n, double la, double lo){
        name=n;
        longitude=lo;
        latitude=la;
    }
    public String toString(){
        return name;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

}
