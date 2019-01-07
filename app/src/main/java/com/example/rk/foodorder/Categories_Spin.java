package com.example.rk.foodorder;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Categories_Spin extends Activity {
    TextView textView;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;
    Spinner spinner;
    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayList<String> list;
    ListView listView;
    FoodAdaptor foodAdaptor;
    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    Categories_Spin categories_spin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories_spin=this;
        setContentView(R.layout.activity_categories__spin);
        spinner=findViewById(R.id.spinner);
        listView=findViewById(R.id.lstFood);
        foodAdaptor=new FoodAdaptor(this,R.layout.food_listview);
        listView.setAdapter(foodAdaptor);
        //context=View.getContext;
        list=new ArrayList<>();
        listItems=new ArrayList<>();
        adapter=new ArrayAdapter<String>(this,R.layout.category_spinner,R.id.txt,listItems);
        json_string=getIntent().getExtras().getString("jsonString");
        try {
            jsonObject=new JSONObject(json_string);
            jsonArray=jsonObject.getJSONArray("server_response");
            //Toast.makeText(this, ""+jsonArray, Toast.LENGTH_SHORT).show();
            int count=0;
            String name,id;
            while (count<jsonArray.length())
            {

                JSONObject jo=jsonArray.getJSONObject(count);
                name=jo.getString("name");
                id=jo.getString("category_id");
                //CategoryDetails categoryDetails=new CategoryDetails(id,name);
                //categoryAdaptor.add(categoryDetails);
                //ListViewClickItemArray.add(jo.getString("name"));
               // Toast.makeText(this, "loop"+count, Toast.LENGTH_SHORT).show();
              //  Toast.makeText(this, ""+jo.getString("name"), Toast.LENGTH_SHORT).show();
                list.add(jo.getString("name"));
                count++;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        spinner.setAdapter(adapter);
        listItems.addAll(list);
        adapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Categories_Spin.this, ""+spinner.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                String catName=spinner.getItemAtPosition(i).toString();
                //listView.refreshDrawableState();
                FoodBackround fb=new FoodBackround(Categories_Spin.this);
                fb.execute(catName);

                //Toast.makeText(Categories_Spin.this, ""+catName, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        //BackTask bt=new BackTask();
        //bt.execute();
    }
     class FoodBackround extends AsyncTask<String,String,String>
     {
         String JSON_STRING;
         AlertDialog alertDialog;
         String json_url;
         Context context1;
         ProgressDialog progressDialog;
         FoodBackround(Context context)
         {
             this.context1=context;
         }

         @Override
         protected void onPreExecute() {
             super.onPreExecute();
             //10.0.2.2
             json_url="http://10.0.2.2/FoodOrder/Android/foodAval.php?";
             progressDialog = new ProgressDialog(context1);
             progressDialog.setMessage("Loading Foods");
             progressDialog.setCancelable(false);
             progressDialog.show();
             alertDialog=new AlertDialog.Builder(context1).create();
             alertDialog.setTitle("Food");
         }
         @Override
         protected String doInBackground(String... strings) {
             String catName=strings[0];
             try {
                 URL url=new URL(json_url);
                 HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                 httpURLConnection.setRequestMethod("POST");
                 httpURLConnection.setDoOutput(true);
                 httpURLConnection.setDoInput(true);
                 OutputStream outputStream=httpURLConnection.getOutputStream();
                 BufferedWriter bufferdWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                 String data= URLEncoder.encode("categoryname","UTF-8")+"="+URLEncoder.encode(catName,"UTF-8");
                 bufferdWriter.write(data);
                 bufferdWriter.flush();
                 bufferdWriter.close();
                 InputStream inputStream=httpURLConnection.getInputStream();
                 BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                 StringBuilder stringBuilder=new StringBuilder();
                 while ((JSON_STRING=bufferedReader.readLine())!=null)
                 {
                     stringBuilder.append(JSON_STRING+"\n");
                 }
                 bufferedReader.close();
                 inputStream.close();
                 httpURLConnection.disconnect();
                 return  stringBuilder.toString().trim();
             }
             catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             }

             return null;
         }

         @Override
         protected void onPostExecute(String s) {
             super.onPostExecute(s);
             json_string=s;
             //Toast.makeText(Categories_Spin.this, "ans"+s.toString(), Toast.LENGTH_SHORT).show();
             if (progressDialog.isShowing())
                 progressDialog.dismiss();
             //Toast.makeText(Categories_Spin.this, "ans"+s.toString(), Toast.LENGTH_SHORT).show();

             try {
                 jsonObject=new JSONObject(json_string);
                 jsonArray=jsonObject.getJSONArray("server_response");
                 int count=0;
                 String name,id,price,category_id;
                 foodAdaptor.list.clear();
                 while (count<jsonArray.length())
                 {
                     JSONObject jo=jsonArray.getJSONObject(count);
                     name=jo.getString("name");
                     id=jo.getString("food_id");
                     price=jo.getString("price");
                     category_id=jo.getString("category_id");
                     //Toast.makeText(Categories_Spin.this, "loop"+count, Toast.LENGTH_SHORT).show();
                     //CategoryDetails categoryDetails=new CategoryDetails(id,name);
                     FoodDetails foodDetails=new FoodDetails(id,name,price,category_id);
                     //categoryAdaptor.add(categoryDetails);
                     foodAdaptor.add(foodDetails);
                     foodAdaptor.notifyDataSetChanged();
                     //ListViewClickItemArray.add(jo.getString("name"));
                     count++;
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }


     }

    /*private  class BackTask extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list;
        String JSON_STRING;
        String json_url;
        JSONArray jsonArray;
        String json_str;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list=new ArrayList<>();

            json_url="http://192.168.1.103/FoodOrder/Android/catJSON.php?";

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                InputStream inputStream=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder=new StringBuilder();
                while ((JSON_STRING=bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(JSON_STRING+"\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                json_str=stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                 jsonArray=new JSONArray(json_str);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    list.add(jsonObject.getString("name"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            listItems.addAll(list);
            adapter.notifyDataSetChanged();
        }
    }*/
}
