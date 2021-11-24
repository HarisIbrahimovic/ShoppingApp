package com.example.shoppingapplication.main.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppingapplication.R;
import com.example.shoppingapplication.adapters.BagAdapter;
import com.example.shoppingapplication.main.MainViewModel;
import com.example.shoppingapplication.model.MenuItem;


public class BagFragment extends Fragment {

    private MainViewModel viewModel;
    private BagAdapter bagAdapter;
    private TextView storeName;
    private TextView storeLocation;
    private TextView totalCost;
    private ImageView storeImage;
    private ImageButton emptyBag;
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bag, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        setUpViews(view);
        observe();
        onClicks();
        return view;
    }

    private void onClicks() {
        emptyBag.setOnClickListener(v -> {
            viewModel.emptyMyBag();
            linearLayout.setVisibility(View.VISIBLE);
            Toast.makeText(requireActivity(), "Bag removed.", Toast.LENGTH_SHORT).show();
        });

    }

    @SuppressLint("DefaultLocale")
    private void observe() {
        viewModel.getMyBagList().observe(getViewLifecycleOwner(),list->{
            if(list!=null){
                    bagAdapter.setBagList(list);
                    double value = 0;
                    for (MenuItem item : list) {
                        value += item.getPrice();

                    totalCost.setText(String.format("%.2f", value));

            }}
        });

        viewModel.getCurrentShop().observe(getViewLifecycleOwner(),currentShop->{
            if(currentShop!=null){
                RequestOptions requestOptions = new RequestOptions();
                requestOptions = requestOptions.transforms( new CenterCrop());
                linearLayout.setVisibility(View.GONE);
                Glide.with(requireActivity()).load(currentShop.getImageUrl()).apply(requestOptions).into(storeImage);
                storeName.setText(currentShop.getName());
                storeLocation.setText(currentShop.getLocation());
            }
        });
    }

    private void setUpViews(View view) {
        RecyclerView recyclerViewItems = view.findViewById(R.id.recyclerViewBag);
        storeName = view.findViewById(R.id.shopNameBag);
        storeImage = view.findViewById(R.id.shopImageBag);
        storeLocation = view.findViewById(R.id.shopingBagLocation);
        totalCost = view.findViewById(R.id.totalCost);
        linearLayout = view.findViewById(R.id.bagLinearLayout);
        emptyBag = view.findViewById(R.id.deleteBagButton);
        bagAdapter = new BagAdapter(getActivity());
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewItems.setAdapter(bagAdapter);
    }
}