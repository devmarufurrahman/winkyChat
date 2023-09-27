package com.example.mrnchatbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.mrnchatbd.adapter.SearchRecyclerAdapter;
import com.example.mrnchatbd.utils.FirebaseUtils;
import com.example.mrnchatbd.utils.UserModel;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class SearchActivity extends AppCompatActivity {
    ImageButton backBtn, searchUserBtn;
    RecyclerView searchUserRecycler;
    EditText searchInput;
    SearchRecyclerAdapter recyclerAdapter;


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
        Query query = FirebaseUtils.allUserCollectionReference()
                .whereGreaterThanOrEqualTo("userName",searchTerm);

        FirestoreRecyclerOptions<UserModel> options = new FirestoreRecyclerOptions.Builder<UserModel>()
                .setQuery(query, UserModel.class).build();

        recyclerAdapter = new SearchRecyclerAdapter(options,getApplicationContext());
        searchUserRecycler.setLayoutManager(new LinearLayoutManager(this));
        searchUserRecycler.setAdapter(recyclerAdapter);
        recyclerAdapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (recyclerAdapter != null){
            recyclerAdapter.startListening();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (recyclerAdapter != null){
            recyclerAdapter.stopListening();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recyclerAdapter != null){
            recyclerAdapter.startListening();
        }
    }
}