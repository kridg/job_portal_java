package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class signup_page extends AppCompatActivity {

    Button signup;
    EditText email, password, Cpassword;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup_page);

        firebaseAuth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        signup=findViewById(R.id.btn_signup);
        email=findViewById(R.id.txt_email);
        password=findViewById(R.id.txt_password);
        Cpassword=findViewById(R.id.txt_Cpassword);

        //click on signup button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString().trim();
                String Password=password.getText().toString().trim();
                String CPassword=Cpassword.getText().toString().trim();

                if (Email.isEmpty()){
                    Toast.makeText(signup_page.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                }
                else if (Password.isEmpty()){
                    Toast.makeText(signup_page.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else if (CPassword.isEmpty()){
                    Toast.makeText(signup_page.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                } else if (Password.length()<6) {
                    Toast.makeText(signup_page.this, "Password must be greater than 6 characters", Toast.LENGTH_SHORT).show();
                } else if (CPassword.equals(Password)) {
                    createUserAccount(Email, Password);
                }
            }
        });
    }
    private void createUserAccount(String email, String password){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user=firebaseAuth.getCurrentUser();
                    if (user != null){
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("role", "employer");

                        db.collection("users").document(user.getUid()).set(userData).addOnSuccessListener(aVoid -> {
                            Log.d("Firestore", "User data saved successfully with employer role.");
                            goToDataEntryForm();
                        }).addOnFailureListener(e -> {
                            Log.e("FirestoreError", "Error saving user data: " + e.getMessage());
                            Toast.makeText(signup_page.this, "Failed to Save User", Toast.LENGTH_SHORT).show();
                        });
                    } else {
                        Toast.makeText(signup_page.this, "Sign Up Failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(signup_page.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void goToDataEntryForm() {
        Intent intent = new Intent(signup_page.this, employer_page.class);
        startActivity(intent);
        finish();
    }
}
