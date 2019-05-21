package com.example.niaogebiji.myviewapplication.view.recyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.niaogebiji.myviewapplication.R;

import java.util.ArrayList;

/***
 * 2019.5.21
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;

    public MyRecyclerAdapter(Context context,ArrayList<String> datas){
        mContext = context;
        mDatas = datas;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalViewHolder(inflater.inflate(R.layout.item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        NormalViewHolder normalViewHolder = (NormalViewHolder) viewHolder;
        if(position == 0){
            normalViewHolder.mTv.setBackgroundColor(Color.parseColor("#293c9c"));
        }else if(position == 1){
            normalViewHolder.mTv.setBackgroundColor(Color.parseColor("#4fc3f7"));
        }
        normalViewHolder.mTv.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class  NormalViewHolder extends RecyclerView.ViewHolder{
        private TextView mTv;

       public NormalViewHolder(@NonNull View itemView) {
           super(itemView);
           mTv = itemView.findViewById(R.id.item_tv);
       }
   }

}
