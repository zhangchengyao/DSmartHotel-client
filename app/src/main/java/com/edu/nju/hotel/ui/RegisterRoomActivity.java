package com.edu.nju.hotel.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.utils.LinkToServer;
import com.edu.nju.hotel.ui.BaseAppCompatActivity;

/**
 * Created by zcy on 2017/6/30.
 *
 */

public class RegisterRoomActivity extends BaseAppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mItemTitles;
    private Button register;
    private EditText name,city,area;
    String type;
    private final static String url = "/landlord/register";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        //为了在主线程中访问网络，所以加了这两行
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        setToolBarTitle("Register Room");

        type = getIntent().getStringExtra("type");
        register = (Button) findViewById(R.id.register_room);
        name = (EditText)findViewById(R.id.hotelNameValue);
        city = (EditText)findViewById(R.id.hotelCityValue);
        area = (EditText)findViewById(R.id.hotelAreaValue);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initListView();

        register.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(name.getText().toString().equals("")||city.getText().toString().equals("")||area.getText().toString().equals("")){
                        new AlertDialog.Builder(RegisterRoomActivity.this).setTitle("Register Fail")
                                .setMessage("Please complete the information！")
                                .setPositiveButton("Ok", null)
                                .show();
                    }else{
                        String param = "{\"name\":"+"\""+name+"\""+",\"city\":"+"\""+city+"\""+",\"area\":"+"\""+area+"\"}";
                        LinkToServer.sendPost(url,param);
                        new AlertDialog.Builder(RegisterRoomActivity.this).setTitle("Register Success")
                                .setMessage("Your registration request has been submitted！")
                                .setPositiveButton("Ok", null)
                                .show();
                        name.setText("");
                        city.setText("");
                        area.setText("");
                    }
                }
        });
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
        intent.setClass(RegisterRoomActivity.this,RegisterActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void initListView()
    {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);


        mItemTitles = getResources().getStringArray(R.array.menu_array_landlord);


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
                        data.putString("type","landlord");
                        data.putString("name","Jackson");
                        data.putString("gender","Female");
                        data.putString("phone","13882730983");
                        data.putString("vocation","professor");
                        data.putString("education","bachelor");
                        intent.putExtras(data);
                        intent.setClass(RegisterRoomActivity.this,UserProfileActivity.class);
                        startActivity(intent);
                        break;
                    case "Order Room":
                        intent = new Intent();
                        intent.setClass(RegisterRoomActivity.this,TenantOrderTempActivity.class);
                        startActivity(intent);
                        break;
                    case "Room Info":
                        intent = new Intent();
                        intent.setClass(RegisterRoomActivity.this,RoomInfoActivity.class);
                        startActivity(intent);
                        break;
                    case "Register Room":
                        intent = new Intent();
                        intent.setClass(RegisterRoomActivity.this,RegisterRoomActivity.class);
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
        return R.layout.activity_register_room;
    }
}
