package com.edu.nju.hotel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.utils.LinkToServer;

/**
 * Created by zcy on 2017/6/22.
 *
 */

public class RegisterActivity extends BaseAppCompatActivity{

    private EditText et_name,et_password,et_confirm,et_phone;
    private RadioButton btn1,btn2;
    private Button tenantBtn,landlordBtn;
    private String name_val,password_val,confirm_val,phone_val,gender,type;
    private final static String url = "/register";

    //手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    //手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

    //手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 100;

    //记录手指按下时的横坐标。
    private float xDown;

    //记录手指按下时的纵坐标。
    private float yDown;

    //记录手指移动时的横坐标。
    private float xMove;

    //记录手指移动时的纵坐标。
    private float yMove;

    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                yMove= event.getRawY();
                //滑动的距离
                int distanceX = (int) (xMove - xDown);
                int distanceY= (int) (yMove - yDown);
                //获取顺时速度
                int ySpeed = getScrollVelocity();
                //关闭Activity需满足以下条件：
                //1.x轴滑动的距离>XDISTANCE_MIN
                //2.y轴滑动的距离在YDISTANCE_MIN范围内
                //3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                if(distanceX > XDISTANCE_MIN &&(distanceY<YDISTANCE_MIN&&distanceY>-YDISTANCE_MIN)&& ySpeed < YSPEED_MIN) {
                    finish();
                }
                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }

    protected void onCreate(Bundle savedInstanceState) {
        //为了在主线程中访问网络，所以加了这两行
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

//        setContentView(R.layout.activity_register);
        setToolBarTitle("Register");

        et_name = (EditText) findViewById(R.id.nameValue);
        et_password = (EditText) findViewById(R.id.passwordValue);
        et_confirm = (EditText) findViewById(R.id.password2Value);
        et_phone = (EditText) findViewById(R.id.phoneValue);
        btn1 = (RadioButton) findViewById(R.id.maleBtn);
        btn2 = (RadioButton) findViewById(R.id.femaleBtn);
        tenantBtn = (Button) findViewById(R.id.tenantRegister);
        landlordBtn = (Button) findViewById(R.id.landlordRegister);

        tenantBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                name_val = et_name.getText().toString();
                password_val = et_password.getText().toString();
                confirm_val = et_confirm.getText().toString();
                phone_val = et_phone.getText().toString();
                if(btn1.isChecked()){
                    gender = "male";
                }else if(btn2.isChecked()){
                    gender = "female";
                }else{
                    gender = "";
                }
                type = "tenant";

                if(name_val.equals("")||password_val.equals("")){
                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Fail")
                            .setMessage("Information is missing！")
                            .setPositiveButton("Ok", null)
                            .show();
                }
                if(!password_val.equals(confirm_val)){
                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Fail")
                            .setMessage("Please check your password！")
                            .setPositiveButton("Ok", null)
                            .show();
                }

                String param = "{\"id\":\"20\""+
                                ",\"name\":"+"\""+name_val+"\""+
                                ",\"password\":"+"\""+password_val+"\""+
                                ",\"gender\":"+"\""+gender+"\""+
                                ",\"phonenum\":"+"\""+phone_val+"\""+
                                ",\"preference\":\"\""+
                                ",\"education\":\"\""+
                                ",\"vocation\":\"\""+
                                ",\"economic\":\"\"}";
                String result = LinkToServer.sendPost(url+"/tenant",param);
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Success")
                        .setMessage(result)
                        .setPositiveButton("Ok", null)
                        .show();
                //自动跳转到登录界面
                Thread thread = new Thread(new returnToLogin());
                thread.start();
            }
        });

        landlordBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                name_val = et_name.getText().toString();
                password_val = et_password.getText().toString();
                confirm_val = et_confirm.getText().toString();
                phone_val = et_phone.getText().toString();
                if(btn1.isChecked()){
                    gender = "male";
                }else if(btn2.isChecked()){
                    gender = "female";
                }else{
                    gender = "";
                }
                type = "landlord";

                if(name_val.equals("")||password_val.equals("")){
                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Fail")
                            .setMessage("Information is missing！")
                            .setPositiveButton("Ok", null)
                            .show();
                }
                if(!password_val.equals(confirm_val)){
                    new AlertDialog.Builder(RegisterActivity.this).setTitle("Fail")
                            .setMessage("Please check your password！")
                            .setPositiveButton("Ok", null)
                            .show();
                }

                String param = "{\"id\":\"20\""+
                        ",\"name\":"+"\""+name_val+"\""+
                        ",\"password\":"+"\""+password_val+"\""+
                        ",\"landlordtype\":\"\""+
                        ",\"feature\":\"\""+
                        ",\"startprice\":\"0\""+
                        ",\"city\":\"南京市\""+
                        ",\"area\":\"南大\""+
                        ",\"detailaddress\":\"\""+
                        ",\"comment\":\"\""+
                        ",\"longitude\":\"0\""+
                        ",\"latitude\":\"0\""+
                        ",\"concat\":"+"\""+phone_val+"\""+
                        ",\"introduction\":\"\""+
                        ",\"characteristic\":\"\"}";
                String result = LinkToServer.sendPost(url+"/landlord",param);
                new AlertDialog.Builder(RegisterActivity.this).setTitle("Success")
                        .setMessage(result)
                        .setPositiveButton("Ok", null)
                        .show();
                //自动跳转到登录界面
                Thread thread = new Thread(new returnToLogin());
                thread.start();
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    private class returnToLogin implements Runnable{

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
