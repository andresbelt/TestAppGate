package com.test.appgate.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.test.appgate.Resource;
import com.test.appgate.data.DataSource;
import com.test.appgate.data.OperationCallback;
import com.test.appgate.data.database.Users;
import com.test.appgate.data.dto.Response;
import com.test.appgate.util.SecurityUtils;

import java.util.List;


public class MainActivityViewModel extends ViewModel {


    private DataSource repository;

    MutableLiveData<Resource<Users>> mUser = new MutableLiveData<>();
    MutableLiveData<Resource<String>> mResult = new MutableLiveData<>();

    public MainActivityViewModel(DataSource repository) {
        this.repository =repository;
    }

    LiveData<List<Response>> getAllValidation(String user){
        return repository.getAllValidation(user);
    }

    public void validateUser(String user, String password, String lat, String lon){
        mUser.setValue(Resource.loading(null));
        repository.registerUser(user, SecurityUtils.encryption(password),lat,lon,new OperationCallback() {
            @Override
            public void onSuccess(Object data) {
                validateUser(data,user,password);
            }

            @Override
            public void onError(String error) {
                mUser.setValue(Resource.error(null));
            }
        });

    }


    void createUser(String user, String password){
        mResult.setValue(Resource.loading(null));
        if(SecurityUtils.isValidPassword(password)){
            repository.insertUser(user, SecurityUtils.encryption(password));
            mResult.setValue(Resource.success(null));
        }else{
            mResult.setValue(Resource.error(null));
        }
    }


    void addValidation(Object data, String user, boolean result){
        new Thread(() -> {
        Response respond = (Response) data;
        respond.setUser(user);
        respond.setResult(result);
        repository.insertCompanies(respond);
        }).start();

    }

    void validateUser(Object data, String user, String password){

        new Thread(() -> {
            Users mUSer = repository.validateUser(user, SecurityUtils.encryption(password));
            if(mUSer != null){
              // repository.updateResult(user,true);
                addValidation(data,user,true);
                mUser.postValue(Resource.success(mUSer));

            }else{
                addValidation(data,user,false);
                mUser.postValue(Resource.error(null));

            }


        }).start();
    }

}
