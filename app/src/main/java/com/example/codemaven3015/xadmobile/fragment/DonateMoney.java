package com.example.codemaven3015.xadmobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.codemaven3015.xadmobile.R;

public class DonateMoney  extends android.support.v4.app.Fragment {
    View v;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_money, container, false);

        //setWidgets(v);

        return v;
    }
}
