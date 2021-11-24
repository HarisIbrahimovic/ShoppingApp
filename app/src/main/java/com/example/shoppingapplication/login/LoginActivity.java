package com.example.shoppingapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.shoppingapplication.R;
import com.example.shoppingapplication.main.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel viewModel;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView loginText;
    private TextView singUpText;
    private TextView skipText;
    int state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        viewModel.init();
        setUpViews();
        observe();
        onClicks();
    }

    private void onClicks() {
        loginText.setOnClickListener(v -> viewModel.setState(1));
        singUpText.setOnClickListener(v->viewModel.setState(2));
        skipText.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            intent.putExtra("skip","uValue");
            startActivity(intent);
        });

        passwordEditText.setOnEditorActionListener((v, actionId, event) -> {
            if(state == 1) {
                viewModel.loginUser(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim(),
                        "Login"
                );
            }else{
                viewModel.loginUser(emailEditText.getText().toString().trim(),
                    passwordEditText.getText().toString().trim(),
                    "SignUp"
                 );
            }
            return true;
        });


    }

    private void observe() {
        viewModel.getCurrentUserStatus().observe(this, aBoolean -> {
            if(aBoolean){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }

        });
        viewModel.getErrorState().observe(this, aBoolean -> {
            if(aBoolean){
                Toast.makeText(LoginActivity.this,"Error occurred.",Toast.LENGTH_SHORT).show();
                viewModel.setError();
            }
            });

        viewModel.getState().observe(this,numState->{
            if(numState==1){
                loginText.setTextColor(getColor(R.color.teal_200));
                singUpText.setTextColor(getColor(R.color.black));
                state = 1;
            }else {
                singUpText.setTextColor(getColor(R.color.teal_200));
                loginText.setTextColor(getColor(R.color.black));
                state = 2;
            }
        });
    }

    private void setUpViews() {
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginText = findViewById(R.id.prijavaText);
        singUpText = findViewById(R.id.registracijaText);
        skipText = findViewById(R.id.skipButton);
    }
}