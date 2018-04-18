package com.example.administrator.rili;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.jar.Attributes;

import static android.content.ContentValues.TAG;

/**
 * 项目名：   Rili
 * 包名 ：    com.example.administrator.rili
 * 文件名：   rili2
 * 创建者:    Nixo
 * 创建时间：  2018/4/16/016-20:11
 * 描述：
 */

public class rili2 extends LinearLayout implements View.OnTouchListener{


    ImageView preMouth,nextMouth;
    TextView nowMouth;
    GridView grid;
    Calendar calendar = Calendar.getInstance();
    GestureDetector mGestureDetector;
    String dateFormat;
    public setLongOnCilckListener onCilckListener;
    public setShortOnClickListener onSClickListener;

    public rili2(Context context) {
        super(context);

    }

    public rili2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Main(context,attrs);
    }

    public rili2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Main(context,attrs);
    }


    public void Main(Context context, AttributeSet attributes){
         initView(context);

        TypedArray ta = getContext().obtainStyledAttributes(attributes,R.styleable.rili2);
        String foramt = ta.getString(R.styleable.rili2_dataFormat);
        try {
            dateFormat = foramt;
            if(dateFormat == null){
                dateFormat = "yyyy MM";
            }
        }finally {
            ta.recycle();
        }

        RanderView();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        mGestureDetector.onTouchEvent(ev);
//        grid.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void initView(final Context context) {

        View view = LayoutInflater.from(context).inflate(R.layout.rili,this);
        preMouth = view.findViewById(R.id.qian);
        nextMouth = view.findViewById(R.id.xia);
        nowMouth = view.findViewById(R.id.dangqian);
        grid = view.findViewById(R.id.rili_grid);





        mGestureDetector = new GestureDetector(myGestureListener);
        LinearLayout linearLayout = findViewById(R.id.riliView);
        linearLayout.setOnTouchListener(this);
        linearLayout.setLongClickable(true);




        preMouth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,-1);
                RanderView();
            }
        });

        nextMouth.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.MONTH,+1);
                RanderView();
            }
        });


    }
    GestureDetector.SimpleOnGestureListener myGestureListener = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            float x = e1.getX()-e2.getX();
            float x2 = e2.getX() - e1.getX();

            if(x>50 && Math.abs(velocityX)>0){
                calendar.add(Calendar.MONTH,+1);
                RanderView();
            }
            if(x2>50 && Math.abs(velocityX)>0){
                calendar.add(Calendar.MONTH,-1);
                RanderView();
            }

            return false;
        }
    };





    public void RanderView(){
        String format = dateFormat;
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        nowMouth.setText(dateFormat.format(calendar.getTime()));
        ArrayList<Date> dates = new ArrayList<>();
        Calendar calendar2 = (Calendar) calendar.clone();
        calendar2.set(Calendar.DAY_OF_MONTH,1);
        int prevDays = calendar2.get(Calendar.DAY_OF_WEEK)-1;
        calendar2.add(Calendar.DAY_OF_MONTH , - prevDays);

        int maxCellCount = 6*7;
        while (dates.size() < maxCellCount){
            dates.add(calendar2.getTime());
            calendar2.add(Calendar.DAY_OF_MONTH,1);
        }

        grid.setAdapter(new mAdapter(getContext(),dates));
        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(onCilckListener == null){
                    return false;
                }
                onCilckListener.onLongListener((Date) parent.getItemAtPosition(position));
                return true;
            }
        });
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onSClickListener.onShortListener((Date) parent.getItemAtPosition(position));
            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    private class mAdapter extends ArrayAdapter<Date>{

        LayoutInflater inflater ;
        public mAdapter(@NonNull Context context,ArrayList<Date> dates) {
            super(context,R.layout.rili,dates);
            inflater = LayoutInflater.from(getContext());
        }




        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            Date date = getItem(position);

            if(convertView == null){
                convertView = inflater.inflate(R.layout.rili_days,parent,false);
            }
            if(date.getDate() == 7 && date.getMonth() == 3) {
                ((myTextView) convertView).setText(String.valueOf(date.getDate())+"\n 逝逝生日");
            }else if(date.getDate() == 26 && date.getMonth() == 11){
                ((myTextView) convertView).setText(String.valueOf(date.getDate())+"\n 岩岩生日");
            }else if(date.getDate() == 23 && date.getMonth() == 4){
                ((myTextView) convertView).setText(String.valueOf(date.getDate())+"\n Love");
            }else{
                ((myTextView) convertView).setText(String.valueOf(date.getDate()));
            }
            Date now = new Date();


//            Calendar nowCalendar = (Calendar) calendar.clone();


            boolean isToday = false;
            if(date.getMonth() == now.getMonth()){
                isToday = true;
            }

            if(isToday){
                ((myTextView) convertView).setTextColor(Color.parseColor("#000000"));
                if(date.getDay() == 6|| date.getDay()==0){
                    ((myTextView) convertView).setTextColor(Color.parseColor("#CCCCFF"));
                }
            }else{
                ((myTextView) convertView).setTextColor(Color.parseColor("#999999"));
            }


            if(date.getDate() == now.getDate()&&date.getYear() == now.getYear() && date.getMonth() == now.getMonth()){
                ((myTextView) convertView).setTextColor(Color.RED);
                ((myTextView) convertView).setToday(true);
                ((myTextView) convertView).setBackgroundResource(R.drawable.today);
                Log.i("jsy", "执行了");

            }
            if(date.getDate()== now.getDate() &&
                    !(date.getDate() == now.getDate()&&date.getYear() == now.getYear() && date.getMonth() == now.getMonth())){
                ((myTextView) convertView).setTextColor(Color.parseColor("#000000" ));
                ((myTextView) convertView).setAllToday(true);
            }

            if(date.getDate() == 7 && date.getMonth() == 3){
                ((myTextView) convertView).setTextColor(Color.parseColor("#663366" ));
                ((myTextView) convertView).setBackgroundResource(R.drawable.birthday17);
            }else if(date.getDate() == 26 && date.getMonth() == 11){
                ((myTextView) convertView).setTextColor(Color.parseColor("#663366" ));
                ((myTextView) convertView).setBackgroundResource(R.drawable.birthday17);
            }else if(date.getDate() == 23 && date.getMonth() == 4){
                ((myTextView) convertView).setTextColor(Color.parseColor("#663366" ));
                ((myTextView) convertView).setBackgroundResource(R.drawable.heart);
            }

            return convertView;
        }
    }

    public interface setLongOnCilckListener{
        void onLongListener(Date date);
    }
    public interface setShortOnClickListener{
        void onShortListener(Date xda);
    }
}



