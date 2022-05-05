package com.example.myapplication;

import java.util.ArrayList;

// Global Storage: Public stuff and lists, accessed throughout basically all files
public class GlobalStorage {

    private static ArrayList<Ladestation> allStations; // All Stations
    private static ArrayList<Integer> favStations;     // All Favorite Stations
    private static ArrayList<Integer> defStations;     // All Defect Stations
    private static SaveCheckedState saveCheckedState;  // The SaveCheckedState

    /**
     * Returns allStations
     * @return The allStations Array
     */
    public static ArrayList<Ladestation> getAllStations() {
        return allStations;
    }

    /**
     * Set all stations in global storage
     * @param allStations The ArrayList to set the stations in Global Storage to
     */
    public static void setAllStations(ArrayList<Ladestation> allStations) { GlobalStorage.allStations = allStations; }

    /**
     * Returns all favorite stations
     * @return The favoriteStation ArrayList
     */
    public static ArrayList<Integer> getFavStations() {
        return favStations;
    }

    /**
     * Sets the favorite stations in Global Storage to the passed ArrayList
     * @param favStations the ArrayList to set the Global Storage's array list to.
     */
    public static void setFavStations(ArrayList<Integer> favStations) { GlobalStorage.favStations = favStations; }

    /**
     * Returns the defect stations as ArrayList
     * @return the defectStations ArrayList
     */
    public static ArrayList<Integer> getDefStations() {
        return defStations;
    }
    public static void setDefStations(ArrayList<Integer> defStations) { GlobalStorage.defStations = defStations; }

    /**
     * Returns the SaveCheckedState
     * @return saveCheckedState
     */
    public static SaveCheckedState getSaveCheckedState() {
        return saveCheckedState;
    }

    /**
     * Sets the GlobalStorage's SaveCheckedState
     * @param saveCheckedState the state to set the checkedState to
     */
    public static void setSaveCheckedState(SaveCheckedState saveCheckedState) { GlobalStorage.saveCheckedState = saveCheckedState; }

}
