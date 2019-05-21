package com.example.niaogebiji.myviewapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.niaogebiji.myviewapplication.R;
import com.example.niaogebiji.myviewapplication.view.MyWaterFallLayout;
import com.example.niaogebiji.myviewapplication.view.recyclerview.MyRecyclerAdapter;
import com.example.niaogebiji.myviewapplication.view.recyclerview.TimeLineItemDecoration;

import java.util.ArrayList;

public class RecyclerViewActivity extends AppCompatActivity {

    private ArrayList<String> mDatas =new ArrayList<>();

    private void generateDatas(){
        for (int i=0;i<10;i++){
            if(i == 0){
                mDatas.add("2019-5.21 14:12:00\n你的快件已签收 感谢使用中通快递，期待再次为您服务！");
            }else
                mDatas.add("第 " + i +" 个item");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        generateDatas();

        RecyclerView mRecyclerView = findViewById(R.id.linear_recycler_view);

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        TimeLineItemDecoration dividerItemDecoration = new TimeLineItemDecoration();
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, mDatas);
        mRecyclerView.setAdapter(adapter);

    }



}
