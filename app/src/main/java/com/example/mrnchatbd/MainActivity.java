package com.example.mrnchatbd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;
    ChatFragment chatFragment;
    ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        searchButton = findViewById(R.id.searchBtn);

        chatFragment = new ChatFragment();
        profileFragment = new ProfileFragment();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menuChat){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,chatFragment).commit();
                }
                if (item.getItemId() == R.id.menuProfile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFrameLayout,profileFragment).commit();
                }

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.menuChat);

    }
}