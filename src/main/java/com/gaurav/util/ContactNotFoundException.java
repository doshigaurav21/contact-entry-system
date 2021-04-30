package com.gaurav.util;

public class ContactNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 4206601185736058668L;

    public ContactNotFoundException(String errorMsg){
        super(errorMsg);
    }
}
