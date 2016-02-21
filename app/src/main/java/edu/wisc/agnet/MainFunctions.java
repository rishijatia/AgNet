package edu.wisc.agnet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainFunctions extends AppCompatActivity {
    Button forAzure;
    Button forMaps;
    Button forDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_functions);
        //forAzure=(Button) findViewById(R.id.marketbtn);
        //Typeface type= Typeface.createFromAsset(getAssets(), "arial.ttf");
        //forAzure.setTypeface(type);
        //forAzure.setTextColor(Color.WHITE);
       // forMaps=(Button) findViewById(R.id.mapButton);
       // forMaps.setTypeface(type);
        //forMaps.setTextColor(Color.WHITE);
        //forDetails=(Button) findViewById(R.id.dataBtn);
      //  forDetails.setTypeface(type);
       // forDetails.setTextColor(Color.WHITE);

    }
    public void callMapActivity(View view){
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);
    }

    public void loadData(View view){
        Intent intent=new Intent(this, DataActivity.class);
        startActivity(intent);
    }
    public void azureDB(View view){
        Intent intent=new Intent(this, ToDoActivity.class);
        startActivity(intent);
    }
}
