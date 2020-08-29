package com.test.appgate.data;

import androidx.lifecycle.LiveData;

import com.test.appgate.data.database.TestDao;
import com.test.appgate.data.database.Users;
import com.test.appgate.data.dto.Response;
import com.test.appgate.data.local.LocalSource;
import com.test.appgate.data.remote.RemoteSource;

import java.util.List;

public class TestRepository implements DataSource {

    private TestDao mWordDao;
    private LiveData<List<Users>> mAllUsers;

    private RemoteSource remoteRepository;
    private LocalSource localRepository;


    public TestRepository(RemoteSource remoteRepository, LocalSource localRepository) {
        this.remoteRepository =remoteRepository;
        this.localRepository =localRepository;
    }

    @Override
    public LiveData<List<Response>> getAllValidation(String user) {

        return localRepository.getAllValidation(user);
    }

    @Override
    public void insertUser(String user, String password) {
        localRepository.insertNewUser(user,password);
    }

    @Override
    public void insertCompanies(Response list) {
        localRepository.insertRegister(list);

    }

    @Override
    public void registerUser(String username, String password, String lat, String lon, OperationCallback callback) {
        remoteRepository.callRegister(callback,lat,lon);

    }

    @Override
    public Users validateUser(String username, String password) {
        return localRepository.getUser(username,password);

    }

}
