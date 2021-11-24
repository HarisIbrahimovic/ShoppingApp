package com.example.shoppingapplication.main.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.media.MediaBrowserServiceCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.adapters.CategoryAdapter;
import com.example.shoppingapplication.adapters.MainAdapter;
import com.example.shoppingapplication.category.CategoryActivity;
import com.example.shoppingapplication.main.MainViewModel;
import com.example.shoppingapplication.shopmenu.MenuActivity;

import java.util.List;

public class HomeFragment extends Fragment implements MainAdapter.OnItemClick,CategoryAdapter.CategoryClicked{

    private MainViewModel viewModel;

    private RecyclerView mainRecyclerView;
    private RecyclerView categoryRecyclerView;
    private RecyclerView newRecyclerView;

    private CategoryAdapter categoryAdapter;
    private CategoryAdapter newItemAdapter;
    private MainAdapter mainAdapter;

    private TextView locationTextView;
    private TextView changeLocationButton;
    private EditText addLocationEditText;
    private ImageButton bagButton;
    private Button addLocationButton;

    private View myView;
    private AlertDialog dialog;

    private int currentShop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        setUpViews(view);
        observe();
        onClicks();
        return view;
    }

    private void onClicks() {
        bagButton.setOnClickListener(v->viewModel.setCurrentFragment(3));
        changeLocationButton.setOnClickListener(v->openFragment());
    }

    private void observe() {
        viewModel.getCatItemList().observe(getViewLifecycleOwner(),list-> categoryAdapter.setCategoryItems(list));
        viewModel.getNewItemList().observe(getViewLifecycleOwner(),list-> newItemAdapter.setCategoryItems(list));
        viewModel.getBasicItemList().observe(getViewLifecycleOwner(),list-> mainAdapter.setBasicItemList(list));
        viewModel.getCurrentLocation().observe(getViewLifecycleOwner(),s-> locationTextView.setText(s));


        viewModel.getMyBagList().observe(getViewLifecycleOwner(),list->{
            if(list!=null){
                    currentShop = list.get(0).getShopId();
                    viewModel.setCurrentShop(list.get(0).getShopId());
            }
        });
        viewModel.getToastMessage().observe(getViewLifecycleOwner(), s->{
            if(!s.equals("null")){
                Toast.makeText(requireActivity(),s, Toast.LENGTH_SHORT).show();
                viewModel.setToastMessage();
            }
        });

    }

    private void setUpViews(View view) {
        changeLocationButton = view.findViewById(R.id.changeLocationButton);
        categoryRecyclerView = view.findViewById(R.id.recyclerViewCategories);
        mainRecyclerView = view.findViewById(R.id.recyclerViewOne);
        newRecyclerView = view.findViewById(R.id.newRecyclerView);
        locationTextView = view.findViewById(R.id.locationTextView);
        bagButton = view.findViewById(R.id.bagButton);

        mainAdapter = new MainAdapter(getContext(),this);
        newItemAdapter = new CategoryAdapter(getActivity(),this,2);
        categoryAdapter = new CategoryAdapter(getActivity(),this,1);

        newRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        categoryRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, GridLayoutManager.HORIZONTAL, false));

        mainRecyclerView.setAdapter(mainAdapter);
        categoryRecyclerView.setAdapter(categoryAdapter);
        newRecyclerView.setAdapter(newItemAdapter);
    }

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

    @Override
    public void itemClicked(int id) {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("currentShop",currentShop);
        startActivity(intent);
    }

    @Override
    public void onCategoryClicked(String category) {
        Intent intent = new Intent(getActivity(), CategoryActivity.class);
        intent.putExtra("category",category);
        intent.putExtra("currentShop",currentShop);
        startActivity(intent);
    }

    @Override
    public void onNewClicked(int id) {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        intent.putExtra("id",id);
        intent.putExtra("currentShop",currentShop);
        startActivity(intent);
    }


}
