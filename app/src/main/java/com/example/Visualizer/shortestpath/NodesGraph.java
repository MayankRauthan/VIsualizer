package com.example.Visualizer.shortestpath;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Visualizer.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;


public class NodesGraph extends Fragment {


    private int nodeNo;
    public ArrayList<HashMap<Integer,Integer>> adj;
    public Stack<Integer> stack;

    public NodesGraph() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nodeNo=0;
        stack=new Stack<>();
        adj=new ArrayList<>();
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

                    adj.add(new HashMap<>());
                    iv.setOnClickListener(view1 -> {
                        Log.d("customNodeView", "Node clicked");
                        markSelected(iv);
                        //iv.setBackgroundResource(R.drawable.redroundbutton,iv);

                    });


                    view.performClick();
                    return true;
                }
                return false;
            }

        });
    }

    private void markSelected(customNodeView iv) {
        if (iv.currentBackgroundResource == R.drawable.greenroundstartbutton) {
            if(!stack.isEmpty())
            {
                adj.get(stack.peek()).put(iv.nodeNo,iv.nodeNo);
            }
            stack.push(iv.nodeNo);
            Log.i("green to red","clicked");
            iv.setBackgroundResource(R.drawable.redroundbutton);
        }
        else if(iv.currentBackgroundResource==R.drawable.redroundbutton)
        {
            stack.pop();
            if(!stack.isEmpty())
            {
                adj.get(stack.peek()).remove(iv.nodeNo);
            }
            iv.setBackgroundResource(R.drawable.greenroundstartbutton);

        }
        Log.i("adj",adj+"");
        Log.i(" stack", ""+stack);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nodes_graph, container, false);
    }

}