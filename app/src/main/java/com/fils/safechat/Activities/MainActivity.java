package com.fils.safechat.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fils.safechat.R;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("running onCreate");
    }
}
