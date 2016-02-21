package edu.wisc.agnet;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.android.volley.*;
//import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.climate.login.LoginButton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoginButton.LoginListener {

    private static final String TAG = "MainActivity";

    //TextView textView;
    static JSONObject ssn;
    static JSONObject data;
    public static StringBuilder stringBuilder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        LoginButton loginButton = (LoginButton) findViewById(R.id.login);
        loginButton.registerListener(this);
        loginButton.setCredentials("dpdus1aoi0k55b", "933to8vvii2pgf6oq6sv1lf22d");

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
    @Override
    public void onLogin(final JSONObject session) {

        ssn=session;
        Log.d("SSN",ssn.toString());
        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, "https://hackillinois.climate.com/api/fields", null, new com.android.volley.Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        data=response;
                        Log.d(TAG, "fields: ");
                        try {
                            Log.d(TAG, response.toString(2));
                            if(response.has("error")) {
                                //textView.setText(response.optString("error_description"));
                            } else {
                                JSONArray fields = response.optJSONArray("fields");
                                stringBuilder = new StringBuilder("Fields:\n\n");
                                for (int i = 0; i < fields.length(); ++i) {
                                    stringBuilder.append(fields.optJSONObject(i).optString("name"));
                                    stringBuilder.append("\n");
                                    stringBuilder.append(fields.optJSONObject(i).optJSONObject("centroid").optJSONArray("coordinates").toString());
                                    stringBuilder.append("\n");
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: " + error.getMessage());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> headers = super.getHeaders();
                HashMap<String, String> map = new HashMap<>();
                map.putAll(headers);
                String auth = null;
                auth = "Bearer " + session.opt("access_token");
                map.put("Authorization", auth);
                return map;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsObjRequest);

        Intent intent=new Intent(this, MainFunctions.class);
        startActivity(intent);

    }

    @Override
    public void onError(Exception exception) {

    }

}

