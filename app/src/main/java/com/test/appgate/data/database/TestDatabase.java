package com.test.appgate.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.test.appgate.data.dto.Response;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities={Response.class,Users.class}, version = 1, exportSchema = false)
public abstract class TestDatabase extends RoomDatabase {

    public abstract TestDao testDao();

    private static volatile TestDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TestDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TestDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TestDatabase.class, "test_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
