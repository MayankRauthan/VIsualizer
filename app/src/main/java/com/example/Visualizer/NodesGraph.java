package com.example.Visualizer;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class NodesGraph extends Fragment {


    private int nodeNo;

    public NodesGraph() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodeNo=0;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomRelativeLayout zone = (CustomRelativeLayout)view.findViewById(R.id.relativeLayout);
        zone.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    customNodeView iv = new customNodeView(getContext(),nodeNo++);
                    iv.setLayoutParams(new ViewGroup.LayoutParams(150, 150));
                    iv.setX(event.getX()-75);
                    iv.setY(event.getY()-75);
                    zone.addView(iv);
                    iv.setOnClickListener((view1)->{
                        iv.setBackgroundColor(Color.RED);

                    });

                    view.performClick();
                    return true;
                }
                return false;
            }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nodes_graph, container, false);
    }

}