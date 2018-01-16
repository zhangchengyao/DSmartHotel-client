package com.edu.nju.hotel.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.adapter.OrderResultsAdapter;
import com.edu.nju.hotel.entity.OrderResultsEntity;
import com.edu.nju.hotel.ui.BaseAppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by luoxuechun on 2017/6/5.
 */
public class OrderResultsActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener{

    private ListView mListView;

    private ArrayList<OrderResultsEntity> mList;

    private  MediaPlayer mp;
    @Override
    public void onCreate(Bundle savedInstanceState){
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_order_results);
        setToolBarTitle("Order Results");

        mp=MediaPlayer.create(OrderResultsActivity.this,R.raw.hotel_reserve_result);
        getSubTitle().setText("Tips");
        getSubTitle().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mp.start();

                //check the right of user

            }
        });
        mListView=(ListView)findViewById(R.id.listView);
        Resources res=this.getResources();
        mList=new ArrayList<OrderResultsEntity>();

        //初始化data
        ArrayList<String> tags=new ArrayList<>();
        String negotiation1;
        negotiation1="第一次协商完价格为RMB250;";
        negotiation1+="第二次协商完价格为RMB230;";
        negotiation1+="最终价格：RMB230";
        String negotiation2;
        negotiation2="第一次协商完价格为RMB240;";
        negotiation2+="第二次协商完价格为RMB235;";
        negotiation2+="最终价格：RMB235";
        String negotiation3;
        negotiation3="第一次协商完价格为RMB175;";
        negotiation3+="第二次协商完价格为RMB160;";
        negotiation3+="最终价格：RMB160";
        String negotiation4;
        negotiation4="第一次协商完价格为RMB190;";
        negotiation4+="第二次协商完价格为RMB180;";
        negotiation4+="第三次协商完价格为RMB162;";
        negotiation4+="最终价格：RMB162";
        String negotiation5;
        negotiation5="第一次协商完价格为RMB260;";
        negotiation5+="第二次协商完价格为RMB240;";
        negotiation5+="最终价格：RMB240";
        OrderResultsEntity orderResultsEntity1=new OrderResultsEntity(255,230,"0.19km away from the destination","Lingnanjiayuan Chain Hotel",3.42,tags,negotiation1);
        OrderResultsEntity orderResultsEntity2=new OrderResultsEntity(250,235,"0.31km away from the destination","Jiaxin Hotel",3.76,tags,negotiation2);
        OrderResultsEntity orderResultsEntity3=new OrderResultsEntity(180,160,"0.22km away from the destination","Jiayu Hotel",4.11,tags,negotiation3);
        OrderResultsEntity orderResultsEntity4=new OrderResultsEntity(198,162,"0.28km away from the destination","Inn De Hotel",4.74,tags,negotiation4);
        OrderResultsEntity orderResultsEntity5=new OrderResultsEntity(260,240,"0.38km away from the destination","Wong Kim Dinh Hotel",3.21,tags,negotiation5);

        mList.add(orderResultsEntity4);
        mList.add(orderResultsEntity3);
        mList.add(orderResultsEntity2);
        mList.add(orderResultsEntity1);
        mList.add(orderResultsEntity5);

        OrderResultsAdapter orderResultsAdapter=new OrderResultsAdapter(mList,OrderResultsActivity.this);
        mListView.setAdapter(orderResultsAdapter);


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_results;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){
       //if(mList.get(position).equals("Button")){
        Toast.makeText(OrderResultsActivity.this,"你点击了第" + position + "项",Toast.LENGTH_SHORT).show();
           Intent intent=new Intent();
           intent.setClass(OrderResultsActivity.this,RoomResultsDetailActivity.class);
           startActivity(intent);

           //intent.putExtra("hotelName",originPrice.getText());
       //}

       // intent.putExtra("hotelName",);

    }

}
