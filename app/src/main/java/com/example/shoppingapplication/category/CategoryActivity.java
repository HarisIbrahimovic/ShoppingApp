package com.example.shoppingapplication.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.adapters.ShopAdapter;
import com.example.shoppingapplication.model.ShopItem;
import com.example.shoppingapplication.shopmenu.MenuActivity;

public class CategoryActivity extends AppCompatActivity implements ShopAdapter.ShopTouchListener{

    private CategoryViewModel viewModel;
    private RecyclerView categoryRecyclerView;
    private ShopAdapter shopAdapter;
    private ImageButton backButton;
    private TextView categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.init(getIntent().getStringExtra("category"));
        setUpViews();
        observe();
        onClicks();
    }

    private void onClicks() {
        backButton.setOnClickListener(v->finish());
    }

    private void observe() {
        viewModel.getListOfShops().observe(this,list-> shopAdapter.setShopItems(list));
    }

    private void setUpViews() {
        categoryName = findViewById(R.id.categoryNameActivity);
        backButton = findViewById(R.id.backButton);
        categoryName.setText(getIntent().getStringExtra("category"));

        categoryRecyclerView = findViewById(R.id.categoryActivityRecyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopAdapter = new ShopAdapter(this,this);
        categoryRecyclerView.setAdapter(shopAdapter);
    }

    @Override
    public void shopClicked(ShopItem shopItem) {
        Intent intent = new Intent(CategoryActivity.this, MenuActivity.class);
        intent.putExtra("id",shopItem.getId());
        intent.putExtra("currentShop",getIntent().getIntExtra("currentShop",-1));
        startActivity(intent);
    }
}