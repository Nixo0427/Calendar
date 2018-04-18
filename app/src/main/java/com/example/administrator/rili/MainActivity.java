package com.example.administrator.rili;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements rili2.setLongOnCilckListener
        ,rili2.setShortOnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rili2 rili2 = findViewById(R.id.rili);
        rili2.onCilckListener = this;
        rili2.onSClickListener = this;
    }

    @Override
    public void onLongListener(Date date) {
        Date date1 = new Date();
        int a = Integer.parseInt(String.valueOf(date.getMonth()))+1;
        Toast.makeText(this, ""+a+"月"+date.getDate()+"日", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShortListener(Date xda) {
        Toast.makeText(this, ""+xda.toString(), Toast.LENGTH_SHORT).show();
    }
}
