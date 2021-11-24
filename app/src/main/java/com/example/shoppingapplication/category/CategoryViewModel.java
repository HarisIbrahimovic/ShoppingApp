package com.example.shoppingapplication.category;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingapplication.model.ShopItem;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    private CategoryRepository repository;
    private LiveData<List<ShopItem>> listOfShops;

    public void init(String category){
        if(listOfShops==null){
            repository = CategoryRepository.getInstance();
            listOfShops=repository.getListOfShops(category);
        }
    }

    public LiveData<List<ShopItem>> getListOfShops() {
        return listOfShops;
    }
}
