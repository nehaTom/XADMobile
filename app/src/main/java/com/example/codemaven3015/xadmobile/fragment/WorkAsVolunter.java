package com.example.codemaven3015.xadmobile.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.codemaven3015.xadmobile.Constant.Constant;
import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.api.VolleyJSONRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

public class WorkAsVolunter extends Fragment implements View.OnClickListener {
    View v;
    CheckBox checkbox_all, checkbox_sun, checkbox_mon, checkbox_tue, checkbox_wed, checkbox_thu, checkbox_fri, checkbox_sat;
    CheckBox checkbox_slot1, checkbox_slot2, checkbox_slot3, checkbox_slot4;
    EditText et_time, et_mobileNo;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
     static  String selectAll;
    static  String timeSlot;
    Button submit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_work_as_volunter, container, false);
         sharedPreferences=getContext().getSharedPreferences("User_Info",MODE_PRIVATE);
         editor=sharedPreferences.edit();
        setWidgets(v);
        //  onCheckboxClicked(v);
submit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        selectAll = "";
        if(checkbox_all.isChecked()){
            selectAll="Sunday,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday";
        }
        else
        {
            if(checkbox_sun.isChecked()) {
                selectAll = ",Sunday";
            }
                if (checkbox_mon.isChecked()) {
                    selectAll = selectAll + "," + "Monday";
                }
                if (checkbox_tue.isChecked()) {
                    selectAll = selectAll + ",Tuesday";
                }
                if (checkbox_wed.isChecked()) {
                    selectAll = selectAll + ",Wednesday";
                }
                if (checkbox_thu.isChecked()) {
                    selectAll = selectAll + ",Thursday";
                }
                if (checkbox_fri.isChecked()) {
                    selectAll = selectAll + ",Friday";
                }
                if (checkbox_sat.isChecked()) {
                    selectAll = selectAll + ",Saturday";
                }
                selectAll = selectAll.replaceFirst(",", "");

            }
      //  volunterSaveApi();
        Log.e("valueOf",selectAll);
        timeSlot="";
        if(checkbox_slot1.isChecked()) {
            timeSlot = ",1";
        }
        if (checkbox_slot2.isChecked()) {
            timeSlot = timeSlot + "," + "2";
        }
        if (checkbox_slot3.isChecked()) {
            timeSlot = timeSlot + ",3";
        }
        if (checkbox_slot4.isChecked()) {
            timeSlot = timeSlot + ",4";
        }
        timeSlot=timeSlot.replaceFirst(",","");
        Log.e("valueOfTume",timeSlot);

        volunterSaveApi();
    }
});

        return v;
    }

    private void volunterSaveApi() {

        String url = Constant.BaseURL+"time.php";
        HashMap<String, String> params = new HashMap<>();

        params.put("add_time","1");  //flag
        params.put("responsedata","1"); //flag
        params.put("mobile",et_mobileNo.getText().toString());
        params.put("days",selectAll);
        params.put("time_slot",timeSlot);
        params.put("time",et_time.getText().toString());
        params.put("user_id",sharedPreferences.getString("user_id",""));
        VolleyJSONRequest volleyJSONRequest = new VolleyJSONRequest(getContext(),url,params);
//        progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("Sending OTP");
//        progressDialog.setCanceledOnTouchOutside(false);
//        progressDialog.show();
        volleyJSONRequest.executeStringRequest(new VolleyJSONRequest.VolleyJSONRequestInterface() {
            @Override
            public void onSuccess(JSONObject obj) {
             //   progressDialog.hide();

                try {
                    String status = obj.getString("status");
                    if(status.equalsIgnoreCase("success")){

//                        JSONObject objData = new JSONObject();
//                        objData = obj.getJSONObject("data");
                   Toast.makeText(getActivity(),"The data is saved",Toast.LENGTH_SHORT).show();


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
        checkbox_all = v.findViewById(R.id.checkbox_all);
        checkbox_all.setOnClickListener(this);
        checkbox_sun = v.findViewById(R.id.checkbox_sun);
        checkbox_sun.setOnClickListener(this);
        checkbox_mon = v.findViewById(R.id.checkbox_mon);
        checkbox_mon.setOnClickListener(this);
        checkbox_tue = v.findViewById(R.id.checkbox_tue);
        checkbox_tue.setOnClickListener(this);
        checkbox_wed = v.findViewById(R.id.checkbox_wed);
        checkbox_wed.setOnClickListener(this);
        checkbox_thu = v.findViewById(R.id.checkbox_thu);
        checkbox_thu.setOnClickListener(this);
        checkbox_fri = v.findViewById(R.id.checkbox_fri);
        checkbox_fri.setOnClickListener(this);
        checkbox_sat = v.findViewById(R.id.checkbox_sat);
        checkbox_sat.setOnClickListener(this);
        checkbox_slot1 = v.findViewById(R.id.checkbox_slot1);
        checkbox_slot1.setOnClickListener(this);
        checkbox_slot2 = v.findViewById(R.id.checkbox_slot2);
        checkbox_slot2.setOnClickListener(this);
        checkbox_slot3 = v.findViewById(R.id.checkbox_slot3);
        checkbox_slot3.setOnClickListener(this);
        checkbox_slot4 = v.findViewById(R.id.checkbox_slot4);
        checkbox_slot4.setOnClickListener(this);


        et_time = v.findViewById(R.id.et_time);

        et_mobileNo = v.findViewById(R.id.et_mobileNo);
        submit = v.findViewById(R.id.submit);
        selectAll="";
        timeSlot="";
    }

    @Override
    public void onClick(View v) {
        boolean checked = ((CheckBox) v).isChecked();
        switch (v.getId()) {
            case R.id.checkbox_all:
                // list.add(chk1.getTag().toString());
              if(checkbox_all.isChecked()) {
                  checkbox_sun.setChecked(true);
                  checkbox_mon.setChecked(true);
                  checkbox_tue.setChecked(true);
                  checkbox_wed.setChecked(true);
                  checkbox_thu.setChecked(true);
                  checkbox_fri.setChecked(true);
                  checkbox_sat.setChecked(true);
                  selectAll="sunday,Momday,Tuesday,Wednesday,Thursday,Friday,Saturday";
                //  Log.e("valueOfCheckBox",selectAll);
                  Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
              }else{
           //       selectAll="";
                  checkbox_sun.setChecked(false);
                  checkbox_mon.setChecked(false);
                  checkbox_tue.setChecked(false);
                  checkbox_wed.setChecked(false);
                  checkbox_thu.setChecked(false);
                  checkbox_fri.setChecked(false);
                  checkbox_sat.setChecked(false);
              }
                break;
            case R.id.checkbox_sun:
                if(checkbox_sun.isChecked()) {
                    //   list.add(chk2.getTag().toString());
                   // selectAll = selectAll + ",Sunday";
                 //   Log.e("valueOfCheckBox", selectAll);
                    Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                }else{
                     //    selectAll=selectAll.replace("Sunday","");
                  //  Log.e("valueOfCheckBox",selectAll);
                }
                break;

            case R.id.checkbox_mon:
                //   list.add(chk3.getTag().toString());
                if(checkbox_sun.isChecked()) {
               // selectAll=selectAll+",Monday";
              //  Log.e("valueOfCheckBox--Mon",selectAll);
               // Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                }else{
                //    selectAll=selectAll.replace(",Monday","");
                 //   Log.e("valueOfCheckBox--Mon",selectAll);
                }
                break;
            case R.id.checkbox_tue:
                //  list.add(chk4.getTag().toString());
             //   Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_wed:
                // list.add(chk5.getTag().toString());
              //  Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_thu:
                //  list.add(chk6.getTag().toString());

                break;
            case R.id.checkbox_fri:
                // list.add(chk7.getTag().toString());

                break;
            case R.id.checkbox_sat:
                // list.add(chk8.getTag().toString());

                break;
            case R.id.checkbox_slot1:
                //  list.add(chk9.getTag().toString());
              //  Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_slot2:
                //   list.add(chk9.getTag().toString());
              //  Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_slot3:
                //   list.add(chk9.getTag().toString());
              //  Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                break;
            case R.id.checkbox_slot4:
                //  list.add(chk9.getTag().toString());
              //  Toast.makeText(getContext(), "checkbox", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}

//-------------------------------------------------oldData-------------------------------------------------------
////    public void onCheckboxClicked(View view) {
//        boolean checked=((CheckBox)view).isChecked();
//        switch(view.getId()) {
//            case R.id.checkbox_all:
//               // list.add(chk1.getTag().toString());
//
//                Toast.makeText(getContext(),"checkbox",Toast.LENGTH_SHORT);
//                break;
//            case R.id.checkbox_sun:
//             //   list.add(chk2.getTag().toString());
//                Toast.makeText(getContext(),"checkbox",Toast.LENGTH_SHORT);
//                break;
//
//            case R.id.checkbox_mon:
//             //   list.add(chk3.getTag().toString());
//                Toast.makeText(getContext(),"checkbox",Toast.LENGTH_SHORT);
//                break;
//            case R.id.checkbox_tue:
//              //  list.add(chk4.getTag().toString());
//                Toast.makeText(getContext(),"checkbox",Toast.LENGTH_SHORT);
//                break;
//            case R.id.checkbox_wed:
//               // list.add(chk5.getTag().toString());
//                Toast.makeText(getContext(),"checkbox",Toast.LENGTH_SHORT);
//                break;
//            case R.id.checkbox_thu:
//              //  list.add(chk6.getTag().toString());
//
//                break;
//            case R.id.checkbox_fri:
//              // list.add(chk7.getTag().toString());
//
//                break;
//            case R.id.checkbox_sat:
//               // list.add(chk8.getTag().toString());
//
//                break;
//            case R.id.checkbox_slot1:
//              //  list.add(chk9.getTag().toString());
//
//                break;
//            case R.id.checkbox_slot2:
//             //   list.add(chk9.getTag().toString());
//
//                break;
//            case R.id.checkbox_slot3:
//             //   list.add(chk9.getTag().toString());
//
//                break;
//            case R.id.checkbox_slot4:
//              //  list.add(chk9.getTag().toString());
//
//                break;
//
//        }
//    }

