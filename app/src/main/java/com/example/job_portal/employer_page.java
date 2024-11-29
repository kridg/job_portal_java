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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class employer_page extends AppCompatActivity {
    public EditText editname,editcname,editphone,editcountry,editcity;
    public Button enter;
    public FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employer_page);
        editname=findViewById(R.id.employername);
        editcname=findViewById(R.id.companyname);
        editphone=findViewById(R.id.phone);
        editcountry=findViewById(R.id.country);
        editcity=findViewById(R.id.cityname);
        enter=findViewById(R.id.enter_employer);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ename=editname.getText().toString();
                String ecity=editcity.getText().toString();
                String ecountry=editcountry.getText().toString();
                String ephone=editphone.getText().toString();
                String ecname=editcname.getText().toString();
                if (ename.isEmpty() || ecity.isEmpty() || ecountry.isEmpty() || ephone.isEmpty()) {
                    Toast.makeText(employer_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                String userId= firebaseAuth.getCurrentUser().getUid();
                String role=getIntent().getStringExtra("role");

                Map<String,Object> employerData=new HashMap<>();
                employerData.put("employer name",ename);
                employerData.put("city",ecity);
                employerData.put("country",ecountry);
                employerData.put("company name",ecname);
                employerData.put("phone",ephone);
                employerData.put("role",role);

                db.collection("users").document(userId).set(employerData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(employer_page.this, "Data has been added successfully.", Toast.LENGTH_SHORT).show();
                        Intent intent =new Intent(employer_page.this, employer_dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(employer_page.this, "Couldn't save the data, please try again!!!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}