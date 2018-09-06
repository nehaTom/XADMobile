package com.example.codemaven3015.xadmobile.activity;

import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.codemaven3015.xadmobile.R;
import com.example.codemaven3015.xadmobile.fragment.DonateCSR;
import com.example.codemaven3015.xadmobile.fragment.DonateMoney;
import com.example.codemaven3015.xadmobile.fragment.SpaceForStorage;
import com.example.codemaven3015.xadmobile.fragment.WorkAsVolunter;

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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        Spinner category_spinner,nearest_center;
        ImageView deviceImagView;
        EditText deviceNameEditText,DeviceDescripEditText,remarkEditText;
        RadioGroup working_statusRG,radio_markRG;
        Button submit_button;
        private static final String ARG_SECTION_NUMBER = "section_number";

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
             submit_button.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if(inputValidate()){
                         Intent intent=new Intent(getContext(),DonatedList.class);
                         startActivity(intent);

                     }
                 }
             });
            return rootView;
        }

        private void initWidgets(View rootView) {
            category_spinner=rootView.findViewById(R.id.category_spinner);
            nearest_center=rootView.findViewById(R.id.nearest_center);

            deviceImagView=rootView.findViewById(R.id.imv);

            deviceNameEditText=rootView.findViewById(R.id.device_name);
            DeviceDescripEditText=rootView.findViewById(R.id.message);
            remarkEditText=rootView.findViewById(R.id.remark);

            working_statusRG=rootView.findViewById(R.id.working_status);
            radio_markRG=rootView.findViewById(R.id.radio_mark);

            submit_button=rootView.findViewById(R.id.button);
        }

        private boolean inputValidate(){
            if(deviceNameEditText.getText().toString().isEmpty()){
                deviceNameEditText.setError("Enter device name ");
                return false;
            }else if(DeviceDescripEditText.getText().toString().isEmpty()){
                DeviceDescripEditText.setError("Enter device description ");
                return false;
            }else if(remarkEditText.getText().toString().isEmpty()){
                remarkEditText.setError("Enter the remarks ");
                return false;
            }else if(working_statusRG.getCheckedRadioButtonId() == -1){
                Toast.makeText(getContext(),"Please Select one working status of device ",
                        Toast.LENGTH_SHORT).show();
                return false;
            }else if(radio_markRG.getCheckedRadioButtonId() == -1){
                Toast.makeText(getContext(),"Please Select one mark ",
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
            switch ((position)){
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
