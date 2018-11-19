package com.nhquang.parking3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button mainact_btn_InOutAct;
    Button mainact_btn_StatisticInOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainact_btn_InOutAct =(Button)findViewById(R.id.mainact_btn_InOutAct);
        mainact_btn_InOutAct.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getBaseContext(),InOutActivity.class);
                startActivity(intent);
            }
        });

        mainact_btn_StatisticInOut =(Button)findViewById(R.id.mainact_btn_StatisticInOut);
        mainact_btn_StatisticInOut.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getBaseContext(),InOutStatisticActivity.class);
                startActivity(intent);
            }
        });

    }
}
