package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import java.util.ArrayList;


public class PopUpService {
    private final Context context;
    private StationAdapter stationAdapter;
    private AlertDialog dialog;
    private SaveCheckedState saveCheckedState;

    // set necessary vars within constructor

    /**
     * Constructor
     * @param context The context
     * @param stationAdapter the current station adapter
     * @param saveCheckedState the savestate
     */
    public PopUpService(Context context, StationAdapter stationAdapter, SaveCheckedState saveCheckedState) {
        this.context = context;
        this.stationAdapter = stationAdapter;
        this.saveCheckedState = saveCheckedState;
    }


    // calculate filtered stations from popup to display in recyclerview list

    /**
     * calculate filtered stations from popup to display in recyclerview list
     */
    private void calculateThings(){
        String favourites = this.context.getResources().getString(R.string.favourites);
        String fastCharging = this.context.getResources().getString(R.string.fastCharging);
        String range = this.context.getResources().getString(R.string.range);

        // Ini list of filtered stations and deleted stations (those will be removed from full list later on, because java does not allow remove in for, thanks java...)
        ArrayList<Ladestation> finalStations = new ArrayList<>();
        ArrayList<Ladestation> toDel = new ArrayList<>();

        // first add all stations to list
        finalStations.addAll(GlobalStorage.getAllStations());

        // check favourites filter
        if(saveCheckedState.isShowFavourites()){
            for(Ladestation ladestation : finalStations){
                if(!GlobalStorage.getFavStations().contains(ladestation.id)){
                    toDel.add(ladestation);
                }
            }
        }
        // check fastcharging filter
        if(saveCheckedState.isShowFastCharging()){
            for(Ladestation ladestation : finalStations){
                if(ladestation.getModuleType() != Ladestation.ModuleType.FAST_CHARGING){
                    if(!toDel.contains(ladestation)){
                        toDel.add(ladestation);
                    }
                }
            }
        }
        // check seekbar range filter
        if(saveCheckedState.getSearchRange() != 100){
            ArrayList<Ladestation> rangeStations = StationAPI.getProximityStations(finalStations, MainActivity.userLocation.latitude, MainActivity.userLocation.longitude, saveCheckedState.getSearchRange());
            for(Ladestation ladestation : finalStations){
                if(!rangeStations.contains(ladestation)){
                    toDel.add(ladestation);
                }
            }
        }

        // delete stations outside of filters in delarray from finalarry
        for(Ladestation ladestation : toDel){
            finalStations.remove(ladestation);
        }

        // clear delarray for next filtering
        toDel.clear();

        // set filtered finalarray as new list in listview
        stationAdapter.setNewList(finalStations);
    }


    /**
     * function for popup toggle and listeners
     */
    void showPopup() {
        String range = this.context.getResources().getString(R.string.range);
        String unlimited = this.context.getResources().getString(R.string.unlimited);

        // inflate popup above current listview
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.options_popup, null);

        // check favourites checkbox
        CheckBox checkBoxFav = view.findViewById(R.id.FavToggleButton);
        if(saveCheckedState.isShowFavourites()){
            checkBoxFav.setChecked(true);
        }
        else {
            checkBoxFav.setChecked(false);
        }

        // check fastcharging checkbox
        CheckBox checkBoxFastCharging = view.findViewById(R.id.FastChargingToggleButton);
        if(saveCheckedState.isShowFastCharging()){
            checkBoxFastCharging.setChecked(true);
        }
        else {
            checkBoxFastCharging.setChecked(false);
        }

        // check and handle seekbar range filters
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        TextView tvProgressLabel = view.findViewById(R.id.seekBarText);
        seekBar.setProgress(saveCheckedState.getSearchRange());
        if(seekBar.getProgress() == 100){
            tvProgressLabel.setText(range + ": " + unlimited);
        }
        else{
            tvProgressLabel.setText(range + ": " + seekBar.getProgress() + "km");
        }


        // set listeners in according sequence
        checkBoxFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveCheckedState.setShowFavourites(isChecked);
                if(checkBoxFastCharging.isChecked()){
                    saveCheckedState.setShowFastCharging(true);
                }
                calculateThings();
            }
        });
        checkBoxFastCharging.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                saveCheckedState.setShowFastCharging(isChecked);
                if(checkBoxFav.isChecked()){
                    saveCheckedState.setShowFavourites(true);
                }
                calculateThings();
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekBar.getProgress() == 100){
                    tvProgressLabel.setText(range + ": " + unlimited);
                }
                else{
                    tvProgressLabel.setText(range + ": " + seekBar.getProgress() + "km");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                saveCheckedState.setSearchRange(seekBar.getProgress());
                calculateThings();
            }
        });

        builder.setView(view);
        builder.setCancelable(true);

        dialog = builder.show();
        dialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }
}
