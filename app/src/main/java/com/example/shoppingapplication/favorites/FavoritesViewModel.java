package com.example.shoppingapplication.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingapplication.model.ShopItem;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    private FavoritesRepository favoritesRepository;
    private LiveData<List<ShopItem>> shopItems;

    public void init(){
        favoritesRepository= FavoritesRepository.getInstance();
        shopItems = favoritesRepository.getShopItems();
    }


    public LiveData<List<ShopItem>> getShopItems() {
        return shopItems;
    }
}


