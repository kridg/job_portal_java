package com.example.job_portal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//import android.widget.SearchView;
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
import androidx.appcompat.widget.SearchView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class employer_dashboard extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button addPost;
    RecyclerView recyclerView;
    job_adapter adapter;
    FirebaseFirestore db;
    List<job_post_model> jobList;
    List<job_post_model>filteredJobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employer_dashboard);

        drawerLayout=findViewById(R.id.main);
        navigationView=findViewById(R.id.nav);
        addPost=findViewById(R.id.btn_add_post);
        recyclerView=findViewById(R.id.recycler_view1);
        db=FirebaseFirestore.getInstance();
        jobList= new ArrayList<>();
        filteredJobList= new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new job_adapter(filteredJobList);
        recyclerView.setAdapter(adapter);

        loadJobPosts();

        SearchView searchView=findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterJobs(newText);
                return true;
            }
        });

        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(employer_dashboard.this, job_posting_page.class);
                startActivityForResult(intent, 100);
            }
        });
        setupDrawer();
    }
    protected  void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK){
            loadJobPosts();
        }
    }
    private void setupDrawer(){
        findViewById(R.id.nav_img1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(navigationView);
            }
        });

        navigationView.setNavigationItemSelectedListener(item -> {
            return false;
        });

        View headerView=navigationView.getHeaderView(0);
        Button logoutButton= headerView.findViewById(R.id.logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(employer_dashboard.this, "Logged Out Successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
        TextView employerName=headerView.findViewById(R.id.employer_name);
        TextView companyName=headerView.findViewById(R.id.company_name);
        TextView employerEmail=headerView.findViewById(R.id.employer_email);
        TextView employerPhone=headerView.findViewById(R.id.employer_phone);

        db.collection("users").document(FirebaseAuth.getInstance().
                getCurrentUser().getUid()).get().addOnSuccessListener
                (new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    employerName.setText(documentSnapshot.getString("employer name"));
                    companyName.setText(documentSnapshot.getString("company name"));
                    employerEmail.setText(documentSnapshot.getString("email"));
                    employerPhone.setText("Phone:"+ documentSnapshot.getString("phone"));
                } else {
                    Toast.makeText(employer_dashboard.this, "User Data Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(employer_dashboard.this, "Couldn't fetch User Data", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void loadJobPosts(){
        db.collection("jobs").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                jobList.clear();
                for (QueryDocumentSnapshot document: task.getResult()){
                    job_post_model job=document.toObject(job_post_model.class);
                    jobList.add(job);
                }
                filterJobs("");
            } else {
                Toast.makeText(employer_dashboard.this, "Failed to load job posts. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void filterJobs(String query){
        filteredJobList.clear();
        if (query.isEmpty()){
            filteredJobList.addAll(jobList);
        } else{
            for (job_post_model job: jobList){
                if (job.getJobTitle().toLowerCase().contains(query.toLowerCase())){
                    filteredJobList.add(job);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}