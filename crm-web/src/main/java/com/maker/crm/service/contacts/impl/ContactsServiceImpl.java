package com.maker.crm.service.contacts.impl;

import com.maker.crm.dao.contacts.ContactsMapper;
import com.maker.crm.model.Contacts;
import com.maker.crm.service.contacts.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
   @Autowired
    private ContactsMapper contactsMapper;
    @Override
    public List<Contacts> queryContactsByName(String name) throws Exception {
        return contactsMapper.selectContactsByName(name);
    }
}
