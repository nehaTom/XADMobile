package com.example.codemaven3015.xadmobile.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.activity.Home;
import com.example.codemaven3015.xadmobile.activity.UserProfile;

public class FragmentOtpVarification extends android.support.v4.app.Fragment {
    View v;
    Button StartedButton,ProfileButton;
    TextView enterOTP;
    SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.otp_varification, container, false);
        sharedPreferences = getContext().getSharedPreferences("User_Info", Context.MODE_PRIVATE);

        setWidgets(v);

        return v;
    }

    private void setWidgets(View v) {
        ProfileButton = v.findViewById(R.id.ProfileButton);
        StartedButton = v.findViewById(R.id.StartedButton);
        enterOTP = v.findViewById(R.id.enterOTP);
        enterOTP.setText("Welcome  "+sharedPreferences.getString("FIRST_NAME",""));
        StartedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.getBoolean("FIRST_LOGIN",false)) {
                    Intent i = new Intent(getContext(), UserProfile.class);
                    startActivity(i);

                } else {
                    Intent i = new Intent(getContext(), Home.class);
                    startActivity(i);
                }
//                Intent i = new Intent(getContext(), Home.class);
//                startActivity(i);
            }
        });


        ProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), UserProfile.class);
                startActivity(i);

            }
        });
    }
}
