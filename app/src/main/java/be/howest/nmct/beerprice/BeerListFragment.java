package be.howest.nmct.beerprice;

import android.app.Activity;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import be.howest.nmct.admin.BeerAdmin;
import be.howest.nmct.admin.BeerPrice;
import be.howest.nmct.beerprice.loader.BeerLoader;
import be.howest.nmct.beerprice.loader.Contract;

/**
 * Created by Michiel on 31/03/2015.
 */
public class BeerListFragment extends ListFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public interface ChangeToMapFragment {
        void onChangeToMap(BeerPrice beer);
    }
    private ChangeToMapFragment changeMapFragment;

    private BeerAdapter beerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.beer_list,container,false);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        changeMapFragment = (ChangeToMapFragment) activity;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int[] viewIds= new int[]{R.id.tvOrganisation, R.id.tvBiermerk, R.id.tvStreet,R.id.tvCity,R.id.tvPrijs};
        String[] columnNames = new String[]{
                Contract.BeerColumns.COLUMN_ORGANISATION,
                Contract.BeerColumns.COLUMN_BRAND,
                Contract.BeerColumns.COLUMN_STREET,
                Contract.BeerColumns.COLUMN_CITY,
                Contract.BeerColumns.COLUMN_PRICE
        };
        beerAdapter = new BeerAdapter(getActivity(), R.layout.row_beer, null, columnNames,viewIds,0);
        setListAdapter(beerAdapter);
        getLoaderManager().initLoader(0,null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        BeerPrice b = (BeerPrice) v.getTag();
        changeMapFragment.onChangeToMap(b);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new BeerLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        beerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        beerAdapter.swapCursor(null);
    }


    class BeerAdapter extends SimpleCursorAdapter {

        private int layout;

        public BeerAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
            super(context, layout, c, from, to, flags);
            this.layout = layout;
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            final LayoutInflater inflater = LayoutInflater.from(context);
            View row = inflater.inflate(layout, parent,false);
            ImageView icon = (ImageView) row.findViewById(R.id.ivImage);

            int longitude = cursor.getColumnIndex(Contract.BeerColumns.COLUMN_LONG);
            int latitude = cursor.getColumnIndex(Contract.BeerColumns.COLUMN_LAT);
            BeerPrice b  = BeerAdmin.getBeer(cursor.getDouble(longitude), cursor.getDouble(latitude));
            row.setTag(b);
            int price = cursor.getColumnIndex(Contract.BeerColumns.COLUMN_PRICE);
            if(cursor.getDouble(price) < 1.5) {
                icon.setImageResource(R.drawable.beer_low);
            }else if(cursor.getDouble(price) <2){
                icon.setImageResource(R.drawable.beer_middle_low);
            }else if(cursor.getDouble(price) <2.5){
                icon.setImageResource(R.drawable.beer_middle_high);
            }else{
                icon.setImageResource(R.drawable.beer_high);
            }
            return row;
        }
    }
}
