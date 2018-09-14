package com.example.codemaven3015.xadmobile.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyJSONRequest {

    private Context context;
    private String url;
    private RequestQueue requestQueue;
    HashMap<String, String> params;

    public VolleyJSONRequest(Context context, String url, HashMap<String, String> params) {
        this.params = params;
        this.context = context;
        this.url = url;
        requestQueue =  Volley.newRequestQueue(context);

    }

    public void executeStringRequest(final VolleyJSONRequest.VolleyJSONRequestInterface callback){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
           //     Log.e("Check",response);
                try {
                    JSONObject responseObject = new JSONObject(response);
                    callback.onSuccess(responseObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                callback.onFailure(error);
//                Log.e("Check",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                //Map<String, String> params = new HashMap<String, String>();
                //params.put("tag", "test");
                return params;
            }
        }
        ;
        requestQueue.add(stringRequest);
    }

    public void executeRequest(final VolleyJSONRequest.VolleyJSONRequestInterface callback) {
        JSONObject obj = new JSONObject((params));
        Log.e("obj",obj.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(params), new Response.Listener<JSONObject>() {
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

    public interface VolleyJSONRequestInterface {
        void onSuccess(JSONObject obj);

        void onFailure(VolleyError error);
    }
}
