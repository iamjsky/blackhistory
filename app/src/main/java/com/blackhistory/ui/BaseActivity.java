package com.blackhistory.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.blackhistory.SessionManager;


public class BaseActivity extends AppCompatActivity {
    SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionManager(this);
    }



}
