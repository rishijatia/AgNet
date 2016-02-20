package edu.wisc.agnet;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.climate.login.LoginButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements LoginButton.LoginListener {

    private static final String TAG = "MainActivity";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        textView= (TextView) findViewById(R.id.textView);
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


        final JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, "https://hackillinois.climate.com/api/fields", null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "fields: ");
                        try {
                            Log.d(TAG, response.toString(2));
                            if(response.has("error")) {
                                textView.setText(response.optString("error_description"));
                            } else {
                                JSONArray fields = response.optJSONArray("fields");
                                StringBuilder stringBuilder = new StringBuilder("Fields:\n\n");
                                for (int i = 0; i < fields.length(); ++i) {
                                    stringBuilder.append(fields.optJSONObject(i).optString("name"));
                                    stringBuilder.append("\n");
                                    stringBuilder.append(fields.optJSONObject(i).optJSONObject("centroid").optJSONArray("coordinates").toString());
                                    stringBuilder.append("\n");
                                }
                                textView.setText(stringBuilder.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

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

    }

    @Override
    public void onError(Exception exception) {

    }
}

