package com.example.shoppingapplication.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.shoppingapplication.model.BasicItem;
import com.example.shoppingapplication.model.Item;
import com.example.shoppingapplication.model.MenuItem;
import com.example.shoppingapplication.model.ShopItem;

import java.util.List;

public class MainViewModel extends ViewModel {

    private final MutableLiveData<Integer> currentFragment = new MutableLiveData<>();
    private MutableLiveData<ShopItem>  currentShop = new MutableLiveData<>();
    private LiveData<List<BasicItem>>  basicItemList;
    private LiveData<List<ShopItem>>  shopItemList;
    private LiveData<List<MenuItem>>  myBagList;
    private LiveData<List<Item>>  newItemList;
    private LiveData<List<Item>>  catItemList;
    private LiveData<Boolean>  currentUser;
    private LiveData<String>  currentLocation;
    private LiveData<String>  toastMessage;
    private MainRepository mainRepository;

    public void init(){
        if(currentFragment.getValue()==null||currentUser==null){
            currentFragment.setValue(1);
            mainRepository = MainRepository.getInstance();
            currentUser = mainRepository.getCurrentUser();
            basicItemList = mainRepository.getBasicItemList();
            catItemList = mainRepository.getCategoryItemsList();
            newItemList = mainRepository.getNewItemList();
            myBagList = mainRepository.getMyBagList();
            shopItemList = mainRepository.getShopItems();
            toastMessage = mainRepository.getToastMessage();
            currentLocation = mainRepository.getCurrentLocation();
        }
    }

    public void setCurrentFragment(int num){
        currentFragment.setValue(num);
    }

    public LiveData<Integer> getCurrentFragment(){
        return currentFragment;
    }

    public LiveData<List<BasicItem>> getBasicItemList() {
        return basicItemList;
    }

    public LiveData<List<Item>> getCatItemList() {
        return catItemList;
    }

    public LiveData<List<Item>> getNewItemList() {
        return newItemList;
    }

    public LiveData<Boolean> getCurrentUser() {
        return currentUser;
    }

    public LiveData<List<MenuItem>> getMyBagList() {
        return myBagList;
    }

    public LiveData<List<ShopItem>> getShops() {
        return shopItemList;
    }

    public LiveData<ShopItem> getCurrentShop() {
        return currentShop;
    }

    public LiveData<String> getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentShop(int shopId) {
        currentShop = mainRepository.getCurrentShop(shopId);
    }

    public void emptyMyBag() {
        mainRepository.emptyMyBag();
        mainRepository.setCurrentShopToNull();
        mainRepository.setMyBagToNUll();
    }


    public void signOutUser() {
        mainRepository.signOutUser();
    }

    public void setLocation(String location) {
        mainRepository.setLocation(location);
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage() {
        mainRepository.setToastMesage();
    }
}
