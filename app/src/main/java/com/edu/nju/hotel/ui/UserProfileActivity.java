package com.edu.nju.hotel.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.ui.BaseAppCompatActivity;

import org.w3c.dom.Text;

/**
 * Created by zcy on 2017/6/30.
 *
 */

public class UserProfileActivity extends BaseAppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private String[] mItemTitles;
    private String type;
    private TextView name,gender,phone,vocation,education;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        //为了在主线程中访问网络，所以加了这两行
        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        super.onCreate(savedInstanceState);

        setToolBarTitle("User Profile");
        name = (TextView)findViewById(R.id.nameValue_p);
        gender = (TextView)findViewById(R.id.genderValue_p);
        phone = (TextView)findViewById(R.id.phoneValue_p);
        vocation = (TextView)findViewById(R.id.vocationValue_p);
        education = (TextView)findViewById(R.id.educationValue_p);

        type = getIntent().getStringExtra("type");
        String name = getIntent().getStringExtra("name");
        String gender = getIntent().getStringExtra("gender");
        String phone = getIntent().getStringExtra("phone");
        String vocation = getIntent().getStringExtra("vocation");
        String education = getIntent().getStringExtra("education");
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initListView();

        this.name.setText(name);
        this.gender.setText(gender);
        this.phone.setText(phone);
        this.vocation.setText(vocation);
        this.education.setText(education);
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
        intent.setClass(UserProfileActivity.this,RegisterActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    private void initListView()
    {
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        if(type.equals("tenant")){
            mItemTitles = getResources().getStringArray(R.array.menu_array_tenant);
        }else if(type.equals("landlord")){
            mItemTitles = getResources().getStringArray(R.array.menu_array_landlord);
        }

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
                        data.putString("type",type);
                        intent.putExtras(data);
                        intent.setClass(UserProfileActivity.this,UserProfileActivity.class);
                        startActivity(intent);
                        break;
                    case "Order Room":
                        intent = new Intent();
                        intent.setClass(UserProfileActivity.this,TenantOrderTempActivity.class);
                        startActivity(intent);
                        break;
                    case "Room Info":
                        intent = new Intent();
                        intent.setClass(UserProfileActivity.this,RoomInfoActivity.class);
                        startActivity(intent);
                        break;
                    case "Register Room":
                        intent = new Intent();
//                        data = new Bundle();
//                        data.putString("type",type);
//                        intent.putExtras(data);
                        intent.setClass(UserProfileActivity.this,RegisterRoomActivity.class);
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
        return R.layout.activity_user_profile;
    }
}
