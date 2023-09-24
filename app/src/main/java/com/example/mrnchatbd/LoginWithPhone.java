package com.example.mrnchatbd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppLocalesMetadataHolderService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

import java.util.HashMap;
import java.util.Map;

public class LoginWithPhone extends AppCompatActivity {
    CountryCodePicker countryCodePicker;
    EditText mobileInput;
    Button sendOtpBtn;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_phone);
        // id define
        countryCodePicker = findViewById(R.id.loginCountryCode);
        mobileInput = findViewById(R.id.loginMobileInput);
        sendOtpBtn = findViewById(R.id.sendOtpBtn);
        progressBar = findViewById(R.id.progressPhone);


        countryCodePicker.registerCarrierNumberEditText(mobileInput);
        sendOtpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                if (!countryCodePicker.isValidFullNumber()){
                    mobileInput.setError("Phone number is not Valid");
                    progressBar.setVisibility(View.GONE);
                    return;
                }

                Intent intent = new Intent(LoginWithPhone.this, LoginOtp.class);
                intent.putExtra("phone",countryCodePicker.getFullNumberWithPlus());

                startActivity(intent);

            }
        });
    }
}