package com.example.administrator.rili;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import static com.example.administrator.rili.R.mipmap.ic_launcher;


/**
 * 项目名：   Rili
 * 包名 ：    com.example.administrator.rili
 * 文件名：   myTextView
 * 创建者:    Nixo
 * 创建时间：  2018/4/17/017-18:24
 * 描述：
 */

public class myTextView extends android.support.v7.widget.AppCompatTextView{

    public void setToday(boolean today) {
        isToday = today;
    }
    public void setAllToday(boolean AllToday) {
        isAllToday = AllToday;
    }

    private boolean isAllToday = false;

    private boolean isToday = false;
    private Paint paint = new Paint();
    private Paint paint2 = new Paint();

    public myTextView(Context context) {
        super(context);
    }

    public myTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }


    public myTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setARGB(80,0, 136, 119);

        paint2.setStyle(Paint.Style.STROKE);
        paint2.setColor(Color.RED);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday){
            canvas.translate(getWidth()/2,getHeight()/2);
            canvas.drawCircle(0,0,getWidth()/2,paint);

        }
        if (isAllToday){
            canvas.translate(getWidth()/2,getHeight()/2);
            paint2.setStrokeWidth(8);
            canvas.drawCircle(0,0,getWidth()/2,paint2);

        }

    }
}
