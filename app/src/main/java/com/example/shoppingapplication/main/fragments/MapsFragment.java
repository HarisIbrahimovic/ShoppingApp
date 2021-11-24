package com.example.shoppingapplication.main.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.adapters.ShopAdapter;
import com.example.shoppingapplication.main.MainViewModel;
import com.example.shoppingapplication.model.ShopItem;
import com.example.shoppingapplication.shopmenu.MenuActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsFragment extends Fragment implements ShopAdapter.ShopTouchListener {


    private MainViewModel viewModel;

    private RecyclerView shopRecyclerView;
    private ShopAdapter shopAdapter;

    private EditText addLocationEditText;
    private TextView changeLocation;
    private TextView currentLocation;
    private Button addLocationButton;

    private AlertDialog dialog;
    private View myView;

    private List<ShopItem> shopItemList;
    private int currentShop;


    private final OnMapReadyCallback callback = googleMap -> {
        LatLng myCity = new LatLng(44.540648, 18.676792);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myCity,14.0f));
        if(shopItemList!=null){
            for(ShopItem item : shopItemList){
                googleMap.addMarker(new MarkerOptions().position(new LatLng(item.getLat(),item.getLon())).title(item.getName()));
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.init();
        setUpView(view);
        observe();
        onClicks();
        return view;
    }

    private void onClicks() {
        changeLocation.setOnClickListener(v->openFragment());
    }

    private void setUpView(View view) {
        shopAdapter = new ShopAdapter(getActivity(),this);
        shopRecyclerView = view.findViewById(R.id.shopItemsRecyclerView);
        changeLocation = view.findViewById(R.id.changeLocationText);
        currentLocation = view.findViewById(R.id.textView9);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        shopRecyclerView.setAdapter(shopAdapter);
    }

    private void observe() {
        viewModel.getShops().observe(getViewLifecycleOwner(),list->{
            shopItemList = list;
            shopAdapter.setShopItems(list);
        });
        viewModel.getMyBagList().observe(getViewLifecycleOwner(),list->{
            if(list!=null){
                    currentShop=list.get(0).getShopId();
            }
        });

        viewModel.getCurrentLocation().observe(getViewLifecycleOwner(),s->currentLocation.setText(s));
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), s->{
            if(!s.equals("null")){
                Toast.makeText(requireActivity(),s, Toast.LENGTH_SHORT).show();
                viewModel.setToastMessage();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null)  mapFragment.getMapAsync(callback);

    }

    @Override
    public void shopClicked(ShopItem shopItem) {
        Intent intent = new Intent(requireActivity(), MenuActivity.class);
        intent.putExtra("id",shopItem.getId());
        intent.putExtra("currentShop",currentShop);
        startActivity(intent);
    }


    @SuppressLint("InflateParams")
    private void openFragment() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        myView = inflater.inflate(R.layout.location_view,null);
        myDialog.setView(myView);
        dialog = myDialog.create();
        dialog.show();
        setUpDialog();
    }

    private void setUpDialog() {
        addLocationEditText = myView.findViewById(R.id.locationEditText);
        addLocationButton = myView.findViewById(R.id.addLocationButton);
        addLocationButton.setOnClickListener(v->{
            viewModel.setLocation(addLocationEditText.getText().toString().trim());
            dialog.dismiss();
        });
    }

}