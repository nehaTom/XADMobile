package com.example.codemaven3015.xadmobile.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;
import com.example.codemaven3015.xadmobile.fragment.DonateCSR;
import com.example.codemaven3015.xadmobile.fragment.DonateMoney;
import com.example.codemaven3015.xadmobile.fragment.SpaceForStorage;
import com.example.codemaven3015.xadmobile.fragment.WorkAsVolunter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Donate extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    ProgressDialog progressDialog;

    static ArrayList<String> categoryName = new ArrayList<>();
    static ArrayList<String> centreName = new ArrayList<>();
    public static  SharedPreferences sharedPreferences;
    public static  SharedPreferences.Editor editor;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        //-----ProgressBar Initialization
        progressDialog = new ProgressDialog(getApplicationContext());
        progressDialog.setMessage("Loading .......");
        progressDialog.setCanceledOnTouchOutside(false);

        sharedPreferences=this.getSharedPreferences("User_Info",MODE_PRIVATE);
        editor=sharedPreferences.edit();


        loadSpinnerData();
        loadSpinnerData1();

        //  progressDialog.show();

        //   loadSpinnerData();

    }

    private void loadSpinnerData1() {
        String URL = "http://xadnew.quickbooksupport365.com/service/donationCenter.php";
        HashMap<String, String> params1 = new HashMap<>();
        params1.put("center", "1");
        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getApplicationContext(), URL, params1);
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
                        JSONArray jsonArray = obj.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            String country = jsonObject1.getString("center_name");
                            Log.d("country", "country");
                            centreName.add(country);
                            Log.d("country", country);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    //----------ApiCalling and ProgressBAr----------------

    private void loadSpinnerData() {
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

    // --------------------------------------

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        Spinner category_spinner, nearest_center;
        ImageView deviceImagView;
        EditText deviceNameEditText, DeviceDescripEditText, remarkEditText;
        RadioGroup working_statusRG, radio_markRG;
        Button submit_button;
        private static final String ARG_SECTION_NUMBER = "section_number";
        String selection_item,facilation_item;
        Long id_device,id_facilation;
        int device_condition,mark_donate;



        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_assistive_device, container, false);
            //TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            initWidgets(rootView);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categoryName);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            category_spinner.setAdapter(arrayAdapter);

            category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(
                        AdapterView<?> parent, View view, int position, long id) {
                    selection_item=parent.getItemAtPosition(position).toString();
                    id_device=id;
                    Toast.makeText(getContext(), "Spinner1: position=" + position + " id=" + id+"item :"+selection_item, Toast.LENGTH_SHORT).show();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    // showToast("Spinner1: unselected");
                }
            });

            ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, centreName);
            arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            nearest_center.setAdapter(arrayAdapter1);

           nearest_center.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                   facilation_item=parent.getItemAtPosition(position).toString();
                   id_facilation=id;
                   Toast.makeText(getContext(),"facilation iteam position:"+facilation_item+"position :"+position+"id :"+id,Toast.LENGTH_SHORT).show();;
               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });

            working_statusRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.working:
                            device_condition=1;
                            Toast.makeText(getContext(),"Working",Toast.LENGTH_SHORT).show();
                            // do operations specific to this selection
                            break;
                        case R.id.not_working:
                            device_condition=0;
                            Toast.makeText(getContext(),"Not Working",Toast.LENGTH_SHORT).show();
                            // do operations specific to this selection
                            break;
                    }
                }
            });

            radio_markRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId){
                        case R.id.yes:
                            mark_donate=1;
                            Toast.makeText(getContext(),"yes",Toast.LENGTH_SHORT).show();
                            // do operations specific to this selection
                            break;
                        case R.id.no:
                            mark_donate=0;

                            Toast.makeText(getContext(),"no",Toast.LENGTH_SHORT).show();
                            // do operations specific to this selection
                            break;
                    }
                }
            });


            submit_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (inputValidate()) {

                        try {
                            saveDevice();
                     //       Intent intent = new Intent(getContext(), DonatedList.class);
                     //       startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(getContext(), DonatedList.class);
                        startActivity(intent);

                    }
                }
            });
            return rootView;
        }

        private void saveDevice() throws JSONException {
            String url="http://xadnew.quickbooksupport365.com/service/devices.php";
            HashMap<String,String>parms=new HashMap<>();
            parms.put("user_id",sharedPreferences.getString("user_id",""));
            parms.put("add","1");
            JSONObject obj_data=new JSONObject();
            obj_data.put("category_id",id_device);
            obj_data.put("donation_center_id",id_facilation);
            obj_data.put("device_name",deviceNameEditText.getText().toString());
            obj_data.put("remarks",remarkEditText.getText().toString());
            obj_data.put("working_status",device_condition);
            obj_data.put("mark_donate",mark_donate);
            obj_data.put("description",DeviceDescripEditText.getText().toString());
            JSONArray jsonArray=new JSONArray();
            jsonArray.put(1);
            jsonArray.put(2);
            obj_data.put("images",jsonArray);
            parms.put("responsedata",obj_data.toString());
            Toast.makeText(getContext(),"JsonArray"+jsonArray+" obj Data"+obj_data,Toast.LENGTH_LONG).show();
            VolleyJSONRequest volleyJSONRequest=new VolleyJSONRequest(getContext(),url,parms);
            volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
                @Override
                public void onSuccess(JSONObject obj) {

                    Toast.makeText(getContext(),""+obj,Toast.LENGTH_LONG);

                }

                @Override
                public void onFailure(VolleyError error) {

                }
            });
        }

        private void initWidgets(View rootView) {
            category_spinner = rootView.findViewById(R.id.category_spinner);

            nearest_center = rootView.findViewById(R.id.nearest_center);

            deviceImagView = rootView.findViewById(R.id.imv);

            deviceNameEditText = rootView.findViewById(R.id.device_name);
            DeviceDescripEditText = rootView.findViewById(R.id.message);
           remarkEditText = rootView.findViewById(R.id.remark);

            working_statusRG = rootView.findViewById(R.id.working_status);
            radio_markRG = rootView.findViewById(R.id.radio_mark);

            submit_button = rootView.findViewById(R.id.button);
        }

        private boolean inputValidate() {
            if (deviceNameEditText.getText().toString().isEmpty()) {
                deviceNameEditText.setError("Enter device name ");
                return false;
            } else if (DeviceDescripEditText.getText().toString().isEmpty()) {
                DeviceDescripEditText.setError("Enter device description ");
                return false;
            } else if (working_statusRG.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "Please Select one working status of device ",
                        Toast.LENGTH_SHORT).show();
                return false;
            } else if (radio_markRG.getCheckedRadioButtonId() == -1) {
                Toast.makeText(getContext(), "Please Select one mark ",
                        Toast.LENGTH_SHORT).show();
                return false;
            }else if(selection_item==null){
                Toast.makeText(getContext(), "Please Select device category : ",
                        Toast.LENGTH_SHORT).show();
                return false;
            }else if(facilation_item==null) {
                Toast.makeText(getContext(), "Please Select facilation center : ",
                        Toast.LENGTH_SHORT).show();
                return false;
            }
            return true;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch ((position)) {
                case 0:
                    return PlaceholderFragment.newInstance(position + 1);

                case 3:
                    DonateMoney donateMoney = new DonateMoney();
                    return donateMoney;
                case 1:
                    WorkAsVolunter wrkasv = new WorkAsVolunter();
                    return wrkasv;
                case 2:
                    SpaceForStorage spaceForStorage = new SpaceForStorage();
                    return spaceForStorage;
                case 4:
                    DonateCSR donateCSR = new DonateCSR();
                    return donateCSR;

                default:
                    return PlaceholderFragment.newInstance(position + 1);
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 5;
        }
    }
}
