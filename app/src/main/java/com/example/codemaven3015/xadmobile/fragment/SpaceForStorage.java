package com.example.codemaven3015.xadmobile.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.codemaven3015.xadmobile.Constant.Constant;
import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class SpaceForStorage extends Fragment {
    View v;
    EditText et_location,et_address,et_comments,et_mobileNo;
    Button submit;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_space_for_storage, container, false);
        sharedPreferences=getContext().getSharedPreferences("User_Info", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        setWidgets(v);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateFields())
               storageSpaceApi();
            }
        });

        return v;
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
    }

    private void setWidgets(View v) {

        et_location=v.findViewById(R.id.et_location);
        et_address=v.findViewById(R.id.et_address);
        et_comments=v.findViewById(R.id.et_comments);
        et_mobileNo=v.findViewById(R.id.et_mobileNo);
        submit=v.findViewById(R.id.submit);
    }
}
