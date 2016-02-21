package edu.wisc.agnet;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;


import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;


import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class MapActivity extends AppCompatActivity {
    private GoogleMap map;
    private static LatLng center;
    private String latlng;
    private static final LatLng DAVAO = new LatLng(7.0722, 125.6131);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataActivity obj = new DataActivity();
        obj.run(obj);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        latlng=MainActivity.data.optJSONArray("fields").optJSONObject(0).optJSONObject("centroid").optJSONArray("coordinates").toString();
        String lat =latlng.substring(1,latlng.indexOf(','));
        lat=lat.trim();
        String lng = latlng.substring(latlng.indexOf(',') + 1, latlng.length() - 1);
        lng=lng.trim();
        LatLng center1 = new LatLng(Double.parseDouble(lng),Double.parseDouble(lat));
        int col=0x55ff0000;
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        CircleOptions circleOptions = new CircleOptions()
                .center(new LatLng(Double.parseDouble(lng),Double.parseDouble(lat)))
                .radius(1000);
        try {
            if (Double.parseDouble(DataActivity.zWeatherData.getJSONObject("current_observation").optString("temp_c")) <20)
                col=0x5500ff00;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        circleOptions.fillColor(col);
        circleOptions.strokeColor(Color.TRANSPARENT);
        circleOptions.visible(true);
        Circle circle = map.addCircle(circleOptions);
        String name = MainActivity.data.optJSONArray("fields").optJSONObject(0).optString("name");
        Marker davao = map.addMarker(new MarkerOptions().position(center1).title(name));

int col2=0x55ff0000;
        latlng=MainActivity.data.optJSONArray("fields").optJSONObject(1).optJSONObject("centroid").optJSONArray("coordinates").toString();
        String lat2 =latlng.substring(1, latlng.indexOf(','));
        lat2=lat2.trim();
        String lng2 = latlng.substring(latlng.indexOf(',') + 1, latlng.length() - 1);
        lng2=lng2.trim();
        LatLng center2 = new LatLng(Double.parseDouble(lng2),Double.parseDouble(lat2));
        String name2 = MainActivity.data.optJSONArray("fields").optJSONObject(1).optString("name");
        Marker davao2 = map.addMarker(new MarkerOptions().position(center2).title(name2));
        try {
            if (Double.parseDouble(DataActivity.zWeatherData.getJSONObject("current_observation").optString("temp_c")) <20)
                col2=0x5500ff00;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CircleOptions circleOptions1 = new CircleOptions()
                .center(new LatLng(Double.parseDouble(lng2),Double.parseDouble(lat2)))
                .radius(1000);
        circleOptions1.fillColor(col2);
        circleOptions1.visible(true);
        circleOptions1.strokeColor(Color.TRANSPARENT);
        Circle circle1 = map.addCircle(circleOptions1);
int col3 = 0x55ff0000;
        latlng=MainActivity.data.optJSONArray("fields").optJSONObject(2).optJSONObject("centroid").optJSONArray("coordinates").toString();
        String lat3 =latlng.substring(1, latlng.indexOf(','));
        lat3=lat3.trim();
        String lng3 = latlng.substring(latlng.indexOf(',') + 1, latlng.length() - 1);
        lng3=lng3.trim();
        LatLng center3 = new LatLng(Double.parseDouble(lng3),Double.parseDouble(lat3));
        String name3 = MainActivity.data.optJSONArray("fields").optJSONObject(2).optString("name");
        Marker davao3 = map.addMarker(new MarkerOptions().position(center3).title(name3));
        try {
            if (Double.parseDouble(DataActivity.zWeatherData.getJSONObject("current_observation").optString("temp_c")) <20)
                col3=0x5500ff00;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        CircleOptions circleOptions2 = new CircleOptions()
                .center(new LatLng(Double.parseDouble(lng3),Double.parseDouble(lat3)))
                .radius(1000);
        circleOptions2.strokeColor(Color.TRANSPARENT);
        circleOptions2.fillColor(col3);
        circleOptions2.visible(true);
        Circle circle2 = map.addCircle(circleOptions2);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(davao.getPosition());
        builder.include(davao2.getPosition());
        builder.include(davao3.getPosition());
        LatLngBounds bounds = builder.build();
       // center= new LatLng((center1.latitude+center2.latitude+center3.latitude)/3,(center1.longitude+center2.longitude+center3.longitude)/3
        //);
        int padding=0;
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width,height,padding);
        // zoom in the camera to Davao city
        //map.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 15));
        map.moveCamera(cu);
        // animate the zoom process
        //map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
        map.animateCamera(cu);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

