package com.edu.nju.hotel.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.ui.BaseAppCompatActivity;

import java.util.ArrayList;

/**
 * Created by luoxuechun on 2017/6/5.
 */
public class RoomResultsDetailActivity extends BaseAppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<String> mDatas;
    private HomeAdapter mAdapter;
    private TextView originPrice2;
    private TextView location;
    private TextView currentPrice2;
    private ImageView imageView1;
   private TextView scoreText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

       // setContentView(R.layout.activity_room_results_detail);

        initData();
        Intent intent=getIntent();
        setToolBarTitle(intent.getStringExtra("hotelName"));
        getSubTitle().setText("");
        imageView1=(ImageView)findViewById(R.id.imageView1);
        originPrice2=(TextView)findViewById(R.id.originText2);
        currentPrice2=(TextView)findViewById(R.id.currentPrice2);
        originPrice2.setText(intent.getStringExtra("originPrice"));

        currentPrice2.setText(intent.getStringExtra("currentPrice"));
        mRecyclerView=(RecyclerView)findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManagerHorizontal = new LinearLayoutManager(this);
        linearLayoutManagerHorizontal.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManagerHorizontal);
        mRecyclerView.addItemDecoration(new RecyclerDivider(getResources().getColor(R.color.colorDivider), 50, 2));
        mRecyclerView.setAdapter(mAdapter= new HomeAdapter());

        originPrice2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
         scoreText=(TextView)findViewById(R.id.score);
        location=(TextView)findViewById(R.id.locationText1);
//        hotelName=(TextView)findViewById(R.id);
        location.setText(intent.getStringExtra("hotelLocation"));
   //     hotelName.setText(intent.getStringExtra("hotelName"));

        if(intent.getStringExtra("hotelName").toString().equals("Jiaxin Hotel")){
            imageView1.setImageResource(R.mipmap.timg1);
            scoreText.setText("4.8");
        }else if(intent.getStringExtra("hotelName").toString().equals("Jiayu Hotel")){
            imageView1.setImageResource(R.mipmap.timg2);
            scoreText.setText("4.7");
        }else if(intent.getStringExtra("hotelName").toString().equals("Inn De Hotel")){
            imageView1.setImageResource(R.mipmap.timg3);
            scoreText.setText("4.6");
        }else if(intent.getStringExtra("hotelName").toString().equals("Wong Kim Dinh Hotel")){
            imageView1.setImageResource(R.mipmap.timg4);
            scoreText.setText("4.4");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_room_results_detail;
    }

    protected void initData()
    {
        Intent intent=getIntent();
       String negoString;
        mDatas=new ArrayList<String>();
        negoString=intent.getStringExtra("nego");
        String[] temp=negoString.split(";") ;
        for(int i=0;i<temp.length;i++){
            mDatas.add(temp[i]);
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    RoomResultsDetailActivity.this).inflate(R.layout.recycle_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position));
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public MyViewHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.textView3);
            }
        }
    }
}
