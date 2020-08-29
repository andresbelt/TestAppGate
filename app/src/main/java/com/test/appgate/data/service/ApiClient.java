package com.test.appgate.data.service;

import com.test.appgate.data.OperationCallback;

public class ApiClient implements IBuilder {

  private String url;


  @Override
  public ApiClient build() {
    ApiClient api = new ApiClient();
    api.setUrl(this.url);
    return api;
  }


  public ApiClient setUrl(String url){
    this.url = url;
    return this;
  }

  /**
   * Retrieves the Register asynchronously.
   */
  public void callRegisterDownload(OperationCallback callback,String lat, String lon) {
   String urlGet = "timezoneJSON?formatted=true&lat="+lat+"&lng="+lon+"&username=qa_mobile_easy&style=full";
    new DownloadJSONDataTask(callback).execute(url+urlGet);
  }
}