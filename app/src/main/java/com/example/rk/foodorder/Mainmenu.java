package com.example.rk.foodorder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import android.content.Intent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Mainmenu extends Activity {
    Button btnCategories,btnOrders,btnHistory,btnLogout;
    Intent intent;
    String json_string;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        btnCategories = findViewById(R.id.btnRegister);
       // btnOrders = findViewById(R.id.btnOrders);
        btnLogout=findViewById(R.id.btnLogout);
        //btnHistory = findViewById(R.id.btnHistory);
        intent = new Intent(this, Categories_Spin.class);
    }
    public void catBtnClick(View view)
    {
        //Toast.makeText(this, "Categories", Toast.LENGTH_SHORT).show();
        new BackGroundTask(this).execute();
        //startActivity(intent);

    }
    public void ordBtnClick(View view)
    {
        Toast.makeText(this, "Order", Toast.LENGTH_SHORT).show();

        startActivity(intent);
    }
    public void hisBtnClick(View view)
    {
        Toast.makeText(this, "History", Toast.LENGTH_SHORT).show();
    }
    public void userLogOutBtnClick(View view)
    {
        sharedPreferences=getSharedPreferences(MainActivity.loginPref,0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.commit();
        intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.startActivity(intent);
    }
    class BackGroundTask extends AsyncTask<Void,Void,String>
    {
        String JSON_STRING;
        String json_url;
        ProgressDialog progressDialog;
        Context context;
        BackGroundTask(Context context)
        {
            this.context=context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //10.0.2.2
            json_url="http://10.0.2.2/FoodOrder/Android/catJSON.php?";
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Fetching Categories");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
        @Override
        protected String doInBackground(Void... voids) {

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
                return stringBuilder.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
        @Override
        protected void onPostExecute(String result) {
            //super.onPostExecute(aVoid);
            json_string=result;
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            //Toast.makeText(Mainmenu.this, ""+result, Toast.LENGTH_SHORT).show();
            intent.putExtra("jsonString",json_string);
            startActivity(intent);
        }
    }
}
