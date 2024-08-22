package com.example.Visualizer.Traversal;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.Visualizer.R;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class Node{
    int i,j;
    Stack<Node>stack;

    public Node(int i, int j) {
        this.i=i;
        this.j=j;
    }
    public Node(int i,int j,Stack<Node>st)
    {
        this.i=i;
        this.j=j;
        this.stack=(Stack<Node>) st.clone();
    }
}
public class HomeScreenFragment extends Fragment {
    private ImageView[][] matrix ; // 2D array to store buttons
    private GridLayout gridLayout;

    private int noOfClick;
    private Node src,dest;
    boolean vis[][];
    private boolean processing=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_layout_1,container,false);
        gridLayout=(GridLayout) view.findViewById(R.id.grid_layout);
        populateMatrix();
        return view;
    }

    private void populateMatrix() {
        matrix = new ImageView[15][10];
        vis=new boolean[15][10];

        for(int i=0;i<15;i++)
        {
            for(int j=0;j<10;j++)
            {
                ImageView b=new ImageView(getContext());
                b.setBackgroundColor(Color.GRAY);
                float density = getResources().getDisplayMetrics().density;

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        (int) (24 * density),
                        (int) (24 * density)
                );

                params.setMargins(16,16,16,16);
                b.setLayoutParams(params);
                gridLayout.addView(b);
                matrix[i][j]=b;

                //setting on click listner to set up src ,dest and block based on no of click
                int finalI = i;
                int finalJ = j;
                matrix[i][j].setOnClickListener(v->{
                    noOfClick++;
                    if(noOfClick==1){
                        v.setBackgroundResource(R.drawable.star_svgrepo_com);
                        animateDestinationReached(matrix[finalI][finalJ]);
                        src=new Node(finalI, finalJ,new Stack<>());

                    }

                    else if(noOfClick==2)
                    {
                        if(src.i==finalI&&src.j==finalJ)
                        {
                            noOfClick--;
                            return;
                        }

                        v.setBackgroundResource(R.drawable.destination_ensign_flag_svgrepo_com);
                        animateDestinationReached(matrix[finalI][finalJ]);
                        dest=new Node(finalI, finalJ);

                    }
                    else {
                        if(!vis[finalI][finalJ]&&!(finalI== src.i&&finalJ== src.j)&&!(finalI== dest.i&&finalJ== dest.j)) {
                            vis[finalI][finalJ] = true;
                            v.setBackgroundResource(R.drawable.wall_svgrepo_com);
                            animateDestinationReached(matrix[finalI][finalJ]);
                        }
                    }

                });
            }
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton button = view.findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                if(noOfClick<=1) {
                    displayMessage("No src and destination selected");
                    return;
                }
                if(processing)
                    return;
                processing=true;
                Thread t=new Thread(()->travel());
                t.start();
            }
        });

    }

    private void travel() {


        Queue<Node>q=new LinkedList<>();
        q.add(src);
        while(!q.isEmpty())
        {

            Node temp=q.poll();
            int i=temp.i,j=temp.j;
            vis[i][j]=true;





            int nrow[]={1,-1,0,0,1,-1,-1,1};
            int ncol[]={0,0,-1,1,1,-1,1,-1};

            for(int index=0;index<8;index++)
            {
                int row=nrow[index]+i;
                int col=ncol[index]+j;
                if(row>=0&&col>=0&&row<15&&col<10&&!vis[row][col])
                {
                    if(row==dest.i&&col==dest.j) {
                        animateDestinationReached(matrix[row][col]);
                        reversePath(matrix,temp);
                        displayMessage("Destination reached");
                        try {
                            Thread.sleep(4000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        cleanup();
                        return;
                    }
                    getActivity().runOnUiThread(() ->matrix[row][col].setBackgroundColor(Color.GREEN));

                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    vis[row][col]=true;
                    Node neigh= new Node(row,col,temp.stack);
                    neigh.stack.add(neigh);
                    q.add(neigh);
                }
            }
        }
        displayMessage("Cant reach Destination");
        cleanup();

    }

    private void cleanup() {
        getActivity().runOnUiThread(() -> {
                    for (int x = 0; x <= 14; x++) {
                        for (int y = 0; y <= 9; y++) {
                            matrix[x][y].setBackgroundColor(Color.GRAY);
                        }
                    }
                });
        vis=new boolean[15][10];
        noOfClick=0;
        processing=false;
    }

    private void displayMessage(String s)
    {
        getActivity().runOnUiThread(() ->
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show()
        );
    }

    private void reversePath(ImageView[][] matrix, Node temp) {

            while (!temp.stack.isEmpty())
            {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Node node=temp.stack.pop();
                getActivity().runOnUiThread(()-> matrix[node.i][node.j].setBackgroundColor(Color.YELLOW));
                animateDestinationReached(matrix[node.i][node.j]);

            }
            animateDestinationReached(matrix[src.i][src.j]);
    }

    private void animateDestinationReached(ImageView imageView) {
        // Create scale animations
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.5f);

        // Set the duration for the animations
        scaleX.setDuration(500); // 300 milliseconds
        scaleY.setDuration(500);

        // Create an AnimatorSet to play the animations together
        AnimatorSet scaleAnim = new AnimatorSet();
        scaleAnim.playTogether(scaleX, scaleY);

        // Revert the scale back to normal after the initial scale-up
        scaleAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ObjectAnimator scaleXReverse = ObjectAnimator.ofFloat(imageView, "scaleX", 1.5f, 1f);
                ObjectAnimator scaleYReverse = ObjectAnimator.ofFloat(imageView, "scaleY", 1.5f, 1f);

                AnimatorSet reverseAnim = new AnimatorSet();
                reverseAnim.playTogether(scaleXReverse, scaleYReverse);
                reverseAnim.setDuration(300);
                reverseAnim.start();
            }
        });

        // Start the animation
        getActivity().runOnUiThread(()->scaleAnim.start());
    }

}
