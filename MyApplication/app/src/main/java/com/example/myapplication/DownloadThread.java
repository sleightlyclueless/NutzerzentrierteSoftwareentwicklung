package com.example.myapplication;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class DownloadThread implements Runnable
{
    private volatile ArrayList<Ladestation> stations;

    /**
     * Overrides the Runnable's run method
     */
    @Override
    public void run()
    {
        Gson gson = new Gson();                             // get instance of gson object (generate objects from json format)
        Type return_type = new TypeToken<ArrayList<Ladestation>>(){}.getType(); // set type of Ladestation object for gson return
        final String json = getJson();                      // Load json from API url and save into var
        this.stations = gson.fromJson(json, return_type);   // generate and return ArrayList from parsed json string with gson
    }


    /**
     * Fetches the stations as a JSON from the given URL
     * @return JSON-Formatted string
     */
    private String getJson()
    {
        BufferedReader reader = null;
        try
        {
            try
            {
                URL url = new URL("https://api.aurora-theogenia.de/chargingstations/charging_stations.json"); // read into buffer from url

                reader = new BufferedReader(new InputStreamReader(read(url))); // Create buffer to load giant string by chunks
                StringBuffer buffer = new StringBuffer();

                int read;
                char[] chars = new char[1024]; // Read every 1024 chars into char array
                while ((read = reader.read(chars)) != -1)
                {
                    buffer.append(chars, 0, read); // Append those chars onto buffer
                }
                return buffer.toString(); // Convert buffer to string and return it
            }

            finally // once done close the reader again
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a URL connection's stream and returns it
     * @param url The URL to get the stream from
     * @return The fetched InputStream
     * @throws IOException
     */
    private InputStream read(URL url) throws IOException
    {
        HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
        httpCon.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");
        httpCon.setConnectTimeout(10000);
        httpCon.setReadTimeout(10000);
        return httpCon.getInputStream();
    }

    // return the stations ArrayList to save into ArrayList in StationsAPI

    /**
     * Getter for all stations
     * @return the stations ArrayList
     */
    public ArrayList<Ladestation> getStations(){return stations;}
}