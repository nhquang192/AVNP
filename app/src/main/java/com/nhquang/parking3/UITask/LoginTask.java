package com.nhquang.parking3.UITask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nhquang.parking3.InOutActivity;
import com.nhquang.parking3.InOutStatisticActivity;
import com.nhquang.parking3.R;

import org.json.JSONException;
import org.json.JSONObject;

import CTLs.JSONtoClass;
import Models.Base.ClientConfig;
import Models.CustomClass.Token;
import Online.Volley.VolleyRequestMethod;

/**
 * Created by QUANG-PC on 11/30/2017.
 */

public class LoginTask  extends AsyncTask<Void, Integer, Void> {

    //region View
    EditText login_edt_username;
    EditText login_edt_password;
    Button login_btn_login;
    //endregion View

    public Activity _activity;
    public Context _context;
    public VolleyRequestMethod _loginAPI;
    JSONObject jsonObj=null;
    AlertDialog.Builder mBuilder;
    View mView;
    TextView dialog_loading_text;
    AlertDialog dialog;

    Boolean login_success=false;
    Boolean is_login=true;

    public LoginTask(Context context,Activity activity, VolleyRequestMethod loginAPI,Boolean islogin)
    {
        _context=context;
        _activity=activity;
        _loginAPI=loginAPI;
        login_success=false;
        is_login=islogin;
        FindViewByID();
    }
    public void FindViewByID()
    {
        login_btn_login=(Button)_activity.findViewById(R.id.login_btn_login);
        login_edt_password=(EditText)_activity.findViewById(R.id.login_edt_password);
        login_edt_username=(EditText)_activity.findViewById(R.id.login_edt_username);
    }
    public void saveToken(String access_token, String token_type)
    {
        SharedPreferences spre=_activity.getSharedPreferences("login_token",_activity.MODE_PRIVATE);
        SharedPreferences.Editor edt=spre.edit();
        edt.putString("access_token",access_token);
        edt.putString("token_type",token_type);
        edt.commit();
    }
    //region asyncTask
    @Override
    protected void onPreExecute() {

        mBuilder= new AlertDialog.Builder(_activity, R.style.NewDialog);
        mView=_activity.getLayoutInflater().inflate(R.layout.dialog_loading,null);
        dialog_loading_text=(TextView)mView.findViewById(R.id.dialog_loading_text);
        dialog_loading_text.setText("Loading...");
        mBuilder.setView(mView);
        dialog=mBuilder.create();
        dialog.show();
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            while (_loginAPI.isComplete == false) {
                SystemClock.sleep(10);
                //publishProgress(1);
            }
            _loginAPI.isComplete = false;
        }catch (Exception ex){}
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        try {
            jsonObj = new JSONObject(_loginAPI.result[0]);
            Token token= JSONtoClass.JSONtoToken(jsonObj);
            saveToken(token.access_token,token.token_type);
            dialog.cancel();
            if(_loginAPI.isOauth==true) {
                //ClientConfig.CURRENT_USER=login_edt_username.getText().toString();
                if(is_login==true) {
                    ClientConfig.CURRENT_USER = "nv";
                    ClientConfig.ACCESS_TOKEN=token.access_token;
                    ClientConfig.TOKENT_TYPE=token.token_type;
                    Intent intent = new Intent(_activity, InOutActivity.class);
                    _activity.startActivity(intent);
                }
                else
                {
                    ClientConfig.CURRENT_USER = "catruong";
                    ClientConfig.ACCESS_TOKEN=token.access_token;
                    ClientConfig.TOKENT_TYPE=token.token_type;
                    Intent intent = new Intent(_activity, InOutStatisticActivity.class);
                    _activity.startActivity(intent);
                    dialog.cancel();
                }
            }
        } catch (JSONException e) {
            dialog.cancel();
            e.printStackTrace();
        }

    }
    //endregion asyntask
}
