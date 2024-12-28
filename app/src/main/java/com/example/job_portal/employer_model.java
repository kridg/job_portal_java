package com.example.job_portal;

public class employer_model {
    private String editname;
    private String editcname;
    private String editphone;
    private String editcountry;
    private String editcity;
//    private String editposition;
    employer_model(){

    }

    private employer_model(String name, String cname,String phone,String country,String city,String position){
        this.editname=name;
        this.editcname=cname;
        this.editphone=phone;
        this.editcountry=country;
        this.editcity=city;
//        this.editposition=position;
    }

    public String getEditname() {
        return editname;
    }

    public void setEditname(String editname) {
        this.editname = editname;
    }

    public String getEditcname() {
        return editcname;
    }

    public void setEditcname(String editcname) {
        this.editcname = editcname;
    }

    public String getEditphone() {
        return editphone;
    }

    public void setEditphone(String editphone) {
        this.editphone = editphone;
    }

    public String getEditcountry() {
        return editcountry;
    }

    public void setEditcountry(String editcountry) {
        this.editcountry = editcountry;
    }

    public String getEditcity() {
        return editcity;
    }

    public void setEditcity(String editcity) {
        this.editcity = editcity;
    }

}
