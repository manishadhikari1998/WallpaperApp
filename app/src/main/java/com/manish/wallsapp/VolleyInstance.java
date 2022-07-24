package com.manish.wallsapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyInstance {
    private RequestQueue requestQueue;
    private static  VolleyInstance mInstance;

    private VolleyInstance(Context context){
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }
    public  static  synchronized VolleyInstance getmInstance(Context context){
        if(mInstance == null){
            mInstance = new VolleyInstance(context);

        }
        return mInstance;
    }
    public  RequestQueue getRequestQueue(){
        return requestQueue;
    }
}
