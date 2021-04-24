package com.abualrub.assignmenttwoindividualv2.domain;

import java.util.ArrayList;

public class User {
    private boolean doesHaveEdu;
    private boolean doesHaveWorkEx;
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String whatMakesHappy;
    private ArrayList<String> education;
    private ArrayList<String> workExperience;

    public User(){

    }

    public User(boolean doesHaveEdu,
                boolean doesHaveWorkEx,
                String firstName,
                String lastName,
                String email,
                String gender,
                String whatMakesHappy,
                ArrayList<String> education,
                ArrayList<String> workExperience) {
        this.doesHaveEdu = doesHaveEdu;
        this.doesHaveWorkEx = doesHaveWorkEx;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.whatMakesHappy = whatMakesHappy;
        this.education = education;
        this.workExperience = workExperience;
    }

    public String workExperienceToString(String workPlace, String startYear, String endYear, String role){
        return "Worked at "+workPlace+" as "+role+" from "+startYear+" till "+endYear;
    }

    public String educationToString(String schoolName,String major, String startYear, String endYear, String level){
        return "Studied "+major+" ("+level+") at "+schoolName + " from "+startYear+" till "+endYear;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail(){return email;}

    public void setEmail(String email){this.email = email;}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getEducation() {
        return education;
    }

    public void setEducation(ArrayList<String> education) {
        this.education = education;
    }

    public ArrayList<String> getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(ArrayList<String> workExperience) {
        this.workExperience = workExperience;
    }

    public String getWhatMakesHappy(){
        return whatMakesHappy;
    }

    public void setWhatMakesHappy(String whatMakesHappy){
        this.whatMakesHappy = whatMakesHappy;
    }

    public boolean doesHaveEdu() {
        return doesHaveEdu;
    }

    public void setDoesHaveEdu(boolean doesHaveEdu) {
        this.doesHaveEdu = doesHaveEdu;
    }

    public boolean doesHaveWorkEx() {
        return doesHaveWorkEx;
    }

    public void setDoesHaveWorkEx(boolean doesHaveWorkEx) {
        this.doesHaveWorkEx = doesHaveWorkEx;
    }
}
