package com.test.appgate.data.local;


import androidx.lifecycle.LiveData;

import com.test.appgate.data.database.Users;
import com.test.appgate.data.dto.Response;
import java.util.List;

public interface LocalSource {

    LiveData<List<Users>>getAllUsers();
    LiveData<List<Response>>getAllValidation(String user);

    Users getUser(String user,String password);
    void insertNewUser(String user,String password);
    void insertRegister(Response list);

}
