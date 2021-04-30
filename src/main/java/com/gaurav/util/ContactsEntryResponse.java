package com.gaurav.util;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ContactsEntryResponse {

    private HttpStatus error;
    private int status;
    private String message;
}
