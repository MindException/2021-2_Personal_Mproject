package com.cookandroid.personal_mobile_project;

public class PhoneException extends Exception {
    //전화번호 11자리가 아닐 경우 발생하는 예외

    PhoneException(String msg){

        super(msg);

    }
}
