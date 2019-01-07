package com.example.rk.foodorder; /**
 * Created by Rk on 05-04-2018.
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

import com.example.rk.foodorder.SplashScreen;

public class registerBackground extends AsyncTask<String,String,String> {
    AlertDialog alertDialog;
    Context context;
    Intent intent;
    registerBackground(Context context)
    {
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Registration");
        intent=new Intent(this.context,MainActivity.class);
    }
    @Override
    protected String doInBackground(String... strings) {
        String response="";
        String line="";
        String fname,lname,email,mobile,address,username,password;
        fname=strings[0];
        lname=strings[1];
        email=strings[2];
        mobile=strings[3];
        address=strings[4];
        username=strings[5];
        password=strings[6];
        String loginUrl="http://192.168.1.105/FoodOrder/Android/registerAndro.php?";
        try {
            URL url=new URL(loginUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferdWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data= URLEncoder.encode("fname","UTF-8")+"="+URLEncoder.encode(fname,"UTF-8")
                    +"&"+URLEncoder.encode("lname","UTF-8")+"="+URLEncoder.encode(lname,"UTF-8")
                    +"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")
                    +"&"+URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(mobile,"UTF-8")
                    +"&"+URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address,"UTF-8")
                    +"&"+URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(username,"UTF-8")
                    +"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8");
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
        Toast.makeText(context, ""+s, Toast.LENGTH_SHORT).show();
        alertDialog.setMessage(s);

        String res="Successfully Registerd";
        if(s.equals(res)) {
            //Toast.makeText(context, "inside if"+s, Toast.LENGTH_SHORT).show();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.getApplicationContext().startActivity(intent);
        }
        else
        {
            alertDialog.show();
        }

    }


}
