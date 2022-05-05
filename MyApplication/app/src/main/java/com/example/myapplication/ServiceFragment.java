package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import java.util.ArrayList;

public class ServiceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_service, container, false);

        // Recyclerview from StationAdapter into stationRecyclerView
        RecyclerView stationRecyclerView = view.findViewById(R.id.stationRecyclerViewBroken);

        ArrayList<Ladestation> brokenOnes = new ArrayList<>();
        for(Ladestation ladestation : GlobalStorage.getAllStations()){
            if(GlobalStorage.getDefStations().contains(ladestation.id)){
                brokenOnes.add(ladestation);
            }
        }
        StationAdapter stationAdapter = new StationAdapter(brokenOnes, StationAPI.getSqLiteHelper());
        stationRecyclerView.setAdapter(stationAdapter);
        stationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefreshBroken);
        swipeRefreshLayout.setOnRefreshListener(() -> {

            brokenOnes.clear();

            for(Ladestation ladestation : GlobalStorage.getAllStations()){
                if(GlobalStorage.getDefStations().contains(ladestation.id)){
                    brokenOnes.add(ladestation);
                }
            }

            StationAPI.initialize(getContext());


            stationAdapter.setNewList(brokenOnes);
            swipeRefreshLayout.setRefreshing(false);
        });


        return view;
    }
}
