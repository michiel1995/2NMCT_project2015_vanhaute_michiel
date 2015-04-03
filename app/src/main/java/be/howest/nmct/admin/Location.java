package be.howest.nmct.admin;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;


import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;

/**
 * Created by Michiel on 2/04/2015.
 */
public class Location {
    Context context;

    public Location(Context context){
        this.context = context;
    }
    public Double[] getLastKnownLocation() {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        /* Loop over the array backwards, and if you get an accurate location, then break out the loop*/
        android.location.Location l = null;

        for (int i=providers.size()-1; i>=0; i--) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null) break;
        }

        Double[] gps = new Double[2];
        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;
    }
    public String[] getLocation(LatLng latlng) throws IOException {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        addresses = geocoder.getFromLocation(latlng.latitude,latlng.longitude,1);

        String[] arr = new String[3];

        arr[0] = addresses.get(0).getAddressLine(0);
        arr[1] = addresses.get(0).getAddressLine(1);
        arr[2] = addresses.get(0).getAddressLine(2);
        return arr;
    }


}
