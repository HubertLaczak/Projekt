package com.example.application;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FirstAdapter extends  RecyclerView.Adapter<FirstAdapter.ExampleViewHolder> {

    private ArrayList<String> mExampleList;
    private OnItemClickListener mListener;

    public interface  OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_Item;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tv_Item = itemView.findViewById(R.id.tv_OnePath);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public FirstAdapter(ArrayList<String> exampleList){
        this.mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recyclerview, viewGroup, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder exampleViewHolder, int i) {
        String currentItem = mExampleList.get(i);
        exampleViewHolder.tv_Item.setText(currentItem);
    }

    @Override
    public int getItemCount()
    {
        return mExampleList.size();
    }
}