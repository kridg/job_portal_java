package com.example.job_portal;

public class employee_model {
    private String editname1;
    private String editphone1;
    private String editcountry1;
    private String editcity1;
    employee_model(){

    }
    private employee_model(String name1,String phone1,String country1,String city1) {
        this.editname1 = name1;
        this.editphone1 = phone1;
        this.editcountry1 = country1;
        this.editcity1 = city1;
    }

    public String getEditname1() {
        return editname1;
    }

    public void setEditname1(String editname1) {
        this.editname1 = editname1;
    }

    public String getEditphone1() {
        return editphone1;
    }

    public void setEditphone1(String editphone1) {
        this.editphone1 = editphone1;
    }

    public String getEditcountry1() {
        return editcountry1;
    }

    public void setEditcountry1(String editcountry1) {
        this.editcountry1 = editcountry1;
    }

    public String getEditcity1() {
        return editcity1;
    }

    public void setEditcity1(String editcity1) {
        this.editcity1 = editcity1;
    }
}
