package com.example.shoppingapplication.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {


    private LoginRepository loginRepository;

    private LiveData<Boolean> currentUserStatus;
    private LiveData<Boolean> errorState;
    private final MutableLiveData<Integer> state = new MutableLiveData<>();
    private final MutableLiveData<String> toastMessage = new MutableLiveData<>();

    public void init(){
        if(loginRepository==null){
            loginRepository = LoginRepository.getInstance();
            currentUserStatus = loginRepository.getCurrentUser();
            errorState = loginRepository.getErrorAccrued();
            state.setValue(1);
        }
    }

    public void loginUser(String email,String password, String state){

        if(state.equals("Login"))loginRepository.loginUser(email, password);
        else loginRepository.signUpUser(email, password);
    }


    public LiveData<Boolean> getCurrentUserStatus() {
        return currentUserStatus;
    }

    public LiveData<Boolean> getErrorState() {
        return errorState;
    }

    public LiveData<Integer> getState() {
        return state;
    }

    public void setError() {
        loginRepository.setError();
    }

    public void setState(int num) {
        state.setValue(num);
    }



}
