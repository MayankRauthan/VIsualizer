package com.example.Visualizer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class customNodeView extends FrameLayout {
    public customNodeView(@NonNull Context context) {
        super(context);
    }

    public customNodeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public customNodeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public customNodeView(@NonNull Context context,int nodeNo)
    {
        super(context);
        init(nodeNo,context);
    }

    private void init(int nodeNo, Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.nodes, this, true);

        TextView nodeNumberText = (TextView)inflate.findViewById(R.id.node_no);

        // Set the node number
        nodeNumberText.setText(nodeNo+"");
    }

}
