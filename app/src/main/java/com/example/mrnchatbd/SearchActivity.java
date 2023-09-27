package com.example.mrnchatbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class SearchActivity extends AppCompatActivity {
    ImageButton backBtn, searchUserBtn;
    RecyclerView searchUserRecycler;
    EditText searchInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //id define
        backBtn = findViewById(R.id.backBtn);
        searchUserBtn = findViewById(R.id.searchUserBtn);
        searchUserRecycler = findViewById(R.id.searchUserRecycler);
        searchInput = findViewById(R.id.searchUserInput);

        searchInput.requestFocus();
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchTerm = searchInput.getText().toString();
                if (searchTerm.isEmpty() || searchTerm.length()<3){
                    searchInput.setError("Invalid Username");
                    return;
                }

                setupSearchRecyclerView(searchTerm);
            }
        });
    }

    private void setupSearchRecyclerView(String searchTerm) {

    }
}