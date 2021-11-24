package com.example.shoppingapplication.category;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingapplication.model.ShopItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryRepository {

    private static CategoryRepository instance;
    private final MutableLiveData<List<ShopItem>> shops = new MutableLiveData<>();
    public static CategoryRepository getInstance(){
        if(instance==null)instance = new CategoryRepository();
        return instance;
    }


    public LiveData<List<ShopItem>> getListOfShops(String category) {
        setShops(category);
        return shops;
    }

    private void setShops(String category) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shops");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ShopItem> shopItems = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ShopItem item = dataSnapshot.getValue(ShopItem.class);
                    assert item != null;
                    if(item.getCategories()!=null && item.getCategories().contains(category)){
                        shopItems.add(item);

                    }
                }
                shops.postValue(shopItems);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
