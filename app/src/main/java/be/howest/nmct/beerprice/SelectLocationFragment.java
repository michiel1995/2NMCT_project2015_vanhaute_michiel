package be.howest.nmct.beerprice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import be.howest.nmct.admin.BeerPrice;
import be.howest.nmct.admin.Location;

/**
 * Created by Michiel on 2/04/2015.
 */
public class SelectLocationFragment extends Fragment implements OnMapReadyCallback {
    public interface OnSendLocation{
        void onSendBackLocation(LatLng latLng);
    }
    private OnSendLocation sendLocation;
    private Double longitudeCurrentLocation;
    private Double latitudeCurrentLocation;
    private LatLng latlng;

    private Button btnOk;
    private Button btnCancel;
    private Boolean enable = true;
    private Marker marker;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            sendLocation = (OnSendLocation) activity;
        }catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnStudentListItem");
        }

    }

    @Override
    public void onMapReady(final GoogleMap map) {
        LatLng zoomTo = new LatLng(latitudeCurrentLocation, longitudeCurrentLocation);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomTo, 15));
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(enable) {
                    enable = false;
                    setButtonsVisible();
                    latlng = latLng;
                    setPin(map);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_location,container,false);
        initializeMap();
        getLocation();
        btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enable = true;
                setButtonsHidden();
                marker.remove();
            }
        });
        btnOk = (Button) v.findViewById(R.id.btnSave);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendLocation.onSendBackLocation(latlng);
            }
        });
        setButtonsHidden();
        return v;
    }
    private void initializeMap() {
        MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    public void getLocation() {
        Location loc = new Location(getActivity());
        Double[] waardes = loc.getLastKnownLocation();
        if(waardes[0] !=null && waardes[1] != null) {
            longitudeCurrentLocation = waardes[1];
            latitudeCurrentLocation = waardes[0];
        }
        else{
            longitudeCurrentLocation =3.39730;
            latitudeCurrentLocation = 50.93403;
        }
    }
    private void setPin(GoogleMap map)
    {
        LatLng place = new LatLng(latlng.latitude, latlng.longitude);
        marker = map.addMarker(new MarkerOptions()
                .title("new location")
                .position(place));

    }
    public void setButtonsVisible(){
        btnOk.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
    }
    public void setButtonsHidden(){
        btnOk.setVisibility(View.INVISIBLE);
        btnCancel.setVisibility(View.INVISIBLE);
    }
}
