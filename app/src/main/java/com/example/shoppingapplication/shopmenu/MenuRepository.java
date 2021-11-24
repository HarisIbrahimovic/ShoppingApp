package com.example.shoppingapplication.shopmenu;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingapplication.model.MenuItem;
import com.example.shoppingapplication.model.ShopItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuRepository {

    private static MenuRepository instance;
    private final MutableLiveData<List<MenuItem>> menuItemList = new MutableLiveData<>();
    private final MutableLiveData<ShopItem> shopData = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public static MenuRepository getInstance() {
        if(instance==null)instance = new MenuRepository();
        return instance;
    }

    public LiveData<List<MenuItem>> getMenuItemList(int id) {
        setMenuItemList(id);
        return menuItemList;
    }

    public LiveData<ShopItem> getShopData(int id) {
        setShopData(id);
        return shopData;
    }

    private void setShopData(int id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shops");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ShopItem shopItem = dataSnapshot.getValue(ShopItem.class);
                    if(shopItem!=null&&shopItem.getId()==id){
                        shopData.postValue(shopItem);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void setMenuItemList(int id) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Items");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MenuItem> listOfitems = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MenuItem menuItem = dataSnapshot.getValue(MenuItem.class);
                    if(menuItem!=null&&menuItem.getShopId()==id){
                        listOfitems.add(menuItem);
                    }
                }
                menuItemList.postValue(listOfitems);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void addItemToBag(MenuItem menuItem) {
        if(FirebaseAuth.getInstance().getCurrentUser()==null){
            toastMessage.setValue("Please login");
            return;
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bag");
        databaseReference.push().setValue(menuItem);
        toastMessage.setValue("Item added");
    }

    public void setToastMessage(String message) {
        toastMessage.setValue(message);
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public boolean getUserStatus() {
        return FirebaseAuth.getInstance().getCurrentUser() != null;
    }

    public void deleteCurrentBag() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bag");
            databaseReference.removeValue();
            toastMessage.setValue("Bag changed.");
        }
    }

    public void addToFavorites(int id) {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Favorites");
            databaseReference.push().setValue(id);
            toastMessage.setValue("Added.");
        }else toastMessage.setValue("Please login.");
    }
}
