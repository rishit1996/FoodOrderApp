package com.example.rk.foodorder;

/**
 * Created by Rk on 23-04-2018.
 */
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import android.app.AlertDialog;
import android.widget.Toast;
import android.content.Intent;

public class OrderFood extends AsyncTask<String,String,String>{
    AlertDialog alertDialog;
    Context context;
    Intent intent;
    OrderFood(Context context)
    {
        this.context=context;
    }
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Order");
        intent=new Intent(this.context,MainActivity.class);
    }
    @Override
    protected String doInBackground(String... strings) {
        String response="";
        String line="";
        String username,userpass,foodname,foodqty;
        username=strings[0];
        userpass=strings[1];
        foodname=strings[2];
        foodqty=strings[3];
        //10.0.2.2
        String loginUrl="http://10.0.2.2/FoodOrder/Android/addOrder.php?";
        try {
            URL url=new URL(loginUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferdWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                    +"&"+URLEncoder.encode("userpass","UTF-8")+"="+URLEncoder.encode(userpass,"UTF-8")
                    +"&"+URLEncoder.encode("foodname","UTF-8")+"="+URLEncoder.encode(foodname,"UTF-8")
                    +"&"+URLEncoder.encode("foodqty","UTF-8")+"="+URLEncoder.encode(foodqty,"UTF-8");
            bufferdWriter.write(data);
            bufferdWriter.flush();
            bufferdWriter.close();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

            while ((line=bufferedReader.readLine())!=null)
            {
                response+=line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            //return response;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }


    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
        alertDialog.setMessage(s);

        String res="Order Placed";
        String res1="Error in Order";
        String res2="Order Already Placed";
        if(s.equals(res)) {
            Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();

        }
        else if(s.equals(res1))
        {
            alertDialog.setMessage("Something is Wrong");
            alertDialog.show();
        }
        else if(s.equals(res2))
        {
            alertDialog.setMessage("Order Already Placed");
            alertDialog.show();
        }
        else
        {
            alertDialog.setMessage("Order Already Placed");
            alertDialog.show();
        }

    }

}
