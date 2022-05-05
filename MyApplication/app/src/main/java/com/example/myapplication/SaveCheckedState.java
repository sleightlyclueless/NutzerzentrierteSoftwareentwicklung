package com.example.myapplication;

public class SaveCheckedState {

    private boolean showFavourites;
    private boolean showFastCharging;
    private int searchRange;

    /**
     * Constructor
     */
    public SaveCheckedState(){
        this.showFastCharging = false;
        this.showFavourites = false;
        this.searchRange = 100;
    }

    /**
     * Returns  showFavourites
     * @return showFavourites
     */
    public boolean isShowFavourites() {
        return showFavourites;
    }

    /**
     * Returns  showFavourites
     * @return showFavourites
     */
    public void setShowFavourites(boolean showFavourites) {
        this.showFavourites = showFavourites;
    }

    /**
     * Returns showFastCharging
     * @return showFastCharging
     */
    public boolean isShowFastCharging() {
        return showFastCharging;
    }

    /**
     * Returns showFastCharging
     * @return showFastCharging
     */
    public void setShowFastCharging(boolean showFastCharging) { this.showFastCharging = showFastCharging; }

    /**
     * Returns showFastCharging
     * @return showFastCharging
     */
    public int getSearchRange() {
        return searchRange;
    }

    /**
     * Returns showFavourites
     * @return showFavourites
     */
    public void setSearchRange(int searchRange) {
        this.searchRange = searchRange;
    }

}
