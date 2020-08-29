package com.test.appgate.data;

import com.google.gson.Gson;
import com.test.appgate.data.dto.Response;
import com.test.appgate.data.remote.RemoteSource;


public class MockDataApiClient implements RemoteSource {

    @Override
    public void callRegister(OperationCallback<Response> callback, String lat, String lon) {
        Response weatherData = new Gson().fromJson(TestData.DATA_JSON, Response.class);
        callback.onSuccess(weatherData);
    }
}