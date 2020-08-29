package com.test.appgate.data;

public interface OperationCallback<T> {
    void onSuccess(T data);
    void onError(String error);
}