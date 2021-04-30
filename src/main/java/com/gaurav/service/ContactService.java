package com.gaurav.service;

import com.gaurav.entity.AddressEntity;
import com.gaurav.entity.ContactsEntity;
import com.gaurav.entity.PhonesEntity;
import com.gaurav.repository.ContactRepository;
import com.gaurav.util.ContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    public List<Map<String, Object>> getCallListWithPhoneType(String phoneType){
        List<ContactsEntity> contactsEntityList = this.contactRepository.findAllBy(Sort.by("last").ascending().and(Sort.by("first").ascending()));

        return  contactsEntityList.stream().
                filter(k -> k.getPhone().stream().anyMatch(o -> o.getType().equalsIgnoreCase(phoneType)))
                .map(m -> {
                    Map<String, Object> temp = new HashMap<>();
                    temp.put("name", m.getName());
                    temp.put("phone", m.getPhone().get(0).getNumber());
                    return temp;})
                .collect(Collectors.toList());
    }

    public List<ContactsEntity> getAllContacts(){
        return this.contactRepository.findAll();
    }

    public ContactsEntity addContacts(ContactsEntity contactsEntity){
        return this.contactRepository.save(contactsEntity);
    }

    public ContactsEntity findById(Long id){
        Optional<ContactsEntity> contactsEntityOptional = this.contactRepository.findById(id);
        if(!contactsEntityOptional.isPresent()){
            throw new ContactNotFoundException("Contact with id " + id + " not found.");
        }
        return contactsEntityOptional.get();
    }

    public ContactsEntity updateContactById(Long id, ContactsEntity contactsEntity){
        ContactsEntity contactsEntityUpdate = findById(id);

        AddressEntity addressEntity = new AddressEntity(null, contactsEntity.getAddress().getStreet(), contactsEntity.getAddress().getCity(),
                contactsEntity.getAddress().getState() , contactsEntity.getAddress().getZip(), contactsEntityUpdate);


        List<PhonesEntity> phonesEntityList = contactsEntity.getPhone().stream()
                .map(k -> new PhonesEntity(null, k.getNumber(), k.getType(), contactsEntityUpdate))
                .collect(Collectors.toList());

        contactsEntityUpdate.setName(contactsEntity.getName());
        contactsEntityUpdate.setAddress(addressEntity);
        contactsEntityUpdate.setPhone(phonesEntityList);
        contactsEntityUpdate.setEmail(contactsEntity.getEmail());

        return this.contactRepository.save(contactsEntityUpdate);
    }

    public void deleteContactById(Long id){
        findById(id);
        this.contactRepository.deleteById(id);
    }
}




