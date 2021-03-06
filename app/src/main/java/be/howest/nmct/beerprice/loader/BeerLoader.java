package be.howest.nmct.beerprice.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.provider.BaseColumns;

import java.util.List;

import be.howest.nmct.admin.BeerAdmin;
import be.howest.nmct.admin.BeerPrice;

/**
 * Created by Michiel on 31/03/2015.
 */
public class BeerLoader  extends AsyncTaskLoader<Cursor> {
    private Cursor mCursor;
    private Boolean sorted;
    private Context context;
    public final  String[] mColumnNames = new String[]{
            BaseColumns._ID,
            Contract.BeerColumns.COLUMN_ORGANISATION,
            Contract.BeerColumns.COLUMN_BRAND,
            Contract.BeerColumns.COLUMN_PRICE,
            Contract.BeerColumns.COLUMN_CITY,
            Contract.BeerColumns.COLUMN_ADDRESS,
            Contract.BeerColumns.COLUMN_COUNTRY,
            Contract.BeerColumns.COLUMN_LAT,
            Contract.BeerColumns.COLUMN_LONG
    };

    public BeerLoader(Context context) {
        super(context);
    }
    public BeerLoader(Context context, Boolean sorted) {
        super(context);
        this.context = context;
        this.sorted = sorted;
    }
    private static Object lock = new Object();

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mCursor != null){
            deliverResult(mCursor);
        }
        if(takeContentChanged() || mCursor == null){
            forceLoad();
        }
    }


    @Override
    public Cursor loadInBackground() {
        if (mCursor == null)
            loadCursor();
        return mCursor;
    }
    private void loadCursor() {
        synchronized (lock){
            if(mCursor  != null)return;
            MatrixCursor cursor = new MatrixCursor(mColumnNames);
            int id = 1;
            List<BeerPrice> lijst;
            if(sorted)
                 lijst = BeerAdmin.getBeersSorted(context);
            else
                lijst = BeerAdmin.getBeers();
            if(lijst != null){
            for(BeerPrice beer : lijst){
                MatrixCursor.RowBuilder row =cursor.newRow();
                row.add(id);
                row.add(beer.getOrganisation());
                row.add(beer.getBrand());
                row.add(beer.getPrice());
                row.add(beer.getCity());
                row.add(beer.getAddress());
                row.add(beer.getCountry());
                row.add(beer.getLatitude());
                row.add(beer.getLongitude());
                id++;
            }}
            mCursor=cursor;
        }
    }
}
