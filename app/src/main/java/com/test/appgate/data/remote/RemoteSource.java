package com.test.appgate.data.remote;


import com.test.appgate.data.dto.Response;
import com.test.appgate.data.OperationCallback;

public interface RemoteSource {
    void callRegister(OperationCallback<Response> callback,String lat, String lon);
}