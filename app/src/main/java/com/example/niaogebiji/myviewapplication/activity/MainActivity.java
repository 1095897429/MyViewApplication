package com.example.niaogebiji.myviewapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.niaogebiji.myviewapplication.R;
import com.example.niaogebiji.myviewapplication.view.MyWaterFallLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //布局二
        final MyWaterFallLayout myWaterFallLayout = findViewById(R.id.myWaterFallLayout);
        findViewById(R.id.addPic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    addView(myWaterFallLayout);
                }
            }
        });
    }

    private void addView(MyWaterFallLayout myWaterFallLayout) {
        int [] news = new int[]{R.mipmap.water,R.mipmap.getcontent};
        int i = (int) (Math.random() * 2);
        int x = news[i];
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(x);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        myWaterFallLayout.addView(imageView);

        myWaterFallLayout.setOnItemClickListener(new MyWaterFallLayout.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int index) {
                Toast.makeText(MainActivity.this, "item=" + index, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
