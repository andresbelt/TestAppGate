package com.test.appgate.data;

import androidx.lifecycle.LiveData;

import com.test.appgate.data.database.Users;
import com.test.appgate.data.dto.Response;

import java.util.List;

public interface DataSource {

    LiveData<List<Response>> getAllValidation(String user);

    void insertUser(String user, String password);
    void insertCompanies(Response list);

    void registerUser(String username, String password, String lat, String lon,OperationCallback callback);
    Users validateUser(String username, String password);

}