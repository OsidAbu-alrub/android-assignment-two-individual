package com.abualrub.assignmenttwoindividualv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.abualrub.assignmenttwoindividualv2.domain.Gender;
import com.abualrub.assignmenttwoindividualv2.domain.User;
import com.abualrub.assignmenttwoindividualv2.fragments.Education;
import com.abualrub.assignmenttwoindividualv2.fragments.Experience;
import com.abualrub.assignmenttwoindividualv2.fragments.PersonalInformation;
import com.abualrub.assignmenttwoindividualv2.util.Tags;
import com.abualrub.assignmenttwoindividualv2.util.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottomNavigation);
        initBottomNavigation();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new PersonalInformation()).commit();
    }

    private void initBottomNavigation(){
        bottomNavigation.setOnNavigationItemSelectedListener(item ->{
            Fragment selectedFragment = null;
            switch(item.getItemId()){
                case R.id.navPersonal:
                    selectedFragment = new PersonalInformation();
                    break;
                case R.id.navWorkExperience:
                    selectedFragment = new Experience();
                    break;
                case R.id.navEducation:
                    selectedFragment = new Education();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,selectedFragment).commit();
            return true;
        });
    }
}