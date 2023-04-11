package com.maker.crm.service.contacts;

import com.maker.crm.model.Contacts;

import java.util.List;

public interface ContactsService {
    public List<Contacts> queryContactsByName(String name)throws Exception;
}
