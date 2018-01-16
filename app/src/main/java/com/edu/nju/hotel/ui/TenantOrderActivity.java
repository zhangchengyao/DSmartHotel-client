package com.edu.nju.hotel.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;

import com.edu.nju.hotel.R;

/**
 * Created by luoxuechun on 2017/6/3.
 */



public class TenantOrderActivity extends AppCompatActivity {

    private ListView orderList;
    private Button startOrder;
    private Button checkDateButton;
    private Button localButton;
    private Button priceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tenant_order);

        startOrder=(Button)findViewById(R.id.startOrderButton);
        checkDateButton=(Button)findViewById(R.id.checkDateButton);
        localButton=(Button)findViewById(R.id.localButton);
        priceButton=(Button)findViewById(R.id.priceButton);

        startOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //check the right of user

                if(true){
                    Intent intent=new Intent();
                    intent.setClass(TenantOrderActivity.this,OrderResultsActivity.class);
                    startActivity(intent);
                }
            }
        });





    }
}
