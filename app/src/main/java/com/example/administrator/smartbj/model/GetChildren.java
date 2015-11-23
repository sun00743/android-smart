package com.example.administrator.smartbj.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.administrator.smartbj.http.MyStringRequest;
import com.example.administrator.smartbj.utils.UrlUtil;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2015/11/4.
 */
public class GetChildren {

    private Gson gson;
    private RequestQueue requestQueue;
    private MyStringRequest stringRequest;

    public void get(Context context , final DataListener listener) {
        gson = new Gson();

        requestQueue = Volley.newRequestQueue(context);
        stringRequest = new MyStringRequest(UrlUtil.CONTACT_ALL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Smart smart = gson.fromJson(response, Smart.class);
                        List<Data> datas = smart.getData();
                        List<Children> childrens = datas.get(0).getChildren();
                        listener.onSuccess(childrens.get(0));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }
}
