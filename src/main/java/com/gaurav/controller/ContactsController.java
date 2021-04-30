package com.gaurav.controller;

import com.gaurav.entity.ContactsEntity;
import com.gaurav.service.ContactService;
import com.gaurav.util.ContactsEntryResponse;
import com.gaurav.util.PhoneTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping(produces = "application/json")
public class ContactsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContactsController.class);
    private ContactService contactService;

    @Autowired
    public ContactsController(ContactService contactService){
        this.contactService = contactService;
    }

    @GetMapping(value = "/contacts")
    public ResponseEntity getContacts()    {
        try{
            List<ContactsEntity> contactsEntitiesList = this.contactService.getAllContacts();
            if(contactsEntitiesList.isEmpty())
                return ResponseEntity.ok().body(ContactsEntryResponse.builder().status(HttpStatus.OK.value()).message("Currently there are no contacts stored.").build());
            return ResponseEntity.ok().body(contactsEntitiesList);
        }catch(Exception e){
            String msg = "Error retrieving contacts: " + e.getMessage();
            LOGGER.error(msg , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ContactsEntryResponse.builder().error(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(msg).build());
        }
    }

    @PostMapping(value = "/contacts", consumes = "application/json")
    public ResponseEntity createContact(@Valid @RequestBody ContactsEntity contacts){
        try{
            ContactsEntity res = this.contactService.addContacts(contacts);
            return ResponseEntity.ok().body(res);
        }catch(Exception e){
            String msg = "Error creating contact: " + e.getMessage();
            LOGGER.error(msg , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ContactsEntryResponse.builder().error(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(msg).build());
        }
    }

    @PutMapping(value = "/contacts/{id}", consumes = "application/json")
    public ResponseEntity updateContact(@PathVariable(value = "id") Long id, @Valid @RequestBody ContactsEntity contacts){
        try{
            ContactsEntity res = this.contactService.updateContactById(id, contacts);
            return ResponseEntity.ok().body(res);
        }catch(Exception e){
            String msg = "Error updating contact: " + e.getMessage();
            LOGGER.error(msg , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ContactsEntryResponse.builder().error(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(msg).build());
        }
    }

    @GetMapping(value = "/contacts/{id}")
    public ResponseEntity getContact(@PathVariable(value = "id") Long id){
        try{
            ContactsEntity res = this.contactService.findById(id);
            return ResponseEntity.ok().body(res);
        }catch(Exception e){
            String msg = "Error getting contact: " + e.getMessage();
            LOGGER.error(msg , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ContactsEntryResponse.builder().error(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(msg).build());
        }
    }

    @DeleteMapping(value = "/contacts/{id}")
    public ResponseEntity deleteContact(@PathVariable(value = "id") Long id){
        try{
            this.contactService.deleteContactById(id);
            return ResponseEntity.ok().body(ContactsEntryResponse.builder().status(HttpStatus.OK.value()).message("Contact deleted successfully.").build());
        }catch(Exception e){
            String msg = "Error deleting contact: " + e.getMessage();
            LOGGER.error(msg , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ContactsEntryResponse.builder().error(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(msg).build());
        }
    }

    @GetMapping(value = "/contacts/call-list")
    public ResponseEntity getCallListWithHomePhone(){
        try{
            return ResponseEntity.ok().body(this.contactService.getCallListWithPhoneType(PhoneTypeEnum.HOME.toValue()));
        }catch(Exception e){
            String msg = "Error getting call list: " + e.getMessage();
            LOGGER.error(msg , e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ContactsEntryResponse.builder().error(HttpStatus.INTERNAL_SERVER_ERROR).status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(msg).build());
        }
    }
}
