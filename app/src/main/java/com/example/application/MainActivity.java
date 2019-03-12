package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rvPath)
    RecyclerView rvPath;


    private FirstAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<SingleObject> itemsListUniv = new ArrayList<>(); //lista do adaptera z obiektami SingleObject


    private PathAdapter mPathAdapter;
    private RecyclerView.LayoutManager layoutPathManager;
    private ArrayList<String> pathItems = new ArrayList<>();


    String jsonString;
    String key;
    JSONArray itemArray;  //array który ma obecny poziom
    JSONArray basicArray; //array który ma pierwszy poziom

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        makingJsonObject();
        pathItems.add("Main");


        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new FirstAdapter(itemsListUniv);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new FirstAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                rvPath.scrollToPosition(pathItems.size());
                rvPath.post(new Runnable() {
                    @Override
                    public void run() {
                        rvPath.scrollToPosition(mPathAdapter.getItemCount() - 1);
                    }
                });
                switch (position) {
                    case 0:
                        hasChild(position);
                        break;
                    case 1:
                        hasChild(position);
                        break;
                    case 2:
                        hasChild(position);
                        break;
                    case 3:
                        hasChild(position);
                        break;
                    case 4:
                        hasChild(position);
                        break;
                    case 5:
                        hasChild(position);
                        break;
                }
            }
        });

        rvPath.setHasFixedSize(true);
        layoutPathManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvPath.setLayoutManager(layoutPathManager);
        mPathAdapter = new PathAdapter(pathItems);
        rvPath.setAdapter(mPathAdapter);
        mPathAdapter.setOnItemClickListener(new PathAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                searchLevel(pathItems.get(position), position);
            }
        });
        generateFirstArray();


    }

    private void searchLevel(String s, int position) {
        boolean hasChild;

        for (int i = 0; i < basicArray.length(); i++){
            String temp = null;
            JSONObject tempJSON = null;
            String[] splitStr = s.split("\\s+");

            try {
                temp = String.valueOf(basicArray.get(i));
                tempJSON = new JSONObject(temp);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            Iterator<String> keys = tempJSON.keys();
            key = keys.next();
            String mamCie = null;
            try {
                mamCie = tempJSON.getString(key);
                if (mamCie.equals(splitStr[1])){
                    Toast.makeText(this, mamCie, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void hasChild(int position) {
        if(itemsListUniv.get(position).childState()) {
            pathItems.add(itemsListUniv.get(position).getKey() + " " + itemsListUniv.get(position).getValue());
            JSONObject jsonObject;
            try {

                String myNestedJSON = String.valueOf(itemArray.get(position));
                jsonObject = new JSONObject(myNestedJSON);
                String child = jsonObject.getString("hasChild");

                itemArray = new JSONArray(child);

                itemsListUniv.clear();
                for(int i = 0; i < itemArray.length(); i++){
                    JSONObject tempJson = itemArray.getJSONObject(i);
                    createListItem(tempJson);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private void createListItem(JSONObject tempJson){
        boolean hasChild;
        Iterator<String> keys = tempJson.keys();
        String[] lista = new String[2];
        lista[0] = key = keys.next();
        try {
            lista[1] = tempJson.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (tempJson.has("hasChild")) {
            hasChild = true;
        } else
            hasChild = false;
        itemsListUniv.add(new SingleObject(lista[0], lista[1], hasChild));
        mAdapter.notifyDataSetChanged();
    }


    private void generateFirstArray() {
        try {
            for (int i = 0; i < itemArray.length(); i++) {
                JSONObject jsonobject = itemArray.getJSONObject(i);
                createListItem(jsonobject);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void makingJsonObject() {
        InputStream is = getResources().openRawResource(R.raw.tree);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        jsonString = writer.toString();
        try {
            itemArray = new JSONArray(jsonString);
            basicArray = new JSONArray(jsonString);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
