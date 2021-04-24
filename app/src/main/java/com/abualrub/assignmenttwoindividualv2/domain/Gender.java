package com.abualrub.assignmenttwoindividualv2.domain;

public enum Gender {
    MALE("MALE"), FEMALE("FEMALE");

    public final String CONSTANT;
    private Gender(String s){
        this.CONSTANT = s;
    }
}
