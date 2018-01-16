package com.edu.nju.hotel.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.entity.OrderEntity;
import com.edu.nju.hotel.utils.LinkToServer;
import com.google.gson.Gson;
import com.edu.nju.hotel.ui.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zcy on 2017/6/7.
 *
 */

public class TenantOrderTempActivity extends BaseAppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mItemTitles;
    private EditText checkinTime, checkoutTime;
    private SeekBar price;
    private Spinner roomNum,roomType,city,district;
    private CheckBox choose1,choose2,choose3,choose4;
    private List<CheckBox> extra;
    private Button confirm;
    private ArrayAdapter<String> adapter;
    private final static String url = "/tenant";


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setToolBarTitle("Order Room");

        checkinTime = (EditText)findViewById(R.id.checkin_time);
        checkoutTime = (EditText)findViewById(R.id.checkout_time);
//        location = (EditText)findViewById(R.id.location);
        city = (Spinner)findViewById(R.id.cityValue);
        district = (Spinner)findViewById(R.id.districtValue);
        price = (SeekBar)findViewById(R.id.price_seekBar);
        roomNum = (Spinner)findViewById(R.id.number_of_room);
        roomType = (Spinner)findViewById(R.id.type_of_room);
        choose1 = (CheckBox)findViewById(R.id.checkBox);
        choose2 = (CheckBox)findViewById(R.id.checkBox2);
        choose3 = (CheckBox)findViewById(R.id.checkBox3);
        choose4 = (CheckBox)findViewById(R.id.checkBox4);
        confirm = (Button)findViewById(R.id.confirm_order);
        extra = new ArrayList<>();
        extra.add(choose1);
        extra.add(choose2);
        extra.add(choose3);
        extra.add(choose4);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item);
        initListView();

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, R.string.drawer_open,
                R.string.drawer_close)
        {
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view)
            {

                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView)
            {

                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };
        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        // enable ActionBar app icon to behave as action to toggle nav drawer
//        getActionBar().setDisplayHomeAsUpEnabled(true);

        checkinTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(TenantOrderTempActivity.this,DateChooseActivity.class);
                startActivityForResult(it,0);
            }
        });

        checkoutTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent it = new Intent();
                it.setClass(TenantOrderTempActivity.this,DateChooseActivity.class);
                startActivityForResult(it,1);
            }
        });

        city.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        adapter.clear();
                        String[] items = getResources().getStringArray(R.array.districts_Nanjing);
                        for(int j=0;j<items.length;j++){
                            adapter.add(items[j]);
                        }
                        district.setAdapter(adapter);
                        break;
                    case 1:
                        adapter.clear();
                        items = getResources().getStringArray(R.array.districts_Shanghai);
                        for(int j=0;j<items.length;j++){
                            adapter.add(items[j]);
                        }
                        district.setAdapter(adapter);
                        break;
                    case 2:
                        adapter.clear();
                        items = getResources().getStringArray(R.array.districts_Suzhou);
                        for(int j=0;j<items.length;j++){
                            adapter.add(items[j]);
                        }
                        district.setAdapter(adapter);
                        break;
                    default:break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inTime = checkinTime.getText().toString();
                String outTime = checkoutTime.getText().toString();
//                String loca = location.getText().toString();
                String pri = String.valueOf(price.getProgress());
                String type = roomType.getSelectedItem()+"";
                StringBuilder extra_requirements = new StringBuilder();
                for(CheckBox cb:extra){
                    if(cb.isChecked()){
                        extra_requirements.append(cb.getText().toString()+" ");
                    }
                }
                OrderEntity orderEntity = new OrderEntity();
                orderEntity.setRoomNum((roomNum.getSelectedItemId()+1)+"");
                orderEntity.setRoomType((String)roomType.getSelectedItem());
                orderEntity.setStartTime(inTime);
                orderEntity.setEndTime(outTime);
                orderEntity.setAddress((String)city.getSelectedItem()+(String)district.getSelectedItem());
                Gson gson = new Gson();
                String param = gson.toJson(orderEntity);
                LinkToServer.sendPost(url,param);
                Bundle data = new Bundle();
                data.putString("checkinTime",inTime);
                data.putString("checkoutTime",outTime);
//                data.putString("location",loca);
                data.putString("price",pri);
                data.putString("type",type);
                data.putString("extra_requirements",extra_requirements.toString());

                Intent it = new Intent();
                it.putExtras(data);

                it.setClass(TenantOrderTempActivity.this,OrderResultsActivity.class);
                startActivity(it);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle action buttons
        Intent intent = new Intent();
        intent.setClass(TenantOrderTempActivity.this,RegisterActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String year=b.getString("year");
                String month = b.getString("month");
                String day = b.getString("day");
                if(requestCode==0){
                    checkinTime.setText(year+"/"+month+"/"+day);
                }
                else{
                    checkoutTime.setText(year+"/"+month+"/"+day);
                }
                break;
            default:
                break;
        }
    }

    private void initListView()
    {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        mItemTitles = getResources().getStringArray(R.array.menu_array_tenant);


        // Set the adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, mItemTitles));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // Highlight the selected item, update the title, and close the
                // drawer
                mDrawerList.setItemChecked(position, true);
                setTitle(mItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
                Intent intent;
                Bundle data;
                switch (mItemTitles[position]){
                    case "Profile":
                        intent = new Intent();
                        data = new Bundle();
                        data.putString("name","hqq");
                        data.putString("gender","Male");
                        data.putString("type","tenant");
                        data.putString("phone","18972367843");
                        data.putString("vocation","professor");
                        data.putString("education","bachelor");
                        intent.putExtras(data);
                        intent.setClass(TenantOrderTempActivity.this,UserProfileActivity.class);
                        startActivity(intent);
                        break;
                    case "Order Room":
                        intent = new Intent();
                        intent.setClass(TenantOrderTempActivity.this,TenantOrderTempActivity.class);
                        startActivity(intent);
                        break;
                    case "Room Info":
                        intent = new Intent();
                        intent.setClass(TenantOrderTempActivity.this,RoomInfoActivity.class);
                        startActivity(intent);
                        break;
                    case "Register Room":
                        intent = new Intent();
//                        data = new Bundle();
//                        data.putString("type",type);
//                        intent.putExtras(data);
                        intent.setClass(TenantOrderTempActivity.this,RegisterRoomActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_tenant_order_temp;
    }
}
