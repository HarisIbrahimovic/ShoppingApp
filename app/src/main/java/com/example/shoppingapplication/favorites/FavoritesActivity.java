package com.example.shoppingapplication.favorites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.adapters.ShopAdapter;
import com.example.shoppingapplication.category.CategoryActivity;
import com.example.shoppingapplication.model.ShopItem;
import com.example.shoppingapplication.shopmenu.MenuActivity;

public class FavoritesActivity extends AppCompatActivity implements ShopAdapter.ShopTouchListener{

    private FavoritesViewModel favoritesViewModel;
    private RecyclerView recyclerView;
    private ShopAdapter shopAdapter;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        favoritesViewModel.init();
        setUpView();
        observe();
        onClicks();
    }

    private void onClicks() {
        backButton.setOnClickListener(v->finish());
    }

    private void observe() {
        favoritesViewModel.getShopItems().observe(this,list->shopAdapter.setShopItems(list));
    }

    private void setUpView() {
        backButton = findViewById(R.id.imageButton);
        recyclerView = findViewById(R.id.favoritesRecView);
        shopAdapter = new ShopAdapter(this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void shopClicked(ShopItem shopItem) {
    }
}