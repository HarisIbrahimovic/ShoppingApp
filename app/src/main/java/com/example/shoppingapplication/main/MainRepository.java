package com.example.shoppingapplication.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.shoppingapplication.model.BasicItem;
import com.example.shoppingapplication.model.Item;
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

public class MainRepository {

    private static MainRepository instance;

    public MutableLiveData<List<BasicItem>>  basicItemList = new MutableLiveData<>();
    public MutableLiveData<List<ShopItem>>  shopItemList = new MutableLiveData<>();
    public MutableLiveData<List<Item>>  categoryItemsList = new MutableLiveData<>();
    public MutableLiveData<List<Item>>  newItemsList = new MutableLiveData<>();
    public MutableLiveData<List<MenuItem>>  myBagList = new MutableLiveData<>();
    public MutableLiveData<Boolean>  currentUser = new MutableLiveData<>();
    public MutableLiveData<ShopItem>  currentShop = new MutableLiveData<>();
    public MutableLiveData<String>  toastMessage = new MutableLiveData<>();
    public MutableLiveData<String>  currentLocation = new MutableLiveData<>();

    public static MainRepository getInstance(){
        if(instance == null){
            instance = new MainRepository();
        }
        return instance;
    }

    public LiveData<List<BasicItem>> getBasicItemList(){
        setBasicItemList();
        return basicItemList;
    }

    public LiveData<List<Item>> getCategoryItemsList() {
        setCatItemList();
        return categoryItemsList;
    }

    private void setCatItemList() {
        ArrayList<Item> listOfItems = new ArrayList<>();
        Item item1 = new Item("Slatkiš","https://previews.123rf.com/images/dxinerz/dxinerz1506/dxinerz150601089/41323237-lolly-candy-sweet-icon-vector-image-can-also-be-used-for-eatables-food-and-drinks-suitable-for-use-o.jpg");
        Item item2 = new Item("Biblioteka","https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Closed_Book_Icon.svg/1200px-Closed_Book_Icon.svg.png");
        Item item3 = new Item("Cvjećara","https://cdn.iconscout.com/icon/free/png-256/flower-2494153-2086063.png");
        Item item4 = new Item("Burger","https://cdn-icons-png.flaticon.com/512/198/198416.png");
        Item item5 = new Item("Pizza","https://cdn-icons-png.flaticon.com/512/3132/3132693.png");
        Item item6 = new Item("Piletina","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTAjpMZh7JTF_Xh-Joe0CQhO_EMYO2oUCtCPw&usqp=CAU");
        Item item7 = new Item("Supe","https://cdn-icons-png.flaticon.com/512/189/189146.png");
        Item item8 = new Item("Sendvići","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRONgWBVEc5i2YqtlV1ISqS4Vt0rfXARVhpAA&usqp=CAU");
        listOfItems.add(item1);
        listOfItems.add(item2);
        listOfItems.add(item3);
        listOfItems.add(item4);
        listOfItems.add(item5);
        listOfItems.add(item6);
        listOfItems.add(item7);
        listOfItems.add(item8);
        categoryItemsList.setValue(listOfItems);
    }


    private void setBasicItemList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Basic");
        ArrayList<BasicItem> list = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    BasicItem item = dataSnapshot.getValue(BasicItem.class);
                    list.add(item);
                }
                basicItemList.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public LiveData<List<Item>> getNewItemList() {
        setNewItemList();
        return newItemsList;
    }

    private void setNewItemList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("New");
        ArrayList<Item> list = new ArrayList<>();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Item item = dataSnapshot.getValue(Item.class);
                    list.add(item);
                }
                newItemsList.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public LiveData<List<MenuItem>> getMyBagList() {
        setMyBag();
        return myBagList;
    }

    private void setMyBag() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bag");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<MenuItem> menuItems = new ArrayList<>();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        MenuItem item = dataSnapshot.getValue(MenuItem.class);
                        menuItems.add(item);
                        myBagList.postValue(menuItems);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

    public void checkUser() {
        currentUser.setValue(FirebaseAuth.getInstance().getCurrentUser() != null);
    }

    public LiveData<Boolean> getCurrentUser() {
        checkUser();
        return currentUser;
    }

    public LiveData<List<ShopItem>> getShopItems() {
        setShopItemList();
        return shopItemList;
    }

    private void setShopItemList() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shops");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ShopItem> shopItems = new ArrayList<>();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ShopItem item = dataSnapshot.getValue(ShopItem.class);
                    shopItems.add(item);
                    shopItemList.postValue(shopItems);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public MutableLiveData<ShopItem> getCurrentShop(int shopId) {
        setCurrentShop(shopId);
        return currentShop;
    }

    private void setCurrentShop(int shopId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Shops");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    ShopItem item = dataSnapshot.getValue(ShopItem.class);
                    if(item!=null&&item.getId()==shopId) {
                        currentShop.postValue(item);
                        break;
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setCurrentShopToNull(){
        currentShop.setValue(null);
    }
    public void setMyBagToNUll(){
        myBagList.setValue(null);
    }

    public void emptyMyBag() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Bag");
            databaseReference.removeValue();
        }
    }

    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();

    }

    public void setLocation(String location) {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Location");
            databaseReference.setValue(location);
            toastMessage.setValue("Location updated.");
        }else toastMessage.setValue("Please login.");
    }

    public LiveData<String> getToastMessage() {
        return toastMessage;
    }

    public LiveData<String> getCurrentLocation(){
        setCurrentLocation();
        return currentLocation;
    }

    private void setCurrentLocation() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Location");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    currentLocation.postValue(snapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else currentLocation.setValue("Lokacija");

    }

    public void setToastMesage() {
        toastMessage.setValue("null");
    }
}
