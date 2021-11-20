package com.cookandroid.personal_mobile_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.skt.Tmap.TMapView;

public class TMap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tmap);

        LinearLayout linearLayoutTmap = (LinearLayout)findViewById(R.id.linearLayoutTmap);
        TMapView tMapView = new TMapView(this);

        tMapView.setSKTMapApiKey( "l7xxdfe6d51c76ce4e268427e032687e71cb" );
        linearLayoutTmap.addView( tMapView );





    }
}