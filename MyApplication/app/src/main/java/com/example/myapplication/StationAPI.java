package com.example.myapplication;

import android.content.Context;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class StationAPI {

    public static SQLiteHelper getSqLiteHelper() {
        return sqLiteHelper;
    }

    private static SQLiteHelper sqLiteHelper;

    /**
     * Initializes the Station API with default values
     * @param context
     */
    public static void initialize(Context context)
    {
        // generate downloadthread object
        DownloadThread dl = new DownloadThread();
        Thread thread = new Thread(dl);
        thread.start();
        try
        {
            thread.join();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        // return and save downloaded ArrayList into api static
        GlobalStorage.setAllStations(dl.getStations());

        // fav array füllen
        sqLiteHelper = new SQLiteHelper(context);
        GlobalStorage.setFavStations(sqLiteHelper.readFavouritesFromDB());

        //defekt array füllen
        GlobalStorage.setDefStations(sqLiteHelper.readFlagsFromDB());


        GlobalStorage.setSaveCheckedState(new SaveCheckedState());
    }

    /**
     * Sorts the array list by distance
     * @param stationsList
     * @param lat
     * @param lon
     * @param range
     * @return
     */
    public static ArrayList<Ladestation> sort(ArrayList<Ladestation> stationsList, double lat, final double lon, final int range) {
        return stationsList.stream()
                .filter(station ->
                        station.getLat() <= (lat - range) &&
                                station.getLat() >= (lat + range) &&
                                station.getLon() <= (lon - range) &&
                                station.getLon() >= (lon + range))
                .collect(Collectors.toCollection(ArrayList<Ladestation>::new));
    }

    // get only nearby stations

    /**
     * Returns only the nearby stations, given 2 coordinates and distance
     * @param stationsList The station list
     * @param lat1 the coordinate to use as center
     * @param lon1 the coordinate to use as center
     * @param dist the distance
     * @return the array of Ladestationen in range
     */
    public static ArrayList<Ladestation> getProximityStations(ArrayList<Ladestation> stationsList, double lat1, double lon1, int dist)
    {
        /*quelle: http://www.movable-type.co.uk/scripts/latlong.html*/
        ArrayList<Ladestation> filtered = new ArrayList<>();
        stationsList.forEach(ladestation -> {
            double lat2 = ladestation.getLat();
            double lon2 = ladestation.getLon();
            double R = 6371; // Erdradius in km
            double dLat = (lat1 - lat2) * Math.PI / 180.0;
            double dLon = (lon1 - lon2) * Math.PI / 180.0;
            double a = Math.sin(dLat / 2.) * Math.sin(dLat / 2.)
                    + Math.cos(lat1 * Math.PI / 180.0)
                    * Math.cos(lat2 * Math.PI / 180.0)
                    * Math.sin(dLon / 2.) * Math.sin(dLon / 2.);
            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1. - a));
            double d = R * c;
            // Compare distance
            if(d < dist)
            {
                filtered.add(ladestation);
            }
        });
        return  filtered;
    }
}