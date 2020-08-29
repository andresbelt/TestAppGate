package com.test.appgate.ui;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.snackbar.Snackbar;
import com.test.appgate.App;
import com.test.appgate.R;
import com.test.appgate.Resource;
import com.test.appgate.data.database.Users;
import com.test.appgate.databinding.ActivityMainBinding;
import com.test.appgate.ui.base.BaseActivity;
import com.test.appgate.util.Constants;

import java.util.concurrent.TimeUnit;

public class MainActivity extends BaseActivity {

    private MainActivityViewModel viewModel;
    public static final String NEW_ACTIVITY_REQUEST_CODE = "ACTIVATION";
    private  String username;
    private ActivityMainBinding binding;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private Location currentLocation = null;
    private int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private LocationManager manager;


    @Override
    protected void initializeViewModel() {
        viewModel = new ViewModelProvider(this,
                App.getInjection().provideViewModelFactory()).get(MainActivityViewModel.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        manager= (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE );

        binding.btnRegistrar.setOnClickListener(view -> {

            username = binding.edittextUsername.getText().toString();
            String password = binding.edittextPassword.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                viewModel.createUser(
                        binding.edittextUsername.getText().toString(),
                        binding.edittextPassword.getText().toString()
                );
            } else {
                showMessage(getResources().getString(R.string.empty_error));
            }
        });

        binding.btnValidar.setOnClickListener(view -> {

            username = binding.edittextUsername.getText().toString();
            String password = binding.edittextPassword.getText().toString();

            boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            if(statusOfGPS || currentLocation != null){

                if (!username.isEmpty() && !password.isEmpty())
                {
                    viewModel.validateUser(
                            binding.edittextUsername.getText().toString(),
                            binding.edittextPassword.getText().toString(),
                            String.valueOf(currentLocation.getLatitude()),
                            String.valueOf(currentLocation.getLongitude()));
                }else{
                    showMessage(getResources().getString(R.string.empty_error));
                }

            }else{
                showMessage(getResources().getString(R.string.location_error));
            }


        });

        locationRequest();
    }

   private void responseUser(Resource<Users> resource){
        switch (resource.status) {
            case SUCCESS: {
                binding.pbLoading.setVisibility(View.GONE);
                showMessage(getResources().getString(R.string.bienvenido));
                if(resource.data != null){
                    Intent intent = new Intent(
                            MainActivity.this,
                            ListValidationActivity.class);
                    intent.putExtra(NEW_ACTIVITY_REQUEST_CODE,username);
                    startActivity(intent);
                }
                break;
            }
            case LOADING: {
            binding.pbLoading.setVisibility(View.VISIBLE);
                break;

            }
            case ERROR: {
                binding.pbLoading.setVisibility(View.GONE);
                showMessage(getResources().getString(R.string.usuario_error));
                break;
            }
        }
    }

    private void responseCreateUser(Resource<String> resource){
        switch (resource.status) {
            case SUCCESS: {
                binding.pbLoading.setVisibility(View.GONE);
                showMessage(getResources().getString(R.string.registrado));
                break;
            }
            case LOADING: {
                binding.pbLoading.setVisibility(View.VISIBLE);
                break;
            }
            case ERROR: {
                binding.pbLoading.setVisibility(View.GONE);
                showMessage(getResources().getString(R.string.error_create_user));
                break;
            }
        }
    }

    private void showMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void observeViewModel() {
        viewModel.mUser.observe(this, response -> {
            responseUser(response);
        });

        viewModel.mResult.observe(this, response -> {
            responseCreateUser(response);
        });
    }

    @Override
    protected void initViewBinding() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(view);
    }

    private boolean foregroundPermissionApproved() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        );
    }

    private void locationRequest() {

        if (foregroundPermissionApproved()) {
                locationClient();
            } else {
                requestForegroundPermissions();
            }

        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationClient();
                } else {

                    Snackbar.make(binding.getRoot(),
                            getResources().getString(R.string.permission_denied_explanation),
                            Snackbar.LENGTH_LONG)
                            .setAction(getResources().getString(R.string.settings), view -> {
                                Intent intent = new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts(
                                        "package",
                                        getPackageName(),
                                        null);

                                intent.setData(uri);
                                startActivity(intent);
                            }).show();
                }
            }
        }
    }

    void subscribeToLocationUpdates() {
        Log.d(Constants.TAG, "subscribeToLocationUpdates()");

        try {
            // TODO: Step 1.5, Subscribe to location changes.
            fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest, locationCallback, Looper.myLooper());
        } catch (SecurityException e) {
            Log.e(Constants.TAG, getResources().getString(R.string.location_lost));
        }
    }

    private void requestForegroundPermissions() {

        boolean provideRationale = foregroundPermissionApproved();

        if (provideRationale) {
            Snackbar.make(
                    binding.getRoot(),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_LONG
            )
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String[] PERMISSIONS = {
                                    android.Manifest.permission.ACCESS_FINE_LOCATION
                            };

                            ActivityCompat.requestPermissions(
                                    MainActivity.this,
                                    PERMISSIONS,
                                    REQUEST_PERMISSIONS_REQUEST_CODE
                            );
                        }
                    }).show();
        } else {
            Log.d(Constants.TAG, "Request foreground only permission");
            String[] PERMISSIONS = {
                    android.Manifest.permission.ACCESS_FINE_LOCATION
            };

            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS,
                    REQUEST_PERMISSIONS_REQUEST_CODE
            );
        }
    }



    public void unsubscribeToLocationUpdates() {
        Log.d(Constants.TAG, "unsubscribeToLocationUpdates()");

        try {
            // TODO: Step 1.6, Unsubscribe to location changes.
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        } catch (Exception e) {
            Log.e(Constants.TAG, getResources().getString(R.string.location_lost));
        }
    }

    void locationClient(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(TimeUnit.SECONDS.toMillis(60));
        locationRequest.setFastestInterval(TimeUnit.SECONDS.toMillis(30));
        locationRequest.setMaxWaitTime(TimeUnit.MINUTES.toMillis(2));
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                if (locationResult.getLastLocation() != null) {
                    currentLocation = locationResult.getLastLocation();
                } else {
                    Log.d(Constants.TAG, getResources().getString(R.string.location_error));
                }
            }
        };

        subscribeToLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unsubscribeToLocationUpdates();
    }
}