package com.test.appgate.ui;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.test.appgate.App;
import com.test.appgate.R;
import com.test.appgate.databinding.ListActivityBinding;
import com.test.appgate.ui.base.BaseActivity;


public class ListValidationActivity extends BaseActivity {

    private MainActivityViewModel viewModel;
    private String user;
    private ListActivityBinding binding;
    private RecyclerView recyclerList;
    private ValidationListAdapter adapter;


    @Override
    protected void initializeViewModel() {
        viewModel = new ViewModelProvider(this,
                App.getInjection().provideViewModelFactory()).get(MainActivityViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Resources res = getResources();
        String title = res.getString(R.string.usuario, user);

        binding.textUser.setText(title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerList = binding.recyclerview;

        adapter = new ValidationListAdapter(this);
        recyclerList.setAdapter(adapter);
        recyclerList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public void observeViewModel() {
        user = getIntent().getStringExtra(MainActivity.NEW_ACTIVITY_REQUEST_CODE);
        viewModel.getAllValidation(user).observe(this, validation -> {
            // Update the cached copy of the words in the adapter.
            adapter.setWords(validation);
        });

    }

    @Override
    protected void initViewBinding() {
        binding = ListActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(MainActivity.NEW_ACTIVITY_REQUEST_CODE,
                getIntent().getStringExtra(MainActivity.NEW_ACTIVITY_REQUEST_CODE));
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        user = savedInstanceState.getString(MainActivity.NEW_ACTIVITY_REQUEST_CODE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
