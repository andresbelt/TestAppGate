package com.test.appgate.data.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.test.appgate.data.dto.Response;

import java.util.List;

@Dao
public interface TestDao {

    @Query("SELECT * from users_table")
    LiveData<List<Users>> getUsers();

    @Query("SELECT * from register_table WHERE user =:username")
    LiveData<List<Response>> getAllValidation(String username);

    @Query("SELECT * from users_table WHERE user=:username AND password=:password")
    Users getUser(String username,String password);

    @Insert
    void insert(Users word);

    @Insert
    void insertRegister(Response word);

}
