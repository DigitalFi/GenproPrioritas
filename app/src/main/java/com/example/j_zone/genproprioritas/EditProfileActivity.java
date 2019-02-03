package com.example.j_zone.genproprioritas;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class EditProfileActivity extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        pager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);


        pager.setAdapter(new AdapterEditprofile(getSupportFragmentManager()));

        tabLayout.setTabTextColors(getResources().getColor(R.color.putih),
                getResources().getColor(android.R.color.white));

        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
    }
}
