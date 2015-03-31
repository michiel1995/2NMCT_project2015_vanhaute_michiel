package be.howest.nmct.beerprice;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Created by Michiel on 31/03/2015.
 */
public class ShowOnMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String PARAM_LAT = "be.howest.nmct.beerpicer.PARAM_LAT";
    public static final String PARAM_LON = "be.howest.nmct.beerpicer.PARAM_LON";

    private boolean single = false;
    private static Double latitude;
    private static Double longitude;


    public static ShowOnMapFragment newInstance(float latitude, float longitude)
    {
        ShowOnMapFragment frag = new ShowOnMapFragment();
        Bundle arg = new Bundle();
        arg.putDouble(PARAM_LAT, latitude);
        arg.putDouble(PARAM_LON, longitude);
        frag.setArguments(arg);
        return frag;
    }
    public ShowOnMapFragment(){

    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.latitude = getArguments().getDouble(PARAM_LAT);
            this.longitude = getArguments().getDouble(PARAM_LON);
            single = true;
        }


    }


   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_on_map, container, false);
        MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
       mapFragment.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng place = new LatLng(latitude, longitude);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(place, 15));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(place));
    }




}
