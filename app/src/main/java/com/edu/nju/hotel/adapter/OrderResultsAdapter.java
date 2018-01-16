package com.edu.nju.hotel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.entity.OrderResultsEntity;
import com.edu.nju.hotel.ui.OrderResultsActivity;
import com.edu.nju.hotel.ui.RoomResultsDetailActivity;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by luoxuechun on 2017/6/5.
 */

/**
 * 系统反馈订单结果列表的adapter
 */

public class OrderResultsAdapter extends BaseAdapter {

    private ArrayList<OrderResultsEntity> mData;
    private Context mContext;
   private  String negoString;
    public OrderResultsAdapter(ArrayList<OrderResultsEntity> mData,Context mContext){
        this.mData=mData;
        this.mContext=mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
          if(convertView == null){
              convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_order_results_item,parent,false);
          }


          final ImageView imageView1=(ImageView)convertView.findViewById(R.id.hotelPicture);


        final TextView originPrice=(TextView)convertView.findViewById(R.id.originPrice);
        originPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        originPrice.setText(mData.get(position).getOriginPrice()+"");
        final TextView currentPrice=(TextView)convertView.findViewById(R.id.currentPrice);
        currentPrice.setText(mData.get(position).getCurrentPrice()+"");
        final TextView hotelName=(TextView) convertView.findViewById(R.id.hotelNameText);
        hotelName.setText(mData.get(position).getName());
        final TextView hotelLocation=(TextView) convertView.findViewById(R.id.hotelLocationText);
        hotelLocation.setText(mData.get(position).getLocation());
        Button orderNowButton=(Button)convertView.findViewById(R.id.orderNowButton);
        Button decideButton=(Button)convertView.findViewById(R.id.decideButton);


        if(mData.get(position).getName().equals("Jiaxin Hotel")){
            imageView1.setBackgroundResource(R.mipmap.timg1);
        }else if(mData.get(position).getName().equals("Jiayu Hotel")){
            imageView1.setBackgroundResource(R.mipmap.timg2);
        }else if(mData.get(position).getName().equals("Inn De Hotel")){
            imageView1.setBackgroundResource(R.mipmap.timg3);
        }else if(mData.get(position).getName().equals("Wong Kim Dinh Hotel")){
            imageView1.setBackgroundResource(R.mipmap.timg4);
        }
         RatingBar ratingBar=(RatingBar)convertView.findViewById(R.id.ratingBar);
         ratingBar.setRating((float) mData.get(position).getPoints());
         negoString=mData.get(position).getNegotiation();
        decideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //check the right of user

                if(true){
                    Intent intent=new Intent(mContext,RoomResultsDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putString("hotelName",hotelName.getText().toString());
                    b.putString("originPrice",originPrice.getText().toString());
                    b.putString("currentPrice",currentPrice.getText().toString());
                    b.putString("hotelLocation",hotelLocation.getText().toString());
                    b.putString("nego",negoString);
                    intent.putExtras(b);
                    mContext.startActivity(intent);
                }
            }
        });


//        img_icon.setBackgroundResource(mData.get(position).getaIcon());
//        txt_aName.setText(mData.get(position).getaName());
//        txt_aSpeak.setText(mData.get(position).getaSpeak());
        return convertView;
   }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ListItemView listItemView;
//
//        // 初始化item view
//        if (convertView == null) {
//            // 通过LayoutInflater将xml中定义的视图实例化到一个View中
//            convertView = LayoutInflater.from(OrderResultsActivity.this).inflate(
//                    R.layout.activity_order_results_item, null);
//
//            // 实例化一个封装类ListItemView，并实例化它的两个域
//            listItemView = new ListItemView();
//            listItemView.imageView = (ImageView) convertView
//                    .findViewById(R.id.image);
//            listItemView.textView = (TextView) convertView
//                    .findViewById(R.id.title);
//
//            // 将ListItemView对象传递给convertView
//            convertView.setTag(listItemView);
//        } else {
//            // 从converView中获取ListItemView对象
//            listItemView = (ListItemView) convertView.getTag();
//        }
//
//        // 获取到mList中指定索引位置的资源
//        Drawable img = mList.get(position).getImage();
//        String title = mList.get(position).getTitle();
//
//        // 将资源传递给ListItemView的两个域对象
//        listItemView.imageView.setImageDrawable(img);
//        listItemView.textView.setText(title);
//
//        // 返回convertView对象
//        return convertView;
//    }


}
