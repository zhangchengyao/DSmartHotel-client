package com.edu.nju.hotel.ui;


import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.entity.TenantEntity;
import com.edu.nju.hotel.utils.LinkToServer;
import com.google.gson.Gson;
import com.edu.nju.hotel.ui.BaseAppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends BaseAppCompatActivity {
    /*
     * register：
     * tenant login：
     * landlord login：
     * @author LexiLuo
     *
     */
    private EditText et_name, et_pass;
    private TextView registerLink;
    private Button tenantLoginButton, landlordLoginButton;
    private String nameValue, passValue;
    private String type;
    private final static String url = "/login";
//    private TextView mToolbarTitle;
//    private TextView mToolbarSubTitle;
//    private Toolbar mToolbar;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //为了在主线程中访问网络，所以加了这两行
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //remove the title
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setToolBarTitle("Login");
       // setContentView(R.layout.activity_login);


        et_name = (EditText) findViewById(R.id.nameText);
        et_pass = (EditText) findViewById(R.id.passwordText);
        tenantLoginButton = (Button) findViewById(R.id.tenantButton);
        landlordLoginButton = (Button) findViewById(R.id.landlordButton);
        registerLink=(TextView)findViewById(R.id.registerLink);

        registerLink.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        tenantLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameValue = et_name.getText().toString();
                passValue = et_pass.getText().toString();
                type = "tenant";

                //check the right of user
                String param = "{\"name\":"+"\""+nameValue+"\""+",\"password\":"+"\""+passValue+"\""+",\"type\":"+"\""+type+"\"}";
                String result = LinkToServer.sendPost(url,param);
                Gson gson = new Gson();
                TenantEntity tenantEntity = gson.fromJson(result,TenantEntity.class);
                if(result!=null && result.length()!=0) {
                    //登录成功,获得该用户相关数据
                    JSONObject json = null;
                    String economic = null;
                    String education = null;
                    String gender = null;
                    int id = 0;
                    String name = null;
                    String password = null;
                    String phonenum = null;
                    String preference = null;
                    String vocation = null;
                    try {
                        json = new JSONObject(result);
//                        economic = json.getString("economic");
//                        education = json.getString("education");
//                        gender = json.getString("gender");
//                        id = json.getInt("id");
//                        name = json.getString("name");
//                        password = json.getString("password");
//                        phonenum = json.getString("phonenum");
//                        preference = json.getString("preference");
//                        vocation = json.getString("vocation");
                        economic = tenantEntity.getEconomic();
                        education = tenantEntity.getEducation();
                        gender = tenantEntity.getGender();
                        id = tenantEntity.getId();
                        name = tenantEntity.getName();
                        password = tenantEntity.getPassword();
                        phonenum = tenantEntity.getPhonenum();
                        preference = tenantEntity.getPreference();
                        vocation = tenantEntity.getVocation();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Bundle data = new Bundle();
                    data.putString("economic",economic);
                    data.putString("education",education);
                    data.putString("gender",gender);
                    data.putInt("id",id);
                    data.putString("name",name);
                    data.putString("password",password);
                    data.putString("phonenum",phonenum);
                    data.putString("preference",preference);
                    data.putString("vocation",vocation);

//                    data.putString("name",nameValue);
//                    data.putString("gender","Male");
//                    data.putString("type",type);
//                    data.putString("phone","18972367843");
//                    data.putString("vocation","professor");
//                    data.putString("education","bachelor");

                    Intent intent=new Intent();
                    intent.putExtras(data);
                    intent.setClass(LoginActivity.this,UserProfileActivity.class);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(LoginActivity.this).setTitle("Login Fail")
                            .setMessage("Your account is invalid！")
                            .setPositiveButton("Ok", null)
                            .show();
                }
            }
        });
        landlordLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameValue = et_name.getText().toString();
                passValue = et_pass.getText().toString();
                type = "landlord";
                //check the right of user

                String param = "{\"name\":"+"\""+nameValue+"\""+",\"password\":"+"\""+passValue+"\""+",\"type\":"+"\""+type+"\"}";
                String result = LinkToServer.sendPost(url,param);
                if(result!=null && result.length()!=0){
                    Bundle data = new Bundle();
                    data.putString("name",nameValue);
                    data.putString("type",type);
                    data.putString("gender","Female");
                    data.putString("phone","13882730983");
                    data.putString("vocation","professor");
                    data.putString("education","bachelor");
                    Intent intent=new Intent();
                    intent.putExtras(data);
                    intent.setClass(LoginActivity.this,UserProfileActivity.class);
                    startActivity(intent);
                }else{
                    new AlertDialog.Builder(LoginActivity.this).setTitle("Login Fail")
                            .setMessage("Your account is invalid！")
                            .setPositiveButton("Ok", null)
                            .show();
                }
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


}
