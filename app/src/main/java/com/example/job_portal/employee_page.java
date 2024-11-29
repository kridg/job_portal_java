package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class employee_page extends AppCompatActivity {
    public EditText editname1,editphone1,editcountry1,editcity1;
    public FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    public Button enter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_page);
        editname1=findViewById(R.id.employee_name);
        editcountry1=findViewById(R.id.country1);
        editphone1=findViewById(R.id.phone1);
        editcity1=findViewById(R.id.city_name1);
        enter1=findViewById(R.id.enter_employee);

        enter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ename1=editname1.getText().toString();
                String ecity1=editcity1.getText().toString();
                String ecountry1=editcountry1.getText().toString();
                String ephone1=editphone1.getText().toString();
                if (ename1.isEmpty() || ecity1.isEmpty() || ecountry1.isEmpty() || ephone1.isEmpty()) {
                    Toast.makeText(employee_page.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                String userId= firebaseAuth.getCurrentUser().getUid();
                String role=getIntent().getStringExtra("role");

                Map<String,Object> employeeData=new HashMap<>();
                employeeData.put("employee name",ename1);
                employeeData.put("Phone Number",ephone1);
                employeeData.put("preferred city",ecity1);
                employeeData.put("country",ecountry1);
                employeeData.put("role",role);

                db.collection("users").document(userId).set(employeeData).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(employee_page.this, "Data has been added successfully!", Toast.LENGTH_SHORT).show();

                        Intent intent= new Intent(employee_page.this, jobseeker_dashboard.class);
                        intent.putExtra("role","jobseeker");
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(employee_page.this, "Data couldn't be saved, please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}