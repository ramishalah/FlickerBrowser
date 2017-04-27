package org.example.rami.flickerbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.MalformedInputException;

/**
 * Created by Rami on 3/8/2017.
 */
enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

class GetRawData extends AsyncTask<String, Void, String> {
    private static final String TAG = "GetRawData";
    private final OnDownloadComplete mCallback;
    private DownloadStatus mDownloadStatus;

    interface OnDownloadComplete{
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callback) {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mCallback = callback;
    }

    void runOnSameThread(String s){
        Log.d(TAG, "runOnSameThread: starts");

        if(mCallback != null){
            mCallback.onDownloadComplete(doInBackground(s), DownloadStatus.OK);
        }
//        onPostExecute(doInBackground(s));

        Log.d(TAG, "runOnSameThread: ends");
    }

    @Override
    protected void onPostExecute(String s) {
       // Log.d(TAG, "onPostExecute: The parameter is: " + s);
        if(mCallback != null){
            mCallback.onDownloadComplete(s, mDownloadStatus);
        }
        Log.d(TAG, "onPostExecute: Ends");
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        if(params == null){
            mDownloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try{
            mDownloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code is: " + response);


            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            while(null != (line = reader.readLine())){
                result.append(line).append('\n');
            }

            mDownloadStatus = DownloadStatus.OK;
            return result.toString();

        }catch (MalformedInputException e){
            Log.e(TAG, "doInBackground: Invalid URL" + e.getMessage());
        }catch (IOException e){
            Log.e(TAG, "doInBackground: IO Exception reading data" + e.getMessage());
        }catch (SecurityException e){
            Log.e(TAG, "doInBackground: Error connetino to internet. neeed permision" + e.getMessage());
        }finally {
            if(connection != null){
                connection.disconnect();
            }
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    Log.e(TAG, "doInBackground: Error closing the reader" + e.getMessage());
                }
            }
        }

        mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
