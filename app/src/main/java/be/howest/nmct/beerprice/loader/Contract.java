package be.howest.nmct.beerprice.loader;

import android.provider.BaseColumns;

/**
 * Created by Michiel on 31/03/2015.
 */
public class Contract {
    public interface BeerColumns extends BaseColumns {
        public static final String COLUMN_ORGANISATION = "beer_organisation";
        public static final String COLUMN_PRICE = "beer_price";
        public static final String COLUMN_ADDRESS = "beer_address";
        public static final String COLUMN_CITY = "beer_city";
        public static final String COLUMN_COUNTRY = "beer_country";
        public static final String COLUMN_BRAND = "beer_brand";
        public static final String COLUMN_LONG = "beer_longitude";
        public static final String COLUMN_LAT= "beer_latitude";
    }

}
