package be.howest.nmct.admin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michiel on 31/03/2015.
 */
public class BeerAdmin {
    private static List<BeerPrice> beers;
    static {
        beers = new ArrayList<BeerPrice>();
       beers.add(new BeerPrice("test 1","stella",(float)2.6,"waregem","hekkenstraat",4,3.39730,50.93403));
       beers.add(new BeerPrice("test 2","stella",(float)2.42,"waregem","hekkenstraat",4,3.38236,50.94755));
       beers.add(new BeerPrice("test 3","stella",(float)2.0,"waregem","hekkenstraat",4,3.37859,50.94047));
        beers.add(new BeerPrice("test 3","stella",(float)1.5,"waregem","hekkenstraat",4,3.37869,50.94037));
    }

    public static List<BeerPrice> getBeers(){return beers;}

    public static BeerPrice getBeer( Double longitude, Double latitude){
        for(BeerPrice b : beers){
            if(b.getLatitude().equals(latitude)  && b.getLongitude().equals(longitude))
                return b;
        }
        return null;
    }
}
