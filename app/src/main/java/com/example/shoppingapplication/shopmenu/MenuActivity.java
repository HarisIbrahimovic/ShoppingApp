package com.example.shoppingapplication.shopmenu;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.shoppingapplication.R;
import com.example.shoppingapplication.adapters.ShoppingItemAdapter;
import com.example.shoppingapplication.model.MenuItem;

public class MenuActivity extends AppCompatActivity implements ShoppingItemAdapter.TouchListener {

    private MenuViewModel viewModel;
    private TextView shopName;
    private TextView shopLocation;
    private TextView deliveryTime;
    private TextView shopRatingScore;
    private TextView shopDesc;
    private ImageView shopImage;
    private TextView addToFavorites;
    private ShoppingItemAdapter shoppingItemAdapter;
    private boolean bagRemoved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        viewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        viewModel.init(getIntent().getIntExtra("id", -1));
        setUpViews();
        observe();
        onClicks();

    }

    private void onClicks() {
        addToFavorites.setOnClickListener(v->viewModel.addToFavorites(getIntent().getIntExtra("id",-1)));

    }

    private void observe() {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms( new CenterCrop());
        RequestOptions finalRequestOptions = requestOptions;

        viewModel.getBagRemoved().observe(this,bool->bagRemoved=bool);

        viewModel.getShopData().observe(this, item->{
            shopName.setText(item.getName());
            shopDesc.setText(item.getDesc());
            shopLocation.setText(item.getLocation());
            shopRatingScore.setText(String.valueOf(item.getRatingScore()));
            deliveryTime.setText(item.getDeliveryTime());
            Glide.with(MenuActivity.this).load(item.getImageUrl()).apply(finalRequestOptions).into(shopImage);
        });

        viewModel.getMenuItems().observe(this,items-> shoppingItemAdapter.setListOfMenuItems(items));

        viewModel.getToastMessage().observe(this, message->{
            if(!message.equals("null")){
                Toast.makeText(MenuActivity.this,message, Toast.LENGTH_SHORT).show();
                viewModel.setToastMessage();
            }
        });

    }

    private void setUpViews() {
        shopName= findViewById(R.id.menuShopName);
        shopLocation= findViewById(R.id.menuShopLocation);
        shopDesc= findViewById(R.id.shopContents);
        shopRatingScore = findViewById(R.id.ratingScore);
        shopImage = findViewById(R.id.menuImage);
        deliveryTime = findViewById(R.id.deliveryTime);
        addToFavorites = findViewById(R.id.addToFavorites);
        RecyclerView shopRecyclerView = findViewById(R.id.menuItemsRecyclerView);
        shoppingItemAdapter = new ShoppingItemAdapter(this,this);
        shopRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        shopRecyclerView.setAdapter(shoppingItemAdapter);
    }

    @Override
    public void onClickListener(MenuItem menuItem) {
        if(getIntent().getIntExtra("currentShop",-1)!=menuItem.getShopId()&& !bagRemoved){
            viewModel.deleteCurrentBag();
            viewModel.setBagRemoved(true);
        }
        viewModel.addItemToBag(menuItem);
    }

    @Override
    public boolean checkUser() {
        return viewModel.getUserStatus();
    }
}