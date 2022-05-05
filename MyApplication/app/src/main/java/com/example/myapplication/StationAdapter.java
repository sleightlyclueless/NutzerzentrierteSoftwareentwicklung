package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder>{
    private ArrayList<Ladestation> stationList;
    private SQLiteHelper sqLiteHelper;

    public StationAdapter(ArrayList<Ladestation> stationList, SQLiteHelper sqLiteHelper)
    {
        this.stationList = stationList;
        this.sqLiteHelper = sqLiteHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stationView = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(stationView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ladestation ladestation = stationList.get(position); // Hole ladestation
        holder.favouriteButton.setImageResource(R.drawable.ic_baseline_star_25); // Buttons clearen

        // Setzt als Top text den Operator
        TextView stationTextTop = holder.stationTextTop;
        stationTextTop.setText(ladestation.getOperator());

        // Gleiches wie oben nur mit straße
        TextView stationTextCenter = holder.stationTextCenter;
        stationTextCenter.setText(ladestation.getStreet() +" "+ ladestation.getNumber());

        // Setzt Bottom text
        TextView stationTextBottom = holder.stationTextBottom;
        stationTextBottom.setText(ladestation.getPostalCode() + " " + ladestation.getLocation());

        // Set station id
        holder.stationID = ladestation.id;

        // Buttons
        ImageButton button = holder.favouriteButton;
        if(GlobalStorage.getFavStations().contains(ladestation.id)){
            button.setImageResource(R.drawable.ic_baseline_star_24);
        }
        button.setOnClickListener(v -> toggleButtonFav(ladestation, button));

        ImageButton buttonRep = holder.reportButton;
        if(GlobalStorage.getDefStations().contains(ladestation.id)){
            buttonRep.setImageResource(R.drawable.ic_baseline_flag_25);
        }

        if(MainActivity.onServiceFragment){
            buttonRep.setOnClickListener(v -> toggleButtonRepWorker(ladestation, buttonRep));
        }
        else{
            buttonRep.setOnClickListener(v -> toggleButtonRep(ladestation, buttonRep));
        }

    }


    @Override
    public int getItemCount() {
        return stationList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView stationTextTop;
        public TextView stationTextCenter;
        public TextView stationTextBottom;
        public ImageButton favouriteButton;
        public ImageButton reportButton;
        public int stationID;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            stationTextTop = itemView.findViewById(R.id.text_stationTop);
            stationTextCenter = itemView.findViewById(R.id.text_stationCenter);
            stationTextBottom = itemView.findViewById(R.id.text_stationBottom);
            favouriteButton = itemView.findViewById(R.id.FavouriteButton);
            reportButton = itemView.findViewById(R.id.ReportButton);
        }
    }

    /**
     * Toggles the favorite button status
     * @param ladestation The ladestation to toggle as favorite
     * @param button The button (the button, button)
     */
    private void toggleButtonFav(Ladestation ladestation, ImageButton button){
        if(GlobalStorage.getFavStations().contains(ladestation.id)){
            int index = GlobalStorage.getFavStations().indexOf(ladestation.id);
            GlobalStorage.getFavStations().remove(index);
            button.setImageResource(R.drawable.ic_baseline_star_25);
            sqLiteHelper.removeSingleFavoriteFromDB(ladestation.id); // remove from db
        }
        else{
            GlobalStorage.getFavStations().add(ladestation.id);
            button.setImageResource(R.drawable.ic_baseline_star_24);
            sqLiteHelper.saveSingleFavoriteToDB(ladestation.id);// add to db
        }
    }

    /**
     * Sets the icon for report button accordingly
     * @param ladestation The ladestation to toggle as reported
     * @param button The button (the button, button)
     */
    private void toggleButtonRep(Ladestation ladestation, ImageButton button){
        if(!GlobalStorage.getDefStations().contains(ladestation.id)){
            GlobalStorage.getDefStations().add(ladestation.id);
            button.setImageResource(R.drawable.ic_baseline_flag_25);
            sqLiteHelper.saveSingleFlagToDB(ladestation.id);
        }
    }

    /**
     * Does the same as toggleButtonRep, but allows the worker to "repair" the station
     * @param ladestation The ladestation to repair
     * @param button The button (the button, button)
     */
    public void toggleButtonRepWorker(Ladestation ladestation, ImageButton button){
        if(GlobalStorage.getDefStations().contains(ladestation.id)){
            int index = GlobalStorage.getDefStations().indexOf(ladestation.id);
            GlobalStorage.getDefStations().remove(index);
            button.setImageResource(R.drawable.ic_baseline_flag_24);
            sqLiteHelper.removeSingleReportFromDB(ladestation.id);
            this.notifyItemRemoved(index);
            ArrayList<Ladestation> malWiederEineArrayList = new ArrayList<>();
            for(Ladestation ladestation1 : GlobalStorage.getAllStations()){
                if(GlobalStorage.getDefStations().contains(ladestation1.id)){
                    malWiederEineArrayList.add(ladestation1);
                }
            }
            this.setNewList(malWiederEineArrayList);
        }
    }

    /**
     * Sets the stationList to new list
     * @param newList The new station list
     */
    @SuppressLint("NotifyDataSetChanged")
    public void setNewList(ArrayList<Ladestation> newList){
        stationList = newList;
        notifyDataSetChanged();
    }
}
