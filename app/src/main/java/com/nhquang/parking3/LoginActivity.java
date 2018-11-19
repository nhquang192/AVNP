package com.nhquang.parking3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.nhquang.parking3.UITask.LoginTask;

import Models.Base.ClientConfig;
import Online.API.APILogin;

public class LoginActivity extends AppCompatActivity {

    EditText login_edt_username;
    EditText login_edt_password;
    Button login_btn_login;
    Button login_btn_tracuu;

    Context mycontext;
    Activity myactivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ClientConfig.CURRENT_USER="";
        mycontext=getBaseContext();
        myactivity=this;
        FindViewByID();
        SetEventToView();
    }

    public void FindViewByID()
    {
        login_btn_login=(Button)findViewById(R.id.login_btn_login);
        login_btn_tracuu=(Button)findViewById(R.id.login_btn_TraCuu);
        login_edt_password=(EditText)findViewById(R.id.login_edt_password);
        login_edt_username=(EditText)findViewById(R.id.login_edt_username);
    }
    public void SetEventToView()
    {
        login_btn_login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=login_edt_username.getText().toString();
                String password=login_edt_password.getText().toString();

                if(username.equals("KSVIT") && password.equals("KsvIT085780"))
                {
                    Intent intent=new Intent(myactivity,ConfigAct.class);
                    startActivity(intent);
                }
                else {
                    APILogin apiLogin = new APILogin(mycontext);
                    apiLogin.GetToken(username, password);
                    LoginTask loginTask = new LoginTask(mycontext, myactivity, apiLogin,true);
                    loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
        login_btn_tracuu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String username="catruong";
                String password="12345678";
                if(username.equals("KSVIT") && password.equals("KsvIT085780"))
                {
                    Intent intent=new Intent(myactivity,ConfigAct.class);
                    startActivity(intent);
                }
                else {
                    APILogin apiLogin = new APILogin(mycontext);
                    apiLogin.GetToken(username, password);
                    LoginTask loginTask = new LoginTask(mycontext, myactivity, apiLogin,false);
                    loginTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        login_edt_username.setText("");
        login_edt_password.setText("");
    }
}
