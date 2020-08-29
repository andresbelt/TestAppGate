package com.test.appgate.data.database;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_table")
public class Users {

    @PrimaryKey(autoGenerate = true)
    int id;


    @NonNull
    @ColumnInfo(name = "user")
    private String user;

    @NonNull
    @ColumnInfo(name = "password")
    private String password;

    public Users(@NonNull String user,@NonNull String password) {
        this.user = user;
        this.password = password;
    }


    @NonNull
    public String getUser() {
        return user;
    }

    @NonNull
    public String getPassword() {
        return password;
    }



}
