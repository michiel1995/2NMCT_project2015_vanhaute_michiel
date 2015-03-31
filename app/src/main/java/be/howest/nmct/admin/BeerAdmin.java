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
       beers.add(new BeerPrice("test 1","stella",(float)2.6,"waregen","hekkenstraat",4,(float)1.215,(float)1.598));
       beers.add(new BeerPrice("test 2","stella",(float)2.6,"waregen","hekkenstraat",4,(float)1.256,(float)1.518));
       beers.add(new BeerPrice("test 3","stella",(float)2.6,"waregen","hekkenstraat",4,(float)1.218,(float)1.528));
    }

    public static List<BeerPrice> getBeer(){return beers;}

    public static BeerPrice getBeer(float longitude, float latitude){
        for(BeerPrice b : beers){
            if(b.getLatitude().equals(latitude)  && b.getLongitude().equals(longitude))
                return b;
        }
        return null;
    }
}
