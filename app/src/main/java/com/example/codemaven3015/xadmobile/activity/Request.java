package com.example.codemaven3015.xadmobile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Request extends AppCompatActivity {



    EditText device_name, patientConditionEditText ;
    Spinner category_spinner,nearest_center;
    ImageView prescriptionImv;
    Button submitButton;
    ProgressDialog progressDialog;
    Long id_facilation,id_device;
    static ArrayList<String> categoryName=new ArrayList<>();
    static ArrayList<String> centreName=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initWidgets();

       // categoryName=new ArrayList<>();
      //  centreName=new ArrayList<>();
        progressDialog=new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading ......");
        progressDialog.setCanceledOnTouchOutside(false);




        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(

                    AdapterView<?> parent, View view, int position, long id) {
             //   selection_item=parent.getItemAtPosition(position).toString();
                Log.d("click", "onItemSelected: ");
                id_device=id;
                Toast.makeText(getApplicationContext(), "Spinner1: position=" + position + " id=" + id, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // showToast("Spinner1: unselected");
            }
        });



        nearest_center.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("click", "onItemSelected:center ");
               // facilation_item=parent.getItemAtPosition(position).toString();
                id_facilation=id;
                Toast.makeText(getApplicationContext(),"position :"+position+"id :"+id,Toast.LENGTH_SHORT).show();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(arrayAdapter);


        ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, centreName);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nearest_center.setAdapter(arrayAdapter1);



        loadCategorySpinnerData();
        loadNearestCenterSpinnerData();


        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputvalidate()){

                    try {
                        callRequestDeviceApi();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(getApplicationContext(),DonatedList.class);
                    startActivity(intent);
                }
            }
        });

    }

    private void callRequestDeviceApi() throws JSONException {

        String url="http://xadnew.quickbooksupport365.com/service/request.php";
        HashMap<String,String>parms=new HashMap<>();
        parms.put("add_request","1");
        JSONObject obj=new JSONObject();
        obj.put("category_id",id_device);
        obj.put("donation_center_id",id_facilation);
        obj.put("device_name",device_name.getText().toString());
        obj.put("patient_condition",patientConditionEditText.getText().toString());
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(1);
        jsonArray.put(2);
        obj.put("images",jsonArray);
        parms.put("responsedata",obj.toString());
        Toast.makeText(this,"JsonArray"+jsonArray+" obj Data"+obj,Toast.LENGTH_LONG).show();
        VolleyJSONRequest volleyJSONRequest=new VolleyJSONRequest(getApplicationContext(),url,parms);
        volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
                Toast.makeText(getApplicationContext(),""+obj,Toast.LENGTH_LONG);
            }

            @Override
            public void onFailure(VolleyError error) {

            }
        });
    }

    private boolean inputvalidate() {
        if(device_name.getText().toString().isEmpty()){
            device_name.setError("Enter Device Name ");
            return false;
        }else if(patientConditionEditText.getText().toString().isEmpty()){
            patientConditionEditText.setError("About patient Condition");
            return false;
        }
        return true;
    }

    private void initWidgets() {
        device_name=findViewById(R.id.device_name);
        patientConditionEditText=findViewById(R.id.message);
        category_spinner=findViewById(R.id.category_spinner);
        nearest_center=findViewById(R.id.nearest_center);
        prescriptionImv=findViewById(R.id.imv);
        submitButton=findViewById(R.id.button);

        category_spinner=findViewById(R.id.category_spinner);
        nearest_center=findViewById(R.id.nearest_center);
    }


    private void loadCategorySpinnerData() {
        String URL = "http://xadnew.quickbooksupport365.com/service/category.php";
        HashMap<String, String> params = new HashMap<>();

        params.put("category", "1");
        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getApplicationContext(), URL, params);
        //  progressDialog.show();
        volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
                //      progressDialog.hide();
                Log.d("gjhgjh", obj.toString());

                try {
                    String status = obj.getString("status");
                    Log.d("Value1", "" + status);
                    if (status.equalsIgnoreCase("success")) {
                        JSONObject values = obj.getJSONObject("data");
                        int count = values.length();
                        Log.d("jhsgv", String.valueOf(count));
                        for (int i = 0; i < count-1; i++) {
                            JSONObject dxdd = values.getJSONObject(String.valueOf(i));
                            String cat_name = dxdd.getString("cat_name");
                            categoryName.add(cat_name);
                            Log.d("ghsdj", cat_name);

                        }


                    } else {
                        String msg = obj.getString("message");
                        Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_LONG).show();

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
    private void loadNearestCenterSpinnerData() {
        String URL = "http://xadnew.quickbooksupport365.com/service/donationCenter.php";

        HashMap<String, String> params1 = new HashMap<>();
        params1.put("center", "1");
        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getApplicationContext(), URL, params1);
        //   progressDialog.show();
        volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
                //      progressDialog.hide();
                Log.d("gjhgjh", obj.toString());

                try {
                    String status = obj.getString("status");
                    Log.d("Value1", "" + status);
                    if (status.equalsIgnoreCase("success")) {
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String center = jsonObject1.getString("center_name");
                            Log.d("country", "center");
                            centreName.add(center);
                            Log.d("country", center);
                        }

                    } else {
                        String msg = obj.getString("message");
                        Toast.makeText(getApplicationContext(), msg,
                                Toast.LENGTH_LONG).show();

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


}
