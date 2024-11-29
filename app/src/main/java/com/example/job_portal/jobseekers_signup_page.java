package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class jobseekers_signup_page extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText email, password, Cpassword, fullname;
    FirebaseFirestore db;
    Button signup;
    TextView signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jobseekers_signup_page);
        firebaseAuth = FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();

        signup=findViewById(R.id.seeker_signup);
        email=findViewById(R.id.seeker_email);
        password=findViewById(R.id.seeker_password);
        Cpassword=findViewById(R.id.seeker_Cpassword);
        fullname=findViewById(R.id.seeker_name);
        signin=findViewById(R.id.seeker_signin1);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), jobseekers_login_page.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(v -> {
            String Email=email.getText().toString().trim();
            String Password=password.getText().toString().trim();
            String CPassword=Cpassword.getText().toString().trim();
            String FullName=fullname.getText().toString().trim();

            if (Email.isEmpty() || Password.isEmpty() || CPassword.isEmpty()){
                Toast.makeText(jobseekers_signup_page.this, "The fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                if (Password.equals(CPassword)){
                createUser(Email, Password, FullName);
                } else {
                    Toast.makeText(jobseekers_signup_page.this, "Password do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void createUser(String Email, String Password, String FullName){
        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String userId = firebaseAuth.getCurrentUser().getUid();
                Map<String, Object> userMap= new HashMap<>();
                userMap.put("fullName", FullName);
                userMap.put("email", Email);
                userMap.put("role", "jobseeker");

                db.collection("users").document(userId).set(userMap)
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(jobseekers_signup_page.this, "Signup Complete", Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(getApplicationContext(),employee_page.class);
                            intent.putExtra("role","jobseeker");
                            startActivity(intent);
                            finish();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(jobseekers_signup_page.this, "Failed to Save User Data", Toast.LENGTH_SHORT).show();
                        });
            } else {
                Toast.makeText(jobseekers_signup_page.this, "Sign Up Failed:" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}