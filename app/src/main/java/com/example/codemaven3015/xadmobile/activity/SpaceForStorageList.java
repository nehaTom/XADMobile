package com.example.codemaven3015.xadmobile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.example.codemaven3015.xadmobile.Constant.Constant;

import com.example.codemaven3015.xadmobile.Model.SForSModel;
import com.example.codemaven3015.xadmobile.R;

import com.example.codemaven3015.xadmobile.adapter.SpaceFSAdapter;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;
import com.example.codemaven3015.xadmobile.fragment.SpaceForStorage;
import com.example.codemaven3015.xadmobile.helper.SpacesItemDecoration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SpaceForStorageList extends AppCompatActivity {
    SForSModel sForSModel;
    RecyclerView spforStorageRecyclerView;
    SpaceFSAdapter adapter;
    ArrayList<SForSModel> sForSModelsList;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button submit,update;
    EditText et_location,et_address,et_comments,et_mobileNo;
    ProgressDialog progressDialog;
    String afterListBtnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_for_storage);

        submit=findViewById(R.id.submit);
        update=findViewById(R.id.update);
        et_location=findViewById(R.id.et_location);
        et_address=findViewById(R.id.et_address);
        et_comments=findViewById(R.id.et_comments);
        et_mobileNo=findViewById(R.id.et_mobileNo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        toolbar.setTitle(getString(R.string.title_SFS));
        toolbar.setNavigationIcon(R.drawable.arrow);



       // setSupportActionBar(toolbar);
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.setMessage("Loading List");
        sharedPreferences = this.getSharedPreferences("User_Info", MODE_PRIVATE);
        editor=sharedPreferences.edit();
       /* Handler handler = new Handler();
        handler.postDelayed(
                new Runnable() {
                    public void run() {
                       // DonatedSpaceApi();
                    }
                }, 1000);


        //setSpaceforSListAdapter();
*/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //------------------------------------
                if(validateFields()) {
                    storageSpaceApi();
                }
            }
        });
        afterListBtnClick="";
       afterListBtnClick=getIntent().getStringExtra("flag");
     String s="1";
      //  Bundle extras = getIntent().getExtras();
if(sharedPreferences.getBoolean("Button",false)){
            String loc, add, comm, mob,check=null;
            loc = getIntent().getStringExtra("location");
            add = getIntent().getStringExtra("address");
            comm = getIntent().getStringExtra("comments");
               check = getIntent().getStringExtra("flag");
                mob = getIntent().getStringExtra("mobile");
            et_location.setText(loc);
            et_address.setText(add);
            et_comments.setText(comm);
            et_mobileNo.setText(mob);
            //  update.setVisibility(View.VISIBLE);

            submit.setVisibility(View.GONE);
            editor.putBoolean("Button",false);
            editor.commit();
        }
        else{
            submit.setVisibility(View.VISIBLE);
        }



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Prescription.super.onBackPressed();
                Intent intent=new Intent(SpaceForStorageList.this, Donate
                        .class);
                intent.putExtra("viewpager_position", 2);
                startActivity(intent);
            }
        });
    }

    private boolean validateFields() {
        if(et_location.getText().toString().isEmpty()) {
            et_location.setError("Enter Location");
            return false;
        }else if(et_address.getText().toString().isEmpty()) {
            et_address.setError("Enter address");
            return false;
        }else if(et_comments.getText().toString().isEmpty()) {
            et_comments.setError("Enter comments");
            return false;
        }else if(et_mobileNo.getText().length()<10){
            et_mobileNo.setError("Invalid phone number");
            return false;
        }else if(et_mobileNo.getText().toString().trim().isEmpty()){
            et_mobileNo.setError("Enter phone number");
            return false;
        }
        return true;
    }
    private void storageSpaceApi() {

        String url = Constant.BaseURL+"space.php";
        HashMap<String, String> params = new HashMap<>();


        params.put("location",et_location.getText().toString());
        params.put("address",et_address.getText().toString());
        params.put("comments",et_comments.getText().toString());
        params.put("mobile",et_mobileNo.getText().toString());
        params.put("user_id",sharedPreferences.getString("user_id",""));
        params.put("add_space","1");  //flag
        params.put("responsedata","1");  //flag

        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getApplicationContext(),url,params);
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
                        Toast.makeText(getApplicationContext(),"Successful Load the Space",Toast.LENGTH_LONG).show();
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
                        Toast.makeText(getApplicationContext(),msg,
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
    }
    private void DonatedSpaceApi() {
        progressDialog.show();

        String url = Constant.BaseURL+"space.php";
        HashMap<String, String> parms = new HashMap<>();

        parms.put("user_id", sharedPreferences.getString("user_id", ""));  //user id
        parms.put("place", "1");   //flag

        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getApplicationContext(), url, parms);
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
            for (int i = 0; i < count - 1; i++) {
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


    private void setListAdapter() {
        sForSModelsList = new ArrayList<>();

        spforStorageRecyclerView = findViewById(R.id.donatedSpace_RV);
        //-------------------
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        spforStorageRecyclerView.setLayoutManager(mLayoutManager);
        //--------------------------------
        adapter = new SpaceFSAdapter(this, sForSModelsList);
        spforStorageRecyclerView.addItemDecoration(new SpacesItemDecoration(15));
        spforStorageRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

}
