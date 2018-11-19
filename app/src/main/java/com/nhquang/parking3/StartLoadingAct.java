package com.nhquang.parking3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import Models.Base.ClientConfig;

public class StartLoadingAct extends AppCompatActivity {
    String imei1="356803086740419";
    String imei2="356803086740401";
    boolean lincense=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start_loading);

        //region check imei lincense

        /*TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String s=telephonyManager.getDeviceId();
		if(imei1.equals(telephonyManager.getDeviceId()))
			lincense=true;
		else if(imei2.equals(telephonyManager.getDeviceId()))
			lincense=true;
		if(lincense==false)
			this.finish();
*/
        //endregion check imei lincense
        loadConfig();
        Intent intent =new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
    public void loadConfig()
    {
        SharedPreferences spre=this.getSharedPreferences("ClientConfig",this.MODE_PRIVATE);
        String base_api_url_pref=spre.getString("base_api_url","");
        if(base_api_url_pref.equals(""))
            return;
        else {
            ClientConfig.IS_IPSS = spre.getBoolean("is_ipss", false);
            ClientConfig.IS_REGISTER_CARD = spre.getBoolean("is_register_card", false);
            ClientConfig.BASE_API_URL = spre.getString("base_api_url", "");
            ClientConfig.ACCESS_TOKEN = spre.getString("access_token", "");
            ClientConfig.TOKENT_TYPE = spre.getString("token_type", "");
        }
    }
}
