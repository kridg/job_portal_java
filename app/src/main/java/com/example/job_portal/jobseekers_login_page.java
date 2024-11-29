package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class jobseekers_login_page extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    Button signin, signup;
    EditText email, password;
    TextView forgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jobseekers_login_page);
        firebaseAuth=FirebaseAuth.getInstance();

        signin=findViewById(R.id.seeker_signin);
        signup=findViewById(R.id.seeker_signup);
        email=findViewById(R.id.seeker_email);
        password=findViewById(R.id.seeker_password);
        forgotpassword=findViewById(R.id.seeker_forgotpassword);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), jobseekers_signup_page.class);
                startActivity(intent);
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), forgotpassword_page.class);
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email= email.getText().toString().trim();
                String Password= password.getText().toString().trim();

                if (Email.isEmpty()) {
                    Toast.makeText(jobseekers_login_page.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                } else if (Password.isEmpty()) {
                    Toast.makeText(jobseekers_login_page.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(Email, Password);
                }
            }
        });
    }
    private void loginUser(String Email, String Password){
        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(getApplicationContext(),jobseeker_dashboard.class));
                    finish();
                } else {
                    Toast.makeText(jobseekers_login_page.this, "Login Failed: "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}