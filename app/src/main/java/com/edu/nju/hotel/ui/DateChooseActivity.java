package com.edu.nju.hotel.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import com.edu.nju.hotel.R;
import com.edu.nju.hotel.ui.BaseAppCompatActivity;

import java.util.Calendar;

/**
 * Created by zcy on 2017/7/5.
 *
 */

public class DateChooseActivity extends BaseAppCompatActivity {
    private DatePicker mDatePicker;
    private int year,month,day;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_choose);
        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //初始化DatePicker组件，初始化时指定监听器
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
             public void onDateChanged(DatePicker arg0, int year, int month, int day) {
                DateChooseActivity.this.year = year;
                DateChooseActivity.this.month = month;
                DateChooseActivity.this.day = day;
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("year",year+"");
                bundle.putString("month",(month+1)+"");
                bundle.putString("day",day+"");
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                finish();
            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_date_choose;
    }
}
