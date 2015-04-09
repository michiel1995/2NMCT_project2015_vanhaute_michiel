package be.howest.nmct.beerprice;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;

import be.howest.nmct.admin.BeerPrice;
import be.howest.nmct.admin.Location;

/**
 * Created by Michiel on 2/04/2015.
 */
public class NewFragment extends Fragment {

    public interface OnSelectItemsForNew{
        void onSelectBeerBrand();
        void onSelectLocation();
    }

    private EditText etBeerBrand;
    private EditText etLocation;
    private EditText etPrice;
    private EditText etOrganisation;
    private OnSelectItemsForNew selectItemsNew;
    private BeerPrice.BEERBRANDS brand;
    private String address;
    private String city;
    private String country;
    private LatLng latlng;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            selectItemsNew = (OnSelectItemsForNew) activity;
        }catch (ClassCastException ex) {
            throw new ClassCastException(activity.toString() + " must implement OnStudentListItem");
        }
    }
    public void changeBeerbrand (BeerPrice.BEERBRANDS brand){
        this.brand = brand;
    }
    public void changeLocation(LatLng latlng){
        this.latlng = latlng;
    }

    private void showBeerbrand() {
        etBeerBrand.setText(brand.getNaam());
    }
    @Override
    public void onResume() {
        super.onResume();
        if(brand != null)
            showBeerbrand();
        if(latlng != null){
            try {
                getLocation();
            } catch (IOException e) {
                e.printStackTrace();
            }
            showLocation();}

    }

    private void getLocation() throws IOException {
        Location loc = new Location(getActivity());
        String[] location = loc.getLocation(latlng);
        address = location[0];
        city = location[1];
        country = location[2];
    }

    private void showLocation() {
        if(address == null || city == null)
            etLocation.setText("locatie niet gevonden");
        else
             etLocation.setText(address + " - " + city + ", " + country);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.add_beer,container,false);
        etBeerBrand = (EditText) v.findViewById(R.id.etBeerBrand);
        etLocation = (EditText) v.findViewById(R.id.etLocation);
        etOrganisation = (EditText) v.findViewById(R.id.etOrganisation);
        etPrice = (EditText) v.findViewById(R.id.etPrice);
        etBeerBrand.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    noFocus();
                    goToBeerBrand();
                }
            }
        });
        etLocation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    noFocus();
                    goToLocation();

                }
            }
        });
        etOrganisation.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard();
            }
        });
        etPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                hideKeyboard();
            }
        });


        return v;
    }

    private void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etOrganisation.getWindowToken(), 0);
    }

    private void noFocus() {
        View current = getActivity().getCurrentFocus();
        if (current != null)
            current.clearFocus();
    }


    private void goToBeerBrand(){
        selectItemsNew.onSelectBeerBrand();
    }
    private void goToLocation(){
        selectItemsNew.onSelectLocation();
    }
}
