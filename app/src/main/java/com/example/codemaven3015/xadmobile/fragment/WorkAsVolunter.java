package com.example.codemaven3015.xadmobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.codemaven3015.xadmobile.R;

public class WorkAsVolunter extends Fragment {
View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.activity_work_as_volunter, container, false);

        //setWidgets(v);

        return v;
    }
}
