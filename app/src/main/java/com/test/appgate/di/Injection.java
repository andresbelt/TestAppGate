package com.test.appgate.di;

import androidx.lifecycle.ViewModelProvider;
import com.test.appgate.App;
import com.test.appgate.data.DataSource;
import com.test.appgate.data.TestRepository;
import com.test.appgate.data.database.TestDatabase;
import com.test.appgate.data.local.LocalRepository;
import com.test.appgate.data.local.LocalSource;
import com.test.appgate.data.remote.RemoteRepository;
import com.test.appgate.data.remote.RemoteSource;
import com.test.appgate.data.service.ApiClient;
import static com.test.appgate.util.Constants.API_BASE_URL;

public class Injection {

    private App app;
    private TestDatabase database;
    private LocalSource localRepository;
    private RemoteSource remoteRepository;
    private DataSource testDataSource;

    private ApiClient api = new ApiClient()
            .setUrl(API_BASE_URL)
            .build();
    private ViewModelFactory testViewModelFactory;


    public Injection(App app){
        this.app = app;
        database= TestDatabase.getDatabase(app);
        localRepository=new LocalRepository(providerDatabase());
        remoteRepository  = new RemoteRepository(api);
        testDataSource = new TestRepository(remoteRepository,localRepository);
        testViewModelFactory = new ViewModelFactory(providerRepository());
    }

    public DataSource providerRepository(){
        return testDataSource;
    }

    TestDatabase providerDatabase(){
        return database;
    }

    public ViewModelProvider.Factory provideViewModelFactory(){
        return testViewModelFactory;
    }
}
