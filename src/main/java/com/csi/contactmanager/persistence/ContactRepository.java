package com.csi.contactmanager.persistence;

import com.csi.contactmanager.model.Contact;
import org.springframework.data.repository.CrudRepository;

/**
 * Simple in-memory database, this interface is the base repo, handled by Spring
 */
public interface ContactRepository extends CrudRepository<Contact, String> {

    Contact findByName(String name);

    void deleteByName(String name);


}
