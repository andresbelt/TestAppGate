package com.test.appgate.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.test.appgate.ui.MainActivityViewModel;

import com.test.appgate.data.DataSource;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private DataSource repository;

    public ViewModelFactory(DataSource repository) {
        this.repository =repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(repository);
    }
}
