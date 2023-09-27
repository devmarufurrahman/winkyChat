package com.example.mrnchatbd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mrnchatbd.utils.FirebaseUtils;
import com.example.mrnchatbd.utils.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

public class UserName extends AppCompatActivity {

    EditText userNameInput;
    Button letMeIn;
    ProgressBar progressBar;
    String phoneNumber;
    UserModel userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name);
        userNameInput = findViewById(R.id.userNameInput);
        letMeIn = findViewById(R.id.letMeInBtn);
        progressBar = findViewById(R.id.progressLetMe);

        phoneNumber = getIntent().getExtras().getString("phone");
        getUserName();

        letMeIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setUserName();
            }
        });
    }

    private void setUserName(){

        String username = userNameInput.getText().toString();
        if (username.isEmpty() || username.length()<3){
            userNameInput.setError("Is not valid user name");
            return;
        }
        setInProgress(true);
        if (userModel != null){
            userModel.setUserName(username);
        } else {
            userModel = new UserModel(phoneNumber,username, Timestamp.now(), FirebaseUtils.currentUserId());
        }
        FirebaseUtils.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                setInProgress(false);
                if (task.isSuccessful()){
                    Intent intent = new Intent(UserName.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(UserName.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getUserName() {
        setInProgress(true);
        FirebaseUtils.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                setInProgress(false);
                if (task.isSuccessful()){
                    userModel = task.getResult().toObject(UserModel.class);
                   if (userModel != null){
                       userNameInput.setText(userModel.getUserName());
                   }
                }
            }
        });
    }


    void setInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            letMeIn.setVisibility(View.GONE);

        }else {
            progressBar.setVisibility(View.GONE);
            letMeIn.setVisibility(View.VISIBLE);

        }
    }
}