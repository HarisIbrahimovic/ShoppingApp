package com.example.shoppingapplication.shopmenu;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingapplication.model.MenuItem;
import com.example.shoppingapplication.model.ShopItem;

import java.util.List;

public class MenuViewModel extends ViewModel {

    private MenuRepository menuRepository;
    private LiveData<List<MenuItem>> menuItems;
    private LiveData<ShopItem> shopData;
    private LiveData<String> toastMessage;
    private final MutableLiveData<Boolean> bagRemoved = new MutableLiveData<>();

    public void init(int id){
        if(menuRepository!=null)return;
        menuRepository = MenuRepository.getInstance();
        shopData = menuRepository.getShopData(id);
        menuItems = menuRepository.getMenuItemList(id);
        toastMessage=menuRepository.getToastMessage();
        bagRemoved.setValue(false);
    }

    public LiveData<List<MenuItem>> getMenuItems() {
        return menuItems;
    }

    public LiveData<ShopItem> getShopData() {
        return shopData;
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<Boolean> getBagRemoved() {
        return bagRemoved;
    }

    public void addItemToBag(MenuItem menuItem) {
        menuRepository.addItemToBag(menuItem);
    }

    public void setToastMessage(){
        menuRepository.setToastMessage("null");
    }

    public boolean getUserStatus() {
        return menuRepository.getUserStatus();
    }

    public void deleteCurrentBag() {
        menuRepository.deleteCurrentBag();
    }

    public void setBagRemoved(boolean b) {
        bagRemoved.setValue(b);
    }

    public void addToFavorites(int id) {
        menuRepository.addToFavorites(id);
    }
}
