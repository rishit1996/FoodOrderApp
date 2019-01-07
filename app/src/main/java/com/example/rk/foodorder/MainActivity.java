package com.example.rk.foodorder;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    TextView forgetPass,signUp;
    EditText enterUserName,enterPass;
    SharedPreferences sharedPreferences;
   public static final String loginPref="LoginPref";
    public static final String user="userkey";
    public static final String pass="passkey";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLogin=findViewById(R.id.btnLogin);
        signUp=findViewById(R.id.signUp);
       // forgetPass=findViewById(R.id.forgetPass);
        enterUserName=findViewById(R.id.enterUserName);
        enterPass=findViewById(R.id.enterPass);
//        forgetPass.setText(Html.fromHtml("<u>Forget Password?</u>"));
       /* forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Forget", Toast.LENGTH_SHORT).show();
            }
        });*/

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRegister(view);
            }
        });
    }

    //user Register
    public void userRegister(View view)
    {
        //Toast.makeText(this, "SignUp", Toast.LENGTH_SHORT).show();
         Intent intent=new Intent(this,Register.class);
         startActivity(intent);
    }
    //loginFunction
    public void userLogin(View view)
    {
        //Toast.makeText(this, "Function Call", Toast.LENGTH_SHORT).show();
        String userName=enterUserName.getText().toString();
        String userPass=enterPass.getText().toString();
        loginBackground lb=new loginBackground(this);
        lb.execute(userName,userPass);
        sharedPreferences=getSharedPreferences(loginPref, 0);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(user,userName);
        editor.putString(pass,userPass);
        editor.commit();
        String un="";
        String up="";
        un=sharedPreferences.getString(user,null);
        up=sharedPreferences.getString(pass,null);
        //Toast.makeText(this, "user"+un, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "pass"+up, Toast.LENGTH_SHORT).show();



    }

    @Override
    protected void onStart() {
        super.onStart();
        String checkUser="";
        sharedPreferences=getSharedPreferences(MainActivity.loginPref,0);
        checkUser=sharedPreferences.getString(user,"not");
        //Toast.makeText(this, "user"+checkUser, Toast.LENGTH_SHORT).show();
        if(!checkUser.equals("not"))
        {
            //Toast.makeText(this, "user is not set", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this,Mainmenu.class);
            startActivity(intent);
        }

    }
}
