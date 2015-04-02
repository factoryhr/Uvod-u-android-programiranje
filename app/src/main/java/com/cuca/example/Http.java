package com.cuca.example;

import android.util.Log;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Http {
    private static Http ourInstance = new Http();

    public static Http getInstance() {
        return ourInstance;
    }

    private Http() {
    }

    public String post(String url){

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);


        try {
            HttpResponse httpResponse = client.execute(httpPost);
            StatusLine sl = httpResponse.getStatusLine();

            Log.d("CUCA", "Status line " + sl);

            if(sl.getStatusCode() == 200){
                InputStream is = httpResponse.getEntity().getContent();

                BufferedReader reader = new BufferedReader( new InputStreamReader(is) );

                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null){
                    stringBuilder.append(line);
                }

                is.close();

                return stringBuilder.toString();

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
