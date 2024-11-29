package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class jobseeker_dashboard extends AppCompatActivity {
    RecyclerView recyclerView;
    job_adapter jobAdapter;
    List<job_post_model>jobList;
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_jobseeker_dashboard);
        recyclerView=findViewById(R.id.recycler_view1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList=new ArrayList<>();
        fetchJobPosts();

        jobAdapter=new job_adapter(jobList);
        recyclerView.setAdapter(jobAdapter);

        updateJobSeekerProfile();

        drawerLayout=findViewById(R.id.main);
        navigationView=findViewById(R.id.nav);

        setUpDrawer();
    }
    private void fetchJobPosts(){
        FirebaseFirestore db= FirebaseFirestore.getInstance();
        db.collection("users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot documentSnapshot: queryDocumentSnapshots){
                    job_post_model job=documentSnapshot.toObject(job_post_model.class);
                    jobList.add(job);
                }
                jobAdapter.notifyDataSetChanged();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(jobseeker_dashboard.this, "Error Fetching Job Posts", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateJobSeekerProfile(){
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        TextView jobseekerName= findViewById(R.id.jobseeker_name);
        TextView jobseekerEmail= findViewById(R.id.jobseeker_email);
        TextView jobseekerPhone= findViewById(R.id.jobseeker_phone);
        if (user != null){
            jobseekerName.setText(user.getDisplayName() !=null ? user.getDisplayName(): "Job Seeker");
            jobseekerEmail.setText(user.getEmail());
            jobseekerPhone.setText("Phone: " + (user.getPhoneNumber() != null ? user.getPhoneNumber() : "Not Provided"));
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
    private void setUpDrawer(){
        findViewById(R.id.nav_img1).setOnClickListener(v -> drawerLayout.openDrawer(navigationView));

        navigationView.setNavigationItemSelectedListener(item -> {
            return false;
        });

        View headerView = navigationView.getHeaderView(0);
        Button logoutButton = headerView.findViewById(R.id.logout);
        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(jobseeker_dashboard.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}