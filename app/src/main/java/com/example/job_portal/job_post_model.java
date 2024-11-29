package com.example.job_portal;

public class job_post_model {
    private String jobTitle;
    private String jobCompany;
    private String jobDescription;
    private String jobLocation;
    private String jobSalary;

    public job_post_model() {
    }
    public job_post_model(String jobTitle, String jobCompany, String jobDescription, String jobLocation, String jobSalary){
        this.jobTitle=jobTitle;
        this.jobCompany=jobCompany;
        this.jobDescription=jobDescription;
        this.jobLocation=jobLocation;
        this.jobSalary=jobSalary;
    }

    public String getJobTitle(){
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobCompany() {
        return jobCompany;
    }
    public void setJobCompany(String jobCompany) {
        this.jobCompany = jobCompany;
    }
    public String getJobDescription() {
        return jobDescription;
    }public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
    public String getJobLocation() {
        return jobLocation;
    }
    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }
    public String getJobSalary() {
        return jobSalary;
    }
    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }
}
