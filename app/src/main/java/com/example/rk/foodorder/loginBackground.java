package com.example.rk.foodorder;

/**
 * Created by Rk on 31-03-2018.
 */
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.Intent;

public class loginBackground extends AsyncTask<String, String, String>
{

    AlertDialog alertDialog;
    Context context;
    Intent intent;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String userName;
    String userPass;

    loginBackground(Context context)
    {
        this.context=context;
    }
    @Override
    protected void onPreExecute() {
        //Toast.makeText(context, "pre", Toast.LENGTH_SHORT).show();
        super.onPreExecute();
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login");
        intent=new Intent(context,Mainmenu.class);
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Logging In");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected String doInBackground(String... params) {

        userName=params[0];
         userPass=params[1];
        String response="";
        String line="";
        //10.0.2.2
        String loginUrl="http://10.0.2.2/FoodOrder/Android/loginAndro.php?";
        try {
            URL url=new URL(loginUrl);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream=httpURLConnection.getOutputStream();
            BufferedWriter bufferdWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
            String data= URLEncoder.encode("username","UTF-8")+"="+URLEncoder.encode(userName,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(userPass,"UTF-8");
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
        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        //Toast.makeText(context, "Post", Toast.LENGTH_SHORT).show();
       // Toast.makeText(context, ""+result, Toast.LENGTH_SHORT).show();
        alertDialog.setMessage(result);
        String res="Login Sucessful";
        if(result.equals(res)) {
            //Toast.makeText(context, "inside if", Toast.LENGTH_SHORT).show();
            if(progressDialog.isShowing())
                progressDialog.dismiss();
            Toast.makeText(context, "Successfully Logged In", Toast.LENGTH_SHORT).show();

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(intent);
        }
        else
        {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
            alertDialog.setMessage("Error in Login");
            alertDialog.show();
        }
    }
}
