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

import be.howest.nmct.admin.BeerPrice;
import be.howest.nmct.beerprice.loader.Contract;


public class MainActivity extends Activity implements MainFragment.ButtonCLickedMainFragment, BeerListFragment.ChangeToMapFragment {


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
                .replace(R.id.container, new BeerListFragment(), Constants.BEER_LIST)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onChangeToMap(BeerPrice beer) {
        FragmentManager mgr = getFragmentManager();
        ShowOnMapFragment frag =  ShowOnMapFragment.newInstance(beer.getLatitude(),beer.getLongitude());
        mgr.beginTransaction()
                .replace(R.id.container, frag, Constants.BEER_LIST)
                .addToBackStack(null)
                .commit();
    }
}
