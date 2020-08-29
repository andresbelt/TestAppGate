package com.test.appgate.test;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.test.appgate.data.LiveDataTestUtil;
import com.test.appgate.data.MockDataApiClient;
import com.test.appgate.data.OperationCallback;
import com.test.appgate.data.database.TestDao;
import com.test.appgate.data.database.TestDatabase;
import com.test.appgate.data.database.Users;
import com.test.appgate.data.dto.Response;
import com.test.appgate.util.SecurityUtils;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UsersDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private TestDao mWordDao;
    private TestDatabase mDb;
    private static final String USER = "user";

    private static final String PASSWORD = "PASSWORD";


    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        mDb = Room.inMemoryDatabaseBuilder(context, TestDatabase.class)
                // Allowing main thread queries, just for testing.
                .allowMainThreadQueries()
                .build();
        mWordDao = mDb.testDao();
    }

    @After
    public void closeDb() {
        mDb.close();
    }

    @Test
    public void insertNewUser() throws Exception {
        Users userNew = new Users(USER, SecurityUtils.encryption(PASSWORD));
        mWordDao.insert(userNew);
        List<Users> allWords = LiveDataTestUtil.getValue(mWordDao.getUsers());
        assertEquals(allWords.get(0).getUser(), userNew.getUser());
        assertEquals(allWords.get(0).getPassword(), userNew.getPassword());
    }

    @Test
    public void getUser() {

        Users userNew = new Users(USER, SecurityUtils.encryption(PASSWORD));
        mWordDao.insert(userNew);

        Users allWords = mWordDao.getUser(USER,SecurityUtils.encryption(PASSWORD));
        assertEquals(allWords.getUser(), userNew.getUser());
        assertEquals(allWords.getPassword(), allWords.getPassword());
    }


    @Test
    public void getAllValidation() {
        MockDataApiClient userNew = new MockDataApiClient();
        userNew.callRegister(new OperationCallback<Response>() {
            @Override
            public void onSuccess(Response data) {
                data.setResult(true);
                data.setUser("user");
                mWordDao.insertRegister(data);
                List<Response> allWords = null;
                try {
                    allWords = LiveDataTestUtil.getValue(mWordDao.getAllValidation("user"));
                    assertEquals(allWords.get(0).getUser(), "user");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {

            }
        },"","");


    }


}
