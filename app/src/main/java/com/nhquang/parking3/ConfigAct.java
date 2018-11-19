package com.nhquang.parking3;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class ConfigAct extends AppCompatActivity {

    //region View
    Button Config_btn_save;
    EditText Config_edt_token_type;
    EditText Config_edt_access_token;
    EditText Config_edt_baseapiurl;
    CheckBox Config_chk_isipss;
    CheckBox Config_chk_isregistercard;
    //endregion View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        FindIDByView();
        SetEventToView();
        GetConfig();
    }
    public void FindIDByView()
    {
        Config_btn_save=(Button)findViewById(R.id.Config_btn_save);
        Config_edt_baseapiurl=(EditText) findViewById(R.id.Config_edt_baseapiurl);
        Config_chk_isipss=(CheckBox)findViewById(R.id.Config_chk_isipss);
        Config_chk_isregistercard=(CheckBox)findViewById(R.id.Config_chk_isregistercard);
    }
    public void SetEventToView()
    {
        Config_btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean is_ipss=Config_chk_isipss.isChecked();
                Boolean is_register_card=Config_chk_isregistercard.isChecked();
                String base_api_url=Config_edt_baseapiurl.getText().toString();
                SaveConfig(base_api_url,is_ipss,is_register_card);
            }
        });
    }
    public void SaveConfig(String apiurl, Boolean isIPSS, Boolean isregistercard)
    {
        SharedPreferences spre=this.getSharedPreferences("ClientConfig",this.MODE_PRIVATE);
        SharedPreferences.Editor edt=spre.edit();
        edt.putBoolean("is_ipss",isIPSS);
        edt.putString("base_api_url",apiurl);
        edt.putBoolean("is_register_card",isregistercard);
        edt.commit();
    }
    public void GetConfig()
    {
        SharedPreferences spre=this.getSharedPreferences("ClientConfig",this.MODE_PRIVATE);
        Config_chk_isipss.setChecked(spre.getBoolean("is_ipss",false));
        Config_chk_isregistercard.setChecked(spre.getBoolean("is_register_card",false));
        Config_edt_baseapiurl.setText(spre.getString("base_api_url",""));
    }
}
