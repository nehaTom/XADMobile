package com.example.codemaven3015.xadmobile.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.codemaven3015.xadmobile.Constant.Constant;
import com.example.codemaven3015.xadmobile.Model.SForSModel;
import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.activity.SpaceForStorageList;
import com.example.codemaven3015.xadmobile.adapter.SpaceFSAdapter;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;
import com.example.codemaven3015.xadmobile.helper.SpacesItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class SpaceForStorage extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    View v;

    Button donatedSpace_btn;
    SForSModel sForSModel;
    RecyclerView spforStorageRecyclerView;
    SpaceFSAdapter adapter;
    ArrayList<SForSModel> sForSModelsList;
    private SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_space_for_storage, container, false);
        sharedPreferences=getContext().getSharedPreferences("User_Info", MODE_PRIVATE);
        editor=sharedPreferences.edit();

        setWidgets(v);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading List......");
        sharedPreferences = getActivity().getSharedPreferences("User_Info", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                        DonatedSpaceApi();
                    }
                }, 1000);


        setSpaceforSListAdapter();
        donatedSpace_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(validateFields())
//               storageSpaceApi();
                Intent intent=new Intent(getActivity(), SpaceForStorageList.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void setSpaceforSListAdapter() {
        sForSModelsList = new ArrayList<>();

       // spforStorageRecyclerView = findViewById(R.id.donatedSpace_RV);
        //-------------------
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        spforStorageRecyclerView.setLayoutManager(mLayoutManager);
        //--------------------------------
        adapter = new SpaceFSAdapter(getActivity(), sForSModelsList);
        spforStorageRecyclerView.addItemDecoration(new SpacesItemDecoration(15));
        spforStorageRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void DonatedSpaceApi() {
        progressDialog.show();

        String url = Constant.BaseURL+"space.php";
        HashMap<String, String> parms = new HashMap<>();

        parms.put("user_id", sharedPreferences.getString("user_id", ""));  //user id
        parms.put("place", "1");   //flag

        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getActivity(), url, parms);
        volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
                try {
                    String status = obj.getString("status");
                    if (status.equalsIgnoreCase("success")) {
                        progressDialog.hide();

                        parseData(obj);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(VolleyError error) {
                progressDialog.hide();

            }
        });
    }

    private void parseData(JSONObject obj) {
        try {
            JSONObject object = obj.getJSONObject("data");
            int count = object.length();
            for (int i = 0; i < count; i++) {
                sForSModel = new SForSModel();
                JSONObject listData = object.getJSONObject(String.valueOf(i));
                int cont = listData.length();
                //for (int k = 0; k < cont; k++) {
                //   donateModel.setId(listData.getString("id"));
                sForSModel.setId(listData.getString("id"));
                sForSModel.setUser_id(listData.getString("user_id"));
                sForSModel.setLocation(listData.getString("location"));
                sForSModel.setAddress(listData.getString("address"));
                sForSModel.setComments(listData.getString("comments"));
                sForSModel.setMobile(listData.getString("mobile"));


                sForSModelsList.add(sForSModel);   //adding data in modelList

                Log.d("Rai", "data : " + sForSModelsList.toString());
            }

            adapter.notifyDataSetChanged();
            Log.d("Rai1", "data Count : " + sForSModelsList.size());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setWidgets(View v) {
        donatedSpace_btn=v.findViewById(R.id.donatedSpace_btn);
        spforStorageRecyclerView = v.findViewById(R.id.donatedSpace_RV);
//        swipeRefreshLayout = v.findViewById(R.id.swfr);
    }

    @Override
    public void onRefresh() {

    }
    
    
    
    
    

    /*private void storageSpaceApi() {

        String url = Constant.BaseURL+"space.php";
        HashMap<String, String> params = new HashMap<>();


        params.put("location",et_location.getText().toString());
        params.put("address",et_address.getText().toString());
        params.put("comments",et_comments.getText().toString());
        params.put("mobile",et_mobileNo.getText().toString());
        params.put("user_id",sharedPreferences.getString("user_id",""));
        params.put("add_space","1");  //flag
        params.put("responsedata","1");  //flag

        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getContext(),url,params);
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Sending OTP");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
//                progressDialog.hide();

                try {
                    String status = obj.getString("status");
                    if(status.equalsIgnoreCase("success")){
                       Toast.makeText(getActivity(),"Successful Load the Space",Toast.LENGTH_LONG).show();
//                        JSONObject objData = new JSONObject();
//                        objData = obj.getJSONObject("data");
//                        editor.putString("PHONE",phoneEditText.getText().toString());
//                        editor.putString("LAST_ID",objData.getString("last_insert_id"));
//                        editor.putString("FIRST_NAME",firstNameEditText.getText().toString());
//                        editor.putString("LAST_NAME",lastNameEditText.getText().toString());
//                        editor.putBoolean("FIRST_LOGIN",isFirstLogging);
//                        editor.commit();
//                        mViewPager.setCurrentItem(1);

                    }else{
                        String msg=obj.getString("message");
                        Toast.makeText(getContext(),msg,
                                Toast.LENGTH_LONG).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(VolleyError error) {
//                progressDialog.hide();

            }
        });
    }*/

  
}
