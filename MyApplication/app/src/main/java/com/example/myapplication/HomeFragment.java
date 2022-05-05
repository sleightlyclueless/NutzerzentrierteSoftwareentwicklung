package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class HomeFragment extends Fragment {
    private int requestCode;

    /**
     *
     * @param inflater The inflater inflates
     * @param container The container contains
     * @param savedInstanceState The savedInstanceState saves the Instance state
     * @return Returns the View
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // run on home fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // get stationadapter instance
        StationAdapter stationAdapter = new StationAdapter(GlobalStorage.getAllStations(), StationAPI.getSqLiteHelper());

        // Recyclerview from StationAdapter into stationRecyclerView
        RecyclerView stationRecyclerView = view.findViewById(R.id.stationRecyclerView);
        stationRecyclerView.setAdapter(stationAdapter);
        stationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageButton button = view.findViewById(R.id.OpenPopUp);
        button.setOnClickListener(v -> {
            PopUpService popUpService = new PopUpService(getContext(), stationAdapter, GlobalStorage.getSaveCheckedState());
            popUpService.showPopup();
        });

        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            StationAPI.initialize(getContext());
            stationAdapter.setNewList(GlobalStorage.getAllStations());
            swipeRefreshLayout.setRefreshing(false);
        });

        return view;
    }

}
