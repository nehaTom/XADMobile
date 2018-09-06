package com.example.codemaven3015.xadmobile.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.codemaven3015.xadmobile.R;

public class Request extends AppCompatActivity {

    EditText device_name, patientConditionEditText ;
    Spinner category_spinner,nearest_center;
    ImageView prescriptionImv;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        initWidgets();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputvalidate()){
                    Intent intent=new Intent(getApplicationContext(),DonatedList.class);
                    startActivity(intent);
                }
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

    }
}
