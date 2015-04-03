package be.howest.nmct.beerprice;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import com.google.android.gms.maps.model.LatLng;

import be.howest.nmct.admin.BeerPrice;
import be.howest.nmct.beerprice.loader.Contract;


public class MainActivity extends Activity implements MainFragment.ButtonCLickedMainFragment, BeerListFragment.ChangeToMapFragment, NewFragment.OnSelectItemsForNew,BeerBrandFragment.OnSendBackBrand,SelectLocationFragment.OnSendLocation {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment(),Constants.MAINFRAGMENT)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClickListShow() {
        FragmentManager mgr = getFragmentManager();
        mgr.beginTransaction()
                .replace(R.id.container, new BeerListFragment(), Constants.MAINFRAGMENT)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClickMapShow() {
        FragmentManager mgr = getFragmentManager();
        ShowOnMapFragment frag = new ShowOnMapFragment();
        mgr.beginTransaction()
                .replace(R.id.container, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onClickNewShow() {
        FragmentManager mgr = getFragmentManager();
        NewFragment frag = new NewFragment();
        mgr.beginTransaction()
                .replace(R.id.container, frag, Constants.SELECT_BEERBRAND)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onChangeToMap(Double lattitude, Double longitude) {
        FragmentManager mgr = getFragmentManager();
        ShowOnMapFragment frag =  ShowOnMapFragment.newInstance(lattitude, longitude);
        mgr.beginTransaction()
                .replace(R.id.container, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSelectBeerBrand() {
        FragmentManager mgr = getFragmentManager();
        BeerBrandFragment frag = new BeerBrandFragment();
        mgr.beginTransaction()
                .replace(R.id.container, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSelectLocation() {
        FragmentManager mgr = getFragmentManager();
        SelectLocationFragment frag = new SelectLocationFragment();
        mgr.beginTransaction()
                .replace(R.id.container, frag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSendBackBrand(BeerPrice.BEERBRANDS brand) {
        FragmentManager mgr = getFragmentManager();
        mgr.popBackStack();
        NewFragment frag = (NewFragment) mgr.findFragmentByTag(Constants.SELECT_BEERBRAND);
        frag.changeBeerbrand(brand);
    }

    @Override
    public void onSendBackLocation(LatLng latLng) {
        FragmentManager mgr = getFragmentManager();
        mgr.popBackStack();
        NewFragment frag = (NewFragment) mgr.findFragmentByTag(Constants.SELECT_BEERBRAND);
        frag.changeLocation(latLng);
    }
}
