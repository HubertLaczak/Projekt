package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> itemsList1 = new ArrayList<>();
    private ArrayList<String> itemsList2 = new ArrayList<>();
    private ArrayList<String> itemsList3 = new ArrayList<>();
    private ArrayList<String> itemsList4 = new ArrayList<>();


    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.rvPath)RecyclerView rvPath;


    private FirstAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> itemsListUniv = new ArrayList<>();

    private PathAdapter mPathAdapter;
    private RecyclerView.LayoutManager layoutPathManager;
    private ArrayList<String> pathItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        generateArray();
//        pathItems.add("Main");

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FirstAdapter(itemsListUniv);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FirstAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                pathItems.add(itemsListUniv.get(position));
//                rvPath.scrollToPosition(pathItems.size());
//                rvPath.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        rvPath.scrollToPosition(mPathAdapter.getItemCount() - 1);
//                    }
//                });
                itemsListUniv.clear();
                switch (position) {
                    case 0:
                        itemsListUniv.addAll(itemsList1);
                        break;
                    case 1:
                        itemsListUniv.addAll(itemsList2);
                        break;
                    case 2:
                        itemsListUniv.addAll(itemsList3);
                        break;
                    case 3:
                        itemsListUniv.addAll(itemsList4);
                        break;
                }

                mAdapter.notifyDataSetChanged();
//                mPathAdapter.notifyDataSetChanged();
            }
        });

//        rvPath.setHasFixedSize(true);
//        layoutPathManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false);
//        rvPath.setLayoutManager(layoutPathManager);
//        mPathAdapter = new PathAdapter(pathItems);
//        rvPath.setAdapter(mPathAdapter);
//        mPathAdapter.setOnItemClickListener(new PathAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
////                TODO
//            }
//        });



    }



    private void generateArray() {

        itemsList1.add("Jeden");
        itemsList1.add("Dwa");
        itemsList1.add("Trzy");
        itemsList1.add("Cztery");

        itemsList2.add("Dwa");
        itemsList2.add("Trzy");
        itemsList2.add("Cztery");
        itemsList2.add("Jeden");

        itemsList3.add("Trzy");
        itemsList3.add("Cztery");
        itemsList3.add("Jeden");
        itemsList3.add("Dwa");

        itemsList4.add("Cztery");
        itemsList4.add("Jeden");
        itemsList4.add("Dwa");
        itemsList4.add("Trzy");

        itemsListUniv.addAll(itemsList1);
    }

}
