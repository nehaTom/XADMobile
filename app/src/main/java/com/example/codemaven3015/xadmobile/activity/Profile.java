package com.example.codemaven3015.xadmobile.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Profile extends AppCompatActivity {
    EditText fname,lname,emailId,contactNo,adharNo,address,panNo;
    RadioGroup gender;
    Button update;
    LinearLayout profile_View , profile_Edit;
    String flag;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initWidgets();
         sharedPreferences=getApplicationContext().getSharedPreferences("User_Info",MODE_PRIVATE);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputValidation()){
                    try {
                        callRegistrationAPI();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void callRegistrationAPI() throws JSONException {
        String url = "http://xadnew.quickbooksupport365.com/service/profile.php";

        HashMap<String, String> params = new HashMap<>();
        params.put("update","1");
        JSONObject obj_value = new JSONObject();
        obj_value.put("mobile_no",fname.getText().toString());

        obj_value.put("first_name",fname.getText().toString());
        obj_value.put("last_name",lname.getText().toString());
        obj_value.put("email",emailId.getText().toString());
        obj_value.put("mobile_no",sharedPreferences.getString("PHONE",""));
        // obj_value.put("mobile_no",contactNo.getText().toString());
        obj_value.put("adhar_no",adharNo.getText().toString());
        obj_value.put("address",address.getText().toString());
        obj_value.put("pan_no",panNo.getText().toString());

        params.put("responsedata",obj_value.toString());


        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getApplicationContext(),url,params);
        volleyJSONRequest.executeRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
                try {
                    String status = obj.getString("status");
                    if(status.equalsIgnoreCase("success")){
                       // mViewPager.setCurrentItem(1);

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
                Toast.makeText(getApplicationContext(),error.getMessage().toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
        //mViewPager.setCurrentItem(1);


    }

    private boolean inputValidation() {
        if(fname.getText().toString().isEmpty()){
            fname.setError("");
            return false;
        }else if(lname.getText().toString().isEmpty()){

        }

        return true;
    }

    private void initWidgets() {
        flag = getIntent().getStringExtra("flag");
        profile_View = findViewById(R.id.profile_View);
        profile_Edit = findViewById(R.id.profile_Edit);
        update=findViewById(R.id.update);
        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        emailId=findViewById(R.id.emailId);
        contactNo=findViewById(R.id.contactNo);
        adharNo=findViewById(R.id.adharNo);
        address=findViewById(R.id.adharNo);
        panNo=findViewById(R.id.panNo);
        if(flag != null && flag.equalsIgnoreCase("profile_view")){
            profile_Edit.setVisibility(View.GONE);
            profile_View.setVisibility(View.VISIBLE);
        }
    }

}
