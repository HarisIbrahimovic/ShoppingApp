package com.example.shoppingapplication.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;

public class LoginRepository {

    private static LoginRepository instance;
    private final MutableLiveData<Boolean> currentUser = new MutableLiveData<>();
    private final MutableLiveData<Boolean> errorAccrued = new MutableLiveData<>();

    public static LoginRepository getInstance() {
        if(instance==null)instance=new LoginRepository();
        return instance;
    }

    public void loginUser(String email, String password){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                currentUser.postValue(true);
            }else{
                errorAccrued.postValue(true);
            }
        });
    }

    public void signUpUser(String email, String password){
        if(email.isEmpty()||password.isEmpty()){
            errorAccrued.setValue(true);
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                currentUser.postValue(true);
            }else{
                errorAccrued.postValue(true);
            }
        });
    }

    public LiveData<Boolean> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Boolean> getErrorAccrued() {
        return errorAccrued;
    }

    public void setError() {
        errorAccrued.setValue(false);
    }


}
