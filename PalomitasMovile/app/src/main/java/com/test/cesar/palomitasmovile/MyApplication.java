package com.test.cesar.palomitasmovile;

import android.app.Application;

import com.test.cesar.palomitasmovile.Utils.NetworkManager;

/**
 * Created by Cesar on 14/09/2016.
 */
public class MyApplication extends Application
{

    @Override
    public void onCreate()
    {
        super.onCreate();
        NetworkManager.getInstance(this);
    }



}