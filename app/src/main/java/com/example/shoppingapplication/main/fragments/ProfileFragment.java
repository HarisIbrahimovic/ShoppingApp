package com.example.shoppingapplication.main.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.favorites.FavoritesActivity;
import com.example.shoppingapplication.main.MainViewModel;


public class ProfileFragment extends Fragment {

    private MainViewModel viewModel;
    private TextView signOutText;
    private TextView favoritesText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,  false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        favoritesText = view.findViewById(R.id.favorites);
        observe();
        setUpViews(view);
        onClicks();
        return view;
    }

    private void onClicks() {
        signOutText.setOnClickListener(v->{
            viewModel.signOutUser();
            Toast.makeText(getActivity(),"Signed out.",Toast.LENGTH_SHORT).show();
        });
        favoritesText.setOnClickListener(v->{
            startActivity(new Intent(requireActivity(), FavoritesActivity.class));
        });


    }

    private void setUpViews(View view) {
        signOutText = view.findViewById(R.id.signOut);
    }

    private void observe() {
        viewModel.getCurrentUser().observe(getViewLifecycleOwner(),bool->{
            if(bool!=null&&!bool){
                Toast.makeText(getActivity(),"Please login.",Toast.LENGTH_SHORT).show();
                viewModel.setCurrentFragment(1);
            }
        });

    }
}