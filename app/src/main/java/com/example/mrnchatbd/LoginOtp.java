package com.example.mrnchatbd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class LoginOtp extends AppCompatActivity {
    String phoneNumber;
    Long timeOutSec = 60L;
    String verificationCode;
    PhoneAuthProvider.ForceResendingToken resendingToken;
    EditText otpInput;
    Button nextBtn;
    TextView resendBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);
        // id define
        otpInput = findViewById(R.id.otpInput);
        nextBtn = findViewById(R.id.otpNextBtn);
        resendBtn = findViewById(R.id.resendOtp);
        progressBar = findViewById(R.id.progressOtp);

        progressBar.setVisibility(View.GONE);

        phoneNumber = getIntent().getExtras().getString("phone");
        Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();

        sendOtp(phoneNumber,false);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enteredOtp = otpInput.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCode,enteredOtp);
                signIn(credential);
                setInProgress(true);
            }
        });

        resendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendOtp(phoneNumber,true);
            }
        });
    }

    void sendOtp(String phoneNumber, boolean isResend){
        startResendTimer();
        setInProgress(true);
        PhoneAuthOptions.Builder builder =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(timeOutSec, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                signIn(phoneAuthCredential);
                                setInProgress(false);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(LoginOtp.this, "OTP verification failed", Toast.LENGTH_SHORT).show();
                                setInProgress(false);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                verificationCode = s;
                                resendingToken = forceResendingToken;
                                Toast.makeText(LoginOtp.this, "OTP send successfully", Toast.LENGTH_SHORT).show();
                                setInProgress(false);
                            }
                        });
        if (isResend){
            PhoneAuthProvider.verifyPhoneNumber(builder.setForceResendingToken(resendingToken).build());
        } else {
            PhoneAuthProvider.verifyPhoneNumber(builder.build());
        }
    }

    private void startResendTimer() {
        resendBtn.setEnabled(false);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                timeOutSec--;
                resendBtn.setText("Resend OTP in "+timeOutSec+" sec");
                if (timeOutSec <=0){
                    timeOutSec = 60L;
                    timer.cancel();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resendBtn.setEnabled(true);
                            resendBtn.setText("Click on resend!!!");
                        }
                    });
                }
            }
        },0,1000);
    }

    void setInProgress(boolean inProgress){
        if (inProgress){
            progressBar.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.GONE);

        }else {
            progressBar.setVisibility(View.GONE);
            nextBtn.setVisibility(View.VISIBLE);

        }
    }

    private void signIn(PhoneAuthCredential phoneAuthCredential) {
        // login part
        setInProgress(true);
        mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                setInProgress(false);
                if (task.isSuccessful()){
                    Intent intent = new Intent(LoginOtp.this, UserName.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginOtp.this, "OTP verification failed", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}