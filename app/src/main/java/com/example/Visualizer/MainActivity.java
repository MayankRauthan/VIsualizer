package com.example.Visualizer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.Visualizer.Sorting.SortingFragment;
import com.example.Visualizer.shortestpath.NodesGraph;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle=new Bundle();
        bundle.putInt("val",1);
        getSupportFragmentManager().beginTransaction().add(R.id.container1,new SortingFragment())
                .setReorderingAllowed(true)
                .commit();
    }
}