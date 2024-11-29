package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button signup, signin;
    Switch roleSwitch;
    TextView roleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        signup=findViewById(R.id.btn_signup);
        signin=findViewById(R.id.btn_signin);

        roleSwitch=findViewById(R.id.role_switch);
        roleText=findViewById(R.id.role_text);

        roleText.setText(roleSwitch.isChecked()?"Employer":"Job Seeker");

        roleSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                roleText.setText("Employer");
            } else {
                roleText.setText("Job Seeker");
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (roleSwitch.isChecked()) {
                    intent = new Intent(getApplicationContext(), signup_page.class); // Employer Signup
                } else {
                    intent = new Intent(getApplicationContext(), jobseekers_signup_page.class); // Job Seeker Signup
                }
                startActivity(intent);
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2= new Intent(getApplicationContext(), login_page.class);
                startActivity(intent2);
            }
        });
    }
}