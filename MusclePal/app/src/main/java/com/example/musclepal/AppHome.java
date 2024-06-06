package com.example.musclepal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.TimePicker;

import com.example.musclepal.databinding.ActivityAppHomeBinding;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

public class AppHome extends AppCompatActivity{//initialise fragments

    ActivityAppHomeBinding binding;
    final Fragment fragment1 = new timer();
    Fragment active = fragment1;
    final Fragment fragment2 = new discover();
    final Fragment fragment3 = new account();
    final Fragment fragment4 = new Description();
    final FragmentManager fm = getSupportFragmentManager();
    private SwitchMaterial themeSwitch;
    private View parentView;
    private userSettings settings;
    private ShareActionProvider shareActionProvider;
    public String value;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();//bundle for share button
        if (extras != null) {
            value = (extras.getString("key"))+" is telling you to come to the gym!";
        }else{
            value = "Come gym!";
        }
        getSupportActionBar().setTitle("Tell your friends to go gym->");
        getSupportActionBar().hide();//hide action view

        binding = ActivityAppHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        themeSwitch = findViewById(R.id.themeSwitch);
        parentView = findViewById(R.id.parentView);

        fm.beginTransaction().add(R.id.frameLayout, fragment4, "4").hide(fragment4).commit();//hide fragments that arent in use
        fm.beginTransaction().add(R.id.frameLayout, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.frameLayout, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.frameLayout,fragment1, "1").commit();//show first fragment
        if (fragment4.isVisible()){//only show actionbar where needed
            getSupportActionBar().show();
        }

        binding.bottomNav.setOnItemSelectedListener(item -> {

            switch(item.getItemId()){//switch case for switching fragment with bottom nav bar



                case R.id.timer:
                    themeSwitch.setVisibility(View.GONE);
                    if(fragment4.isVisible()){
                        fm.beginTransaction().hide(fragment4).show(fragment1).commit();
                    }else {
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                    }
                    active = fragment1;
                    break;
                case R.id.discover:
                    themeSwitch.setVisibility(View.GONE);
                    if(fragment4.isVisible()){
                        fm.beginTransaction().hide(fragment4).show(fragment2).commit();
                    }else {
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                    }
                    active = fragment2;
                    break;
                case R.id.account:
                    themeSwitch.setVisibility(View.VISIBLE);
                    if(fragment4.isVisible()){
                        fm.beginTransaction().hide(fragment4).show(fragment3).commit();
                    }else {
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                    }
                    active = fragment3;
                    break;

            }



            return true;
        });

        settings = (userSettings) getApplication();


        loadSharedPreferences();//for theme
        initSwitchListener();
        if (savedInstanceState != null) {
            return;
        }
    }




    @Override
    public boolean onCreateOptionsMenu (Menu menu) {//add share button menu
        getMenuInflater().inflate(R.menu.share_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent(value);
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareActionIntent(String your_message){//set share button text
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, your_message);
        shareActionProvider.setShareIntent (intent);
    }

    private void loadSharedPreferences() {//loads theme
        SharedPreferences sharedPreferences = getSharedPreferences(userSettings.PREFERENCES, MODE_PRIVATE);
        String theme = sharedPreferences.getString(userSettings.CUSTOM_THEME, userSettings.LIGHT_THEME);
        settings.setCustomTheme(theme);
        updateView();

    }
    private void updateView() {//change app theme on toggle
        final int black = ContextCompat.getColor(this, R.color.black);
        final int white = ContextCompat.getColor(this, R.color.white);

        if(settings.getCustomTheme().equals(userSettings.DARK_THEME))
        {
            parentView.setBackgroundColor(black);
            themeSwitch.setChecked(true);
        }else
        {
            parentView.setBackgroundColor(white);
            themeSwitch.setChecked(false);
        }
    }



    private void initSwitchListener() {//switch listener for theme
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked)
            {
                if(checked)
                    settings.setCustomTheme(userSettings.DARK_THEME);
                else
                    settings.setCustomTheme(userSettings.LIGHT_THEME);

                SharedPreferences.Editor editor = getSharedPreferences(userSettings.PREFERENCES, MODE_PRIVATE).edit();
                editor.putString(userSettings.CUSTOM_THEME, settings.getCustomTheme());
                editor.apply();
                updateView();
            }
        });
    }

}