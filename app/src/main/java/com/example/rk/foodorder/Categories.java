package com.example.rk.foodorder;

import android.app.Activity;
import android.os.Bundle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Categories extends Activity {
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    CategoryAdaptor categoryAdaptor;
    ListView listView;
    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        categoryAdaptor=new CategoryAdaptor(this,R.layout.category_listview);
        listView=findViewById(R.id.catList);
        listView.setAdapter(categoryAdaptor);
        json_string=getIntent().getExtras().getString("jsonString");
        try {
            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            int count=0;
            String name,id;
             while (count<jsonArray.length())
             {
                JSONObject jo=jsonArray.getJSONObject(count);
                name=jo.getString("name");
                id=jo.getString("category_id");
                 Toast.makeText(this, "loop"+count, Toast.LENGTH_SHORT).show();
                CategoryDetails categoryDetails=new CategoryDetails(id,name);
                categoryAdaptor.add(categoryDetails);
                 ListViewClickItemArray.add(jo.getString("name"));
                count++;
             }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Categories.this, "Clickd"+ListViewClickItemArray.get(i).toString(), Toast.LENGTH_SHORT).show();
                String selecctedCategory=ListViewClickItemArray.get(i);
            }
        });
    }


}
