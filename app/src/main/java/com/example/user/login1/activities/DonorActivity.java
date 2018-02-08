package com.example.user.login1.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.user.login1.R;
import com.example.user.login1.classes.SharedPrefManager;

public class DonorActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor);
        drawerLayout=findViewById(R.id.drawer_donor);
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView=findViewById(R.id.navigation_donor);
        navigationView.setNavigationItemSelectedListener(this);

        if(!SharedPrefManager.getmInstance(this).isloggedin()){
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
            return;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//navigation drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.profile){
            Toast.makeText(this,"Profile Selected",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.update){
            Toast.makeText(this,"Update Selected",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.availability){
            Toast.makeText(this,"Availability Selected",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.rate){
            Toast.makeText(this,"Rate Selected",Toast.LENGTH_SHORT).show();
        }
        if(id==R.id.logout){
            Toast.makeText(this,"Logout Selected",Toast.LENGTH_SHORT).show();
            SharedPrefManager.getmInstance(this).logout();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

        return false;
    }
}
