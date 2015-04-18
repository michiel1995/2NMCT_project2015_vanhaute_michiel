package be.howest.nmct.admin;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Michiel on 31/03/2015.
 */
public class BeerAdmin {
    private static List<BeerPrice> beers;
    static {
        /*beers = new ArrayList<BeerPrice>();
       beers.add(new BeerPrice("test 1", BeerPrice.BEERBRANDS.STELLA,(float)2.6,"waregem","hekkenstraat","belgium",3.39730,50.93403));
       beers.add(new BeerPrice("test 2",BeerPrice.BEERBRANDS.STELLA,(float)2.42,"waregem","hekkenstraat","belgium",3.38236,50.94755));
       beers.add(new BeerPrice("test 3",BeerPrice.BEERBRANDS.STELLA,(float)2.0,"waregem","hekkenstraat","belgium",3.37859,50.94047));
        beers.add(new BeerPrice("test 3",BeerPrice.BEERBRANDS.STELLA,(float)1.5,"waregem","hekkenstraat","belgium",3.37869,50.94037));*/
    }

    public static List<BeerPrice> getBeers(){return beers;}

    public static List<BeerPrice> getBeersSorted(Context context){
        Double[] lastKnownLocation = new Location(context).getLastKnownLocation();
        //als er geen eigen locatie is
        if(lastKnownLocation[0] == null || lastKnownLocation[1] == null )
            return beers;
        for(BeerPrice beer: beers){
            double dist = Math.sqrt(Math.pow(beer.getLongitude() - lastKnownLocation[1],2) + Math.pow(beer.getLatitude()- lastKnownLocation[0],2));
            beer.setDistance(dist);
        }
        Collections.sort(beers);
        return beers;
    }

    public static BeerPrice getBeer( Double longitude, Double latitude){
        for(BeerPrice b : beers){
            if(b.getLatitude().equals(latitude)  && b.getLongitude().equals(longitude))
                return b;
        }
        return null;
    }
    public static  void saveBeer(BeerPrice beer, Context context){
        beers.add(beer);
       /* saveBeers(context);
        readBeers(context);*/
    }

    public static void readBeers(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("beersave", Activity.MODE_PRIVATE);
        String arrayString = sharedPref.getString("beers", null);
        beers = stringToObject(arrayString);
        if(beers == null)
            beers = new ArrayList<BeerPrice>();

    }

    private static List<BeerPrice> stringToObject(String json) {

        return new Gson().fromJson(json, new TypeToken<List<BeerPrice>>(){}.getType());
    }

    public static void saveBeers(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("beersave", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("beers", objectToString(beers));
        editor.commit();
    }

    private static String objectToString(List<BeerPrice> beers) {
        return new Gson().toJson(beers);
    }


}
