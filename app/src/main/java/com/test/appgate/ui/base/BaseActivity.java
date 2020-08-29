package com.test.appgate.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected abstract void initializeViewModel();
    public abstract void observeViewModel();
    protected abstract void initViewBinding();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViewBinding();
        initializeViewModel();
        observeViewModel();

    }
}
