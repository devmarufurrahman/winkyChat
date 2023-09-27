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

    public static class UserModel {
        private String phoneNumber;
        private String userName;
        private String userId;
        private com.google.firebase.Timestamp timestampCreate;


        public UserModel(){

        }

        public UserModel(String phoneNumber, String userName, com.google.firebase.Timestamp timestampCreate,String userId) {
            this.phoneNumber = phoneNumber;
            this.userName = userName;
            this.timestampCreate = timestampCreate;
            this.userId = userId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public com.google.firebase.Timestamp getTimestampCreate() {
            return timestampCreate;
        }

        public void setTimestampCreate(com.google.firebase.Timestamp timestampCreate) {
            this.timestampCreate = timestampCreate;
        }
    }
}