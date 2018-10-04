package com.example.codemaven3015.xadmobile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.codemaven3015.xadmobile.Constant.Constant;
import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Request extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{



    EditText device_name, patientConditionEditText,description;
    Spinner category_spinner,nearest_center;
    ImageView prescriptionImv;
    Button submitButton;
    ProgressDialog progressDialog;
    Long id_facilation,id_device;
    String selection_item,facilation_item;
    TextView textView;
    static ArrayList<String> categoryName=new ArrayList<>();
    static ArrayList<String> centreName=new ArrayList<>();

    static ArrayList<String> categoirId = new ArrayList<>();
    static ArrayList<String> centreId = new ArrayList<>();


    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_drawer);


        initWidgets();
        categoryName.clear();
        centreName.clear();
        //-----------------------------
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
//
      //  textView=navigationView.getHeaderView(0).findViewById(R.id.textView);
//        setUserName();
        //----------------------------

        loadCategorySpinnerData();

        loadNearestCenterSpinnerData();
        progressDialog=new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading ......");
        progressDialog.setCanceledOnTouchOutside(false);
        sharedPreferences=this.getSharedPreferences("User_Info",MODE_PRIVATE);
        editor=sharedPreferences.edit();

       final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryName);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(arrayAdapter);

        category_spinner.postDelayed(new Runnable() {
            @Override
            public void run() {
                category_spinner.setAdapter(arrayAdapter);
            }
        }, 1000);
        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id= Long.parseLong(categoirId.get(position));
                selection_item=parent.getItemAtPosition(position).toString();
         //       Log.d("click", "onItemSelected: ");
                id_device=id;
     //           Toast.makeText(getApplicationContext(), "Spinner1: position=" + position + " id=" + id, Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // showToast("Spinner1: unselected");
            }
        });

   final     ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, centreName);
        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nearest_center.setAdapter(arrayAdapter1);
        nearest_center.postDelayed(new Runnable() {
            @Override
            public void run() {
                nearest_center.setAdapter(arrayAdapter1);
            }
        }, 1000);
        nearest_center.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  Log.d("click", "onItemSelected:center ");
                id= Long.parseLong(centreId.get(position));
                facilation_item=parent.getItemAtPosition(position).toString();
                id_facilation=id;
       //         Toast.makeText(getApplicationContext(),"position :"+position+"id :"+id,Toast.LENGTH_SHORT).show();;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputvalidate()){

                    try {
                        callRequestDeviceApi();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Intent intent=new Intent(getApplicationContext(),RequestList.class);
                    startActivity(intent);
                }
            }
        });




    }

//    private void setUserName() {
//
//        //  user_name_appmenu.setText("");
//        textView.setText(sharedPreferences.getString("FIRST_NAME",""));
//    }

    private void callRequestDeviceApi() throws JSONException {

      //  String url="http://xadnew.quickbooksupport365.com/service/request.php";
        String url=Constant.BaseURL+"request.php";
        HashMap<String,String>parms=new HashMap<>();
        parms.put("add_request","1");
        parms.put("user_id",sharedPreferences.getString("user_id",""));

        JSONObject obj=new JSONObject();
        obj.put("category_id",id_device);
        obj.put("donation_center_id",id_facilation);
        obj.put("device_name",device_name.getText().toString());
        obj.put("patient_condition",patientConditionEditText.getText().toString());
        obj.put("description",description.getText().toString());
        JSONArray jsonArray=new JSONArray();
        jsonArray.put(1);
        jsonArray.put(2);
        obj.put("images",jsonArray);
        parms.put("responsedata",obj.toString());
      //  Toast.makeText(this,"JsonArray"+jsonArray+" obj Data"+obj,Toast.LENGTH_LONG).show();
        VolleyJSONRequest volleyJSONRequest=new VolleyJSONRequest(getApplicationContext(),url,parms);
        volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
             //   Toast.makeText(getApplicationContext(),""+obj,Toast.LENGTH_LONG);
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
        description=findViewById(R.id.description);

        category_spinner=findViewById(R.id.category_spinner);
        nearest_center=findViewById(R.id.nearest_center);
    }


    private void loadCategorySpinnerData() {

      //  centreName.clear();
      //  String URL = "http://xadnew.quickbooksupport365.com/service/category.php";
        String URL = Constant.BaseURL+"category.php";
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
                            String cat_id = dxdd.getString("id");
                            categoryName.add(cat_name);
                            categoirId.add(cat_id);
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
//        String URL = "http://xadnew.quickbooksupport365.com/service/donationCenter.php";
        String URL = Constant.BaseURL+"donationCenter.php";
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
                            String center_id = jsonObject1.getString("id");
                            Log.d("country", "center");
                            centreName.add(center);
                            centreId.add(center_id);
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
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.my_profile) {
//            Toast.makeText(this,"This is in UserProfile ",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),ViewProfile.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.doner) {
            //  Toast.makeText(this,"This is in DOner ",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),Donate.class);
            startActivity(intent);
        } else if (id == R.id.doner_view) {
            //         Toast.makeText(this,"This is in DonerView ",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),DonatedList.class);
            startActivity(intent);
        } else if (id == R.id.recive) {
            //        Toast.makeText(this,"This is in Recive ",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),Request.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            //     Toast.makeText(this,"This is in ReciveView ",Toast.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),RequestList.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
