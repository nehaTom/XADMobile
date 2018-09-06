package com.example.codemaven3015.xadmobile.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;

public class VolleyJSONRequest {

    private Context context;
    private String url;
    private RequestQueue requestQueue;
    HashMap<String, String> params;

    public VolleyJSONRequest(Context context,String url,HashMap<String, String> params){
        this.params = params;
        this.context=context;
        this.url=url;
    }
    public void executeRequest(final VolleyJSONRequest.VolleyJSONRequestInterface callback){

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST,url, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFailure(error);

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public interface VolleyJSONRequestInterface{
        void onSuccess(JSONObject obj);
        void onFailure(VolleyError error);
    }
}
