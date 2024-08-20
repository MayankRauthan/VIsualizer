package com.example.Visualizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle=new Bundle();
        bundle.putInt("val",1);
        getSupportFragmentManager().beginTransaction().add(R.id.container1,new NodesGraph())
                .setReorderingAllowed(true)
                .commit();
    }
}