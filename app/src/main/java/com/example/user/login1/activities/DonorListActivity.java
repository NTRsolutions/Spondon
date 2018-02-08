package com.example.user.login1.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.user.login1.R;
import com.example.user.login1.classes.DataDonor;

import java.util.ArrayList;

public class DonorListActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<DataDonor> obj = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_list);

       // listView =findViewById(R.id.list_view);

       //SearchActivitySample sa=new SearchActivitySample();

        //String bg = getIntent().getStringExtra("bg");

        //obj = sa.obj;

              // DonorCustomAdapter adapter = new DonorCustomAdapter(this,R.layout.donor_list_layout, obj);
        //listView.setAdapter(adapter);

    }
}
