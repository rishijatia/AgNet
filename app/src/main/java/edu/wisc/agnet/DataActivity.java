package edu.wisc.agnet;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class DataActivity extends AppCompatActivity {

    private static JSONObject weatherData;
    private final OkHttpClient client = new OkHttpClient();
    public TextView textView;
    public static JSONObject zWeatherData;
    private String currentTemp;
    public static StringBuilder stringBuilder;

    static String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_data);
        final Context context=this.getApplicationContext();
        textView=(TextView) findViewById(R.id.textView);
        Log.d("onCreate", "Is textView null? : " + (textView == null));
        Log.d("TextView",R.id.textView+"");
        try{
            run(this);
        }catch(Exception ex){
            Log.d("Error", ex.getMessage());
        }
        textView.setTextColor(Color.WHITE);

//        Log.d("DONE",MainActivity.ssn.toString());
        //textView.setText(weatherData.toString());
    }
    void sText (String wData)
    {
        textView.setText(wData);
    }
    //zWeatherData.getJSONObject("current_observation").optString("temp_c");
    void updateWeatherText() throws JSONException {
        Log.d("Update", "Updated");
        if (zWeatherData != null) {
            Log.d("Run", "Is textView null? : " + (((DataActivity) this).textView == null));
           // textView.setText(zWeatherData.getJSONObject("current_observation").optString("temp_c"));
            currentTemp=zWeatherData.getJSONObject("current_observation").optString("temp_c")+" Celsius";

            stringBuilder=new StringBuilder("Farmer Details \n \n\n");
            stringBuilder.append("First Name: "+MainActivity.ssn.optJSONObject("user").optString("firstname"));
            stringBuilder.append("\n\n");
            stringBuilder.append("Last Name: "+MainActivity.ssn.optJSONObject("user").optString("lastname"));
            stringBuilder.append("\n\n");
            stringBuilder.append("E-mail: "+MainActivity.ssn.optJSONObject("user").optString("email"));
            stringBuilder.append("\n\n");
            stringBuilder.append("Farmer ID: "+MainActivity.ssn.optJSONObject("user").optString("id"));
            stringBuilder.append("\n\n");
            stringBuilder.append("Current Temperature at Farm: "+currentTemp);
            if (textView!=null && stringBuilder!=null)
            textView.setText(stringBuilder);
        }
    }



    public void run(final AppCompatActivity activity){

        Log.d("Run", "Is textView null? : " + (((DataActivity) activity).textView == null));
        String coord =  MainActivity.data.optJSONArray("fields").optJSONObject(0).optJSONObject("centroid").optJSONArray("coordinates").toString();
        coord= coord.substring(coord.indexOf(',')+1,coord.length()-1).trim()+ "," + (coord.substring(1,coord.indexOf(','))).trim() ;
        url = "http://api.wunderground.com/api/73bed923bc2a2392/conditions/q/" + coord+".json";
        Request request = new Request.Builder()
                .url(url)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
                try {
                    weatherData = new JSONObject(response.body().string());
                    if (!weatherData.equals(null)) {
                        //sText(weatherData);

                        zWeatherData = weatherData;
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                    updateWeatherText();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                Log.d("rtest",weatherData.toString());
            }
        });
    }
    }
