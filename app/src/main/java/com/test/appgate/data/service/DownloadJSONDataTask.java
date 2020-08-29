package com.test.appgate.data.service;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.test.appgate.data.OperationCallback;
import com.test.appgate.data.dto.Response;
import com.test.appgate.ui.MainActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * asynctask download data from server
 */
public class DownloadJSONDataTask extends AsyncTask<String, Integer, Boolean> {
    private static final String TAG = MainActivity.class.getSimpleName();

    private OperationCallback callback;
    private String responseJSON = null;

    public DownloadJSONDataTask(OperationCallback mHandler) {
        callback = mHandler;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "onPreExecute....");
        //show progress bar
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Log.d(TAG, "doInBackground....url " + strings[0]);
        boolean responserror = false;

        try {
            // This is getting the url from the string we passed in
            URL url = new URL(strings[0]);
            // Create the urlConnection
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode == 200) {
                InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
                responseJSON = convertInputStreamToString(inputStream);
                Log.d(TAG, "response....." + responseJSON);
                responserror = true;

            } else {
                // Status code is not 200
                // Do something to handle the error
                responseJSON = "error";
            }

        } catch (Exception e) {
            Log.d(TAG, e.getLocalizedMessage());
            e.printStackTrace();
            responseJSON = "error";
        }
        return responserror;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        super.onPostExecute(response);
        Log.d(TAG, "onPostExecute....response " + response);
    if(response){
        Gson g = new Gson();

        Response p = g.fromJson(responseJSON.toString(), Response.class);

        callback.onSuccess(p);

    } else{
      callback.onError(responseJSON);

    }

    }

    /**
     * get string form input stream
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}