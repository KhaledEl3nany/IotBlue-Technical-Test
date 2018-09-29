package com.assem.tests.iotblue.App;

import android.app.Application;
import android.content.Context;

import com.assem.tests.iotblue.Utils.ConnectivityReceiver;

public class MyApplication extends Application {

    // Singleton class
    private static MyApplication mInstance;
    private static Context mContext;

    private MyApplication(Context context) {
        // Specify the application context
        mContext = context;
    }

    public static synchronized MyApplication getInstance(Context context) {
        // If Instance is null then initialize new Instance
        if (mInstance == null) {
            mInstance = new MyApplication(context);
        }
        // Return MySingleton new Instance
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
//
//    public static synchronized MyApplication getInstance() {
//        return mInstance;
//    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

}