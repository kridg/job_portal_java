package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.firestore.FirebaseFirestore;

public class job_posting_page extends AppCompatActivity {
    EditText jobTitle, jobCompany, jobDescription, jobLocation, jobSalary;
    Button submitPost;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_posting_page);
        db=FirebaseFirestore.getInstance();

        jobTitle=findViewById(R.id.jobTitle);
        jobCompany=findViewById(R.id.jobCompany);
        jobDescription=findViewById(R.id.jobDescription);
        jobLocation=findViewById(R.id.jobLocation);
        jobSalary=findViewById(R.id.jobSalary);
        submitPost=findViewById(R.id.submitPost);

        submitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=jobTitle.getText().toString();
                String company=jobCompany.getText().toString();
                String description=jobDescription.getText().toString();
                String location=jobLocation.getText().toString();
                String salary=jobSalary.getText().toString();

                if (!title.isEmpty() && !company.isEmpty() && !description.isEmpty() && !location.isEmpty() && !salary.isEmpty()){
                    job_post_model jobPost=new job_post_model(title, company, description, location, salary);

                    db.collection("jobs").add(jobPost).addOnSuccessListener(documentReference -> {
                        Toast.makeText(job_posting_page.this, "Job Post Published", Toast.LENGTH_SHORT).show();

                        Intent resultIntent= new Intent();
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }). addOnFailureListener(e -> {
                        Toast.makeText(job_posting_page.this, "Error", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    Toast.makeText(job_posting_page.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}