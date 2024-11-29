package com.example.job_portal;

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

public class job_apply extends AppCompatActivity {
    private EditText etcoverLetter;
    private Button btnsubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_job_apply);

        etcoverLetter=findViewById(R.id.etCoverLetter);
        btnsubmit=findViewById(R.id.btnSubmitApplication);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coverletter=etcoverLetter.getText().toString().trim();

                if (coverletter.isEmpty()){
                    Toast.makeText(job_apply.this, "Please enter the details in the cover letter", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(job_apply.this, "Application Submitted, please wait for the response.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}