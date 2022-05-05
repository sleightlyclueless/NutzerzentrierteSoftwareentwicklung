package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class LoginFragment extends Fragment {

    /**
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // USER
        Button userButton = view.findViewById(R.id.UserButton);
        userButton.setOnClickListener(v -> {
            MainActivity.getBottomNav().inflateMenu(R.menu.bottom_navigation);
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();
            MainActivity.getBottomNav().setSelectedItemId(R.id.nav_map);
            MainActivity.getBottomNav().setVisibility(View.VISIBLE);
        });

        // WORKER
        Button workerButton = view.findViewById(R.id.WorkerButton);
        workerButton.setOnClickListener(v -> {
            MainActivity.getBottomNav().inflateMenu(R.menu.bottom_navigation_worker);
            getParentFragmentManager().beginTransaction().replace(R.id.fragment_container, new MapFragment()).commit();

            MainActivity.getBottomNav().setSelectedItemId(R.id.nav_map);
            MainActivity.getBottomNav().setVisibility(View.VISIBLE);
        });

        return view;
    }
}
