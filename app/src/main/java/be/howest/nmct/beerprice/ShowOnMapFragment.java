package be.howest.nmct.beerprice;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;
import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import be.howest.nmct.admin.BeerAdmin;
import be.howest.nmct.admin.BeerPrice;
import be.howest.nmct.admin.Location;
import be.howest.nmct.beerprice.loader.Contract;


/**
 * Created by Michiel on 31/03/2015.
 */
public class ShowOnMapFragment extends Fragment implements OnMapReadyCallback {

    public static final String PARAM_LAT = "be.howest.nmct.beerpicer.PARAM_LAT";
    public static final String PARAM_LON = "be.howest.nmct.beerpicer.PARAM_LON";

    private boolean single = false;
    private Double latitude;
    private Double longitude;
    private BeerPrice beer;
    private List<BeerPrice> beers;


    public static ShowOnMapFragment newInstance(Double latitude, Double longitude)
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
        else{
            longlatCurrentLocation();
        }


    }

    private void longlatCurrentLocation() {
        Double[] lastKnownLocation = new Location(getActivity()).getLastKnownLocation();
        if(lastKnownLocation[0] != null || lastKnownLocation[1] != null) {
            this.latitude = lastKnownLocation[0];
            this.longitude = lastKnownLocation[1];
        }
        else{
            this.latitude = 0.0;
            this.longitude = 0.0;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.show_on_map, container, false);
        initializeMap();

       if(!single)
           beers = BeerAdmin.getBeers();
       else
          beer = BeerAdmin.getBeer(this.longitude, this.latitude);

        return v;
    }

    private void initializeMap() {
        MapFragment mapFragment = (MapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        LatLng zoomTo = new LatLng(latitude, longitude);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(zoomTo, 15));

        if(single)
            setPin(map, this.beer);
        else
            multiplePin(map);
    }

    private void multiplePin(GoogleMap map) {
        if(beers != null) {
            for (BeerPrice beer : beers) {
                setPin(map, beer);
            }
        }
    }

    private void setPin(GoogleMap map, BeerPrice beer)
    {
        LatLng place = new LatLng(beer.getLatitude(), beer.getLongitude());
        map.addMarker(new MarkerOptions()
                .title(beer.getOrganisation())
                .snippet(beer.getBrand() + " €" + beer.getPrice())
                .position(place)
                .icon(BitmapDescriptorFactory.fromResource(getResourceIdBeerPicture(beer))));
    }

    private int getResourceIdBeerPicture(BeerPrice beer) {
        float price = beer.getPrice();
        int resourceid;
        if(price <= 1.5) {
            resourceid = R.drawable.beer_low_mini;
        }else if(price <=2.0){
            resourceid = R.drawable.beer_middle_low_mini;
        }else if(price <=2.5){
            resourceid = R.drawable.beer_middle_high_mini;
        }else{
            resourceid = R.drawable.beer_high_mini;
        }
        return resourceid;
    }


}
