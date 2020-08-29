package com.test.appgate;

import android.app.Application;
import android.content.Context;

import com.test.appgate.di.Injection;


public class App extends Application {

    private static App instance =  null;
    private static Injection injection;


    public static Injection getInjection() {
        if (null == injection) {
            injection = new Injection(instance);
        }
        return injection;
    }

    public Context applicationContext(){
        return instance.applicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        injection = createInjection();
    }

    private Injection createInjection() {
        return new Injection(this);
    }
}
