package com.example.shoppingapplication.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.login.LoginActivity;
import com.example.shoppingapplication.main.fragments.BagFragment;
import com.example.shoppingapplication.main.fragments.HomeFragment;
import com.example.shoppingapplication.main.fragments.MapsFragment;
import com.example.shoppingapplication.main.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private Fragment selectedFragment;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel =  new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.init();
        setUpViews();
        observe();
    }

    private void observe() {

        viewModel.getCurrentUser().observe(this, aBoolean -> {
            if(aBoolean==null){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
            else if(!aBoolean && !getIntent().hasExtra("skip")){
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        viewModel.getCurrentFragment().observe(this, integer -> {
            switch (integer){
                case 1:
                    selectedFragment = new HomeFragment();
                    break;
                case 2:
                    selectedFragment = new MapsFragment();
                    break;
                case 3:
                    selectedFragment = new BagFragment();
                    break;
                case 4:
                    selectedFragment = new ProfileFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFrame,selectedFragment).commit();
        });

        viewModel.getMyBagList().observe(this,list->{
            if(list!=null){
                viewModel.setCurrentShop(list.get(0).getShopId());
            }
        });
    }


    @SuppressLint("NonConstantResourceId")
    private void setUpViews() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.mainMenu);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.pocetna:
                    viewModel.setCurrentFragment(1);
                    break;
                case R.id.search:
                    viewModel.setCurrentFragment(2);
                    break;
                case R.id.korpa:
                    viewModel.setCurrentFragment(3);
                    break;
                case R.id.user:
                    viewModel.setCurrentFragment(4);
                    break;
            }
            return true;
        });
    }
}