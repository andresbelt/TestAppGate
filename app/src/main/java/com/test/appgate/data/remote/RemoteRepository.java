package com.test.appgate.data.remote;

import com.test.appgate.data.dto.Response;
import com.test.appgate.data.service.ApiClient;
import com.test.appgate.data.OperationCallback;

public class RemoteRepository implements RemoteSource {

    private ApiClient api;

    public RemoteRepository(ApiClient api) {
        this.api= api;
    }


    @Override
    public void callRegister(OperationCallback<Response> callback,String lat, String lon) {
        api.callRegisterDownload(callback,lat,lon);
    }
}
