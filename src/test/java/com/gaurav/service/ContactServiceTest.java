package com.gaurav.service;

import com.gaurav.entity.AddressEntity;
import com.gaurav.entity.ContactsEntity;
import com.gaurav.entity.PhonesEntity;
import com.gaurav.repository.ContactRepository;
import com.gaurav.util.ContactNotFoundException;
import com.gaurav.util.PhoneTypeEnum;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ContactServiceTest {

    @InjectMocks
    private ContactService contactServiceTest;

    @Mock
    private ContactRepository contactRepositoryMock;

    private ContactsEntity contactsEntity1;
    private ContactsEntity contactsEntity2;
    private List<ContactsEntity> resList;
    private List<Map<String, Object>> resMap;

    @Before
    public void setUp()  {
        MockitoAnnotations.initMocks(this);
        contactServiceTest = new ContactService(contactRepositoryMock);

        //Prepare data
        contactsEntity1 = new ContactsEntity();
        contactsEntity1.setId(8L);
        contactsEntity1.setEmail("abcd@gmail.com");
        contactsEntity1.setLast("lastName");
        contactsEntity1.setMiddle("middleName");
        contactsEntity1.setFirst("firstName");
        contactsEntity1.populateName();

        AddressEntity addressEntity1 = new AddressEntity(null, "123 Street", "city", "Michigan", "48335", contactsEntity1);
        PhonesEntity phonesEntity1 = new PhonesEntity(null, "313-256-7894", "home", contactsEntity1);
        contactsEntity1.setAddress(addressEntity1);
        contactsEntity1.setPhone(Collections.singletonList(phonesEntity1));

        contactsEntity2 = new ContactsEntity();
        contactsEntity2.setId(5L);
        contactsEntity2.setEmail("bcgds@gmail.com");
        contactsEntity2.setLast("last");
        contactsEntity2.setMiddle("middle");
        contactsEntity2.setFirst("first");
        contactsEntity2.populateName();

        AddressEntity addressEntity2 = new AddressEntity(null, "456 Street", "farm", "Michigan", "48335", contactsEntity2);
        PhonesEntity phonesEntity2 = new PhonesEntity(null, "313-256-7894", "mobile", contactsEntity2);
        contactsEntity2.setAddress(addressEntity2);
        contactsEntity2.setPhone(Collections.singletonList(phonesEntity2));

        resList = new ArrayList<>();
        resList.add(contactsEntity1);
        resList.add(contactsEntity2);

        resMap = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("name", contactsEntity1.getName());
        map.put("phone", phonesEntity1.getNumber());
        resMap.add(map);
    }

    @Test
    public void getCallListForMobile() {

        when(contactRepositoryMock.findAllBy(any())).thenReturn(resList);

        List<Map<String, Object>> result = contactServiceTest.getCallListWithPhoneType(PhoneTypeEnum.HOME.toValue());

        verify(contactRepositoryMock, times(1)).findAllBy(any());
        assertEquals(1, result.size());
        assertEquals(result.get(0).get("phone").toString(), resMap.get(0).get("phone").toString());
    }

    @Test
    public void getAllContacts() {
        when(contactRepositoryMock.findAll()).thenReturn(resList);

        List<ContactsEntity> result = contactServiceTest.getAllContacts();
        verify(contactRepositoryMock, times(1)).findAll();
        assertEquals(2, result.size());
    }

    @Test(expected= ContactNotFoundException.class)
    public void findRecordById(){
        when(contactRepositoryMock.findById(6L)).thenReturn(Optional.empty());
        contactServiceTest.findById(6L);
        verify(contactServiceTest, times(1)).findById(any());
    }

    @Test
    public void findRecordByIdExists(){
        when(contactRepositoryMock.findById(5L)).thenReturn(Optional.of(contactsEntity2));
        ContactsEntity res = contactServiceTest.findById(5L);
        verify(contactRepositoryMock, times(1)).findById(any());
        assertEquals(res.getFirst(), contactsEntity2.getFirst());
    }

    @Test
    public void updateContact(){
        when(contactRepositoryMock.findById(any())).thenReturn(Optional.of(contactsEntity1));
        contactsEntity2.setId(8L);
        when(contactRepositoryMock.save(any(ContactsEntity.class))).thenReturn(contactsEntity2);

        ContactsEntity res = contactServiceTest.updateContactById(8L, contactsEntity2);

        verify(contactRepositoryMock, times(1)).findById(any());
        verify(contactRepositoryMock, times(1)).save(any(ContactsEntity.class));
        assertEquals(contactsEntity1.getId(), res.getId());
    }
}