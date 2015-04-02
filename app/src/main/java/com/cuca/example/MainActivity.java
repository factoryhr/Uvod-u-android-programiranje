package com.cuca.example;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends Activity {

    ArrayList<ImageItem> imageItems;
    ListView lista;
    ImagesAdapter imagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        lista = (ListView) findViewById(R.id.listView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String response = Http.getInstance().post("http://android.devtvornica.org/instagram.json");
                imageItems = new ArrayList<ImageItem>();

                try {
                    Log.d("CUCA", "Response je " + response);
                    JSONArray data = new JSONObject(response).getJSONArray("data");

                    int totalData = data.length();

                    for(int i = 0; i < totalData; ++i){
                        JSONObject imageData = data.getJSONObject(i);

                        String imageUrl = imageData.getJSONObject("images").getJSONObject("thumbnail").getString("url");
                        String text = imageData.getJSONObject("caption").getString("text");

                        double latitude = 0;
                        double longitude = 0;

                        try {
                            latitude = Double.valueOf(imageData.getJSONObject("location").getString("latitude"));
                            longitude =  Double.valueOf(imageData.getJSONObject("location").getString("longitude"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        ImageItem imageItem = new ImageItem();
                        imageItem.setImageURL(imageUrl);
                        imageItem.setText(text);
                        imageItem.setLongitude(longitude);
                        imageItem.setLatitude(latitude);

                        imageItems.add(imageItem);
                    }

                    imagesAdapter = new ImagesAdapter(getApplicationContext(), imageItems);

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lista.setAdapter(imagesAdapter);
                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
