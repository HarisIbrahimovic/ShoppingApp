package com.example.shoppingapplication.favorites;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingapplication.model.ShopItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FavoritesRepository {

    private static FavoritesRepository instance;

    private final ArrayList<Integer> listOfFavorites = new ArrayList<>();
    private final MutableLiveData<List<ShopItem>> shopItems = new MutableLiveData<>();

    public static FavoritesRepository getInstance() {
        if(instance==null) instance = new FavoritesRepository();
        return instance;
    }

    public void setListOfFavorites(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Favorites");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for( DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Integer num = dataSnapshot.getValue(Integer.class);
                        listOfFavorites.add(num);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public LiveData<List<ShopItem>> getShopItems() {
        setListOfFavorites();
        setShopItems();
        return shopItems;
    }

    private void setShopItems() {


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shops");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ShopItem> items = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ShopItem shopItem = dataSnapshot.getValue(ShopItem.class);
                    assert shopItem != null;
                    if(listOfFavorites.contains(shopItem.getId())){
                       items.add(shopItem);
                    }

                }
                shopItems.postValue(items);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
