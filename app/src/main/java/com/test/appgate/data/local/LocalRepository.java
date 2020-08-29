package com.test.appgate.data.local;

import androidx.lifecycle.LiveData;
import com.test.appgate.data.database.TestDatabase;
import com.test.appgate.data.database.Users;
import com.test.appgate.data.dto.Response;
import java.util.List;

public class LocalRepository implements LocalSource {

    private TestDatabase database;

    public LocalRepository(TestDatabase database) {
        this.database =database;
    }

    @Override
    public LiveData<List<Users>> getAllUsers() {
        return database.testDao().getUsers();
    }

    @Override
    public LiveData<List<Response>> getAllValidation(String user) {
        return database.testDao().getAllValidation(user);
    }

    @Override
    public Users getUser(String user, String password) {
           return database.testDao().getUser(user,password);
    }

    @Override
    public void insertNewUser(String user,String password) {
        Users userNew = new Users(user,password);
        database.databaseWriteExecutor.execute(() -> {
            database.testDao().insert(userNew);
        });
    }

    @Override
    public void insertRegister(Response list) {
        database.databaseWriteExecutor.execute(() -> {
            database.testDao().insertRegister(list);
        });
    }

}
