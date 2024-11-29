package com.example.job_portal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class job_adapter extends RecyclerView.Adapter<job_adapter.JobViewHolder>{
    private List<job_post_model> jobPosts;
    private Context context;

    public job_adapter(List<job_post_model>jobPosts){
        this.jobPosts=jobPosts;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item_display, parent, false);
        context=parent.getContext();
        return new JobViewHolder(view);
    }
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position){
        job_post_model job = jobPosts.get(position);
        holder.jobTitle.setText(job.getJobTitle());
        holder.jobCompany.setText(job.getJobCompany());
        holder.jobDescription.setText(job.getJobDescription());
        holder.jobLocation.setText(job.getJobLocation());
        holder.jobSalary.setText(job.getJobSalary());

        holder.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, job_apply.class);
                intent.putExtra("jobTitle", job.getJobTitle());
                intent.putExtra("jobCompany", job.getJobCompany());
                intent.putExtra("jobDescription", job.getJobDescription());
                intent.putExtra("jobLocation",job.getJobLocation());
                intent.putExtra("jobSalary",job.getJobSalary());
                context.startActivity(intent);
            }
        });
    }

    public int getItemCount(){
        return jobPosts.size();
    }

    public void updateJobs(List<job_post_model> newJobs){
        jobPosts.clear();
        jobPosts.addAll(newJobs);
        notifyDataSetChanged();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder{
        TextView jobTitle, jobCompany, jobDescription, jobLocation, jobSalary;
        Button btnApply;

        public JobViewHolder(View itemView){
            super(itemView);
            jobTitle=itemView.findViewById(R.id.tvJobTitle);
            jobCompany=itemView.findViewById(R.id.tvCompanyName);
            jobDescription=itemView.findViewById(R.id.tvJobDescription);
            jobLocation=itemView.findViewById(R.id.tvLocation);
            jobSalary=itemView.findViewById(R.id.tvSalary);
            btnApply=itemView.findViewById(R.id.btnApply);
        }
    }
}
