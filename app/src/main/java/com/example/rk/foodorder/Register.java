package com.example.rk.foodorder;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import android.content.Intent;


public class Register extends Activity {
    EditText firstName,lastName,emailId,mobileNum,addressC,userName,userPassword;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailId = findViewById(R.id.emailId);
        mobileNum = findViewById(R.id.mobileNum);
        addressC = findViewById(R.id.address);
        userName = findViewById(R.id.userName);
        userPassword = findViewById(R.id.userPassword);
        btnRegister = findViewById(R.id.btnRegister);

    }
    public void register(View view)
    {
        String fname,lname,email,mobile,address,username,password;
        fname=firstName.getText().toString();
        lname=lastName.getText().toString();
        email=emailId.getText().toString();
        mobile=mobileNum.getText().toString();
        address=addressC.getText().toString();
        username=userName.getText().toString();
        password=userPassword.getText().toString();
        registerBackground rb=new registerBackground(this);
        rb.execute(fname,lname,email,mobile,address,username,password);
    }
}
