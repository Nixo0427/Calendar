package com.example.administrator.rili;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * 项目名：   Rili
 * 包名 ：    com.example.administrator.rili
 * 文件名：   rili
 * 创建者:    Nixo
 * 创建时间：  2018/4/16/016-15:09
 * 描述：
 */

public class rili extends LinearLayout {

    private ImageView shang;
    private ImageView xia;
    private TextView  yue;
    private GridView  riliGrid;
    private Calendar calendar = Calendar.getInstance();


    public rili(Context context) {
        super(context);
        initCortel(context);
    }

    public rili(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initCortel(context);
    }

    public rili(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initCortel(context);
    }

    private void initCortel(Context context){

        initView(context);
        ControlView();
        RanderView();
    }

    private void ControlView() {
        shang.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH , -1);
                RanderView();
            }
        });
        xia.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH , 1);
                RanderView();
            }
        });

    }

    private void RanderView() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM yyyy");
        yue.setText(sdf.format(calendar.getTime()));

        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(Calendar.DAY_OF_MONTH,1);
        int prevDays = calendar2.get(Calendar.DAY_OF_WEEK)-1;
        calendar2.add(Calendar.DAY_OF_MONTH , - prevDays);

        int maxCellCount = 6*7;
        while (cells.size() < maxCellCount){
            cells.add(calendar2.getTime());
            calendar2.add(Calendar.DAY_OF_MONTH,1);
        }
        riliGrid.setAdapter(new mAdatper(getContext(),cells));
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.rili,this);
        shang = findViewById(R.id.qian);
        xia = findViewById(R.id.xia);
        yue = findViewById(R.id.dangqian);
        riliGrid = findViewById(R.id.rili_grid);
    }



    private  class mAdatper extends ArrayAdapter<Date> {
        LayoutInflater inflater;

        public mAdatper(@NonNull Context context, ArrayList<Date> cells) {
            super(context,R.layout.rili);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            Date day = getItem(position);
            if(convertView == null){
                convertView = inflater.inflate(R.layout.rili_days,parent,false);
            }
            int textDay = day.getDay();
            ((TextView)convertView).setText(String.valueOf(textDay));
            return convertView;
        }
    }


}
