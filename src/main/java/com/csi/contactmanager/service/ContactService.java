package com.csi.contactmanager.service;

import com.csi.contactmanager.model.Contact;
import com.csi.contactmanager.persistence.ContactRepository;
import com.csi.contactmanager.resource.PromptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
/**
 * Main service class to handle crud operations
 */
public class ContactService {


    @Autowired
    PromptService promptService;

    @Autowired
    ContactRepository contactRepository;

    /**
     * A user can enter an already existing name, so we need permission to override
     * This use-case was not specified in the task
     *
     * @param contact
     */
    public void addContact(Contact contact) {

        if (contactRepository.findByName(contact.getName()) != null) {
            log.warn("Contact with name {} already exists!", contact.getName());
            if (promptService.overWritePermission()) {
                try {
                    contactRepository.save(contact);
                }
                catch (Exception ex){
                    log.error("Persistence error occured! message: {}", ex.getMessage());
                }
                log.info("Successfully overwritten contact {}!", contact.getName());
            } else
                System.out.println("Aborting");
        } else {
            contactRepository.save(contact);
        }
    }

    /**
     * Needs to be transactional because of the delete by name
     * @param contactName
     */
    @Transactional
    public void deleteContact(String contactName) {
        if (contactRepository.findByName(contactName) != null) {
            contactRepository.deleteByName(contactName);
            log.info("Successfully removed {} from the repository", contactName);
        } else {
            log.error("The contact {} doesn't exist in the repository", contactName);
        }
    }

    /**
     * Can be done with new contact, got separated so we can change the logic on the fly
     * @param contact
     */
    public void updateContact(Contact contact) {
        if (contactRepository.findByName(contact.getName()) !=null) {
            contactRepository.save(contact);
            log.info("Successfully updated contact {}", contact.getName());
        } else {
            log.error("The contact {} doesn't exist in the repository", contact.getName());
        }
    }

    /**
     * This wasn't a requirement, but useful functionality while manual testing bigger files
     * @param contactName
     */
    public void searchContact(String contactName) {
        if (contactRepository.findByName(contactName) !=null)
            log.info(contactRepository.findByName(contactName).toString());
        else
            log.error("The contact {} doesn't exist in the repository", contactName);
    }

    /**
     * Also wasn't a requirement, added for manual testing
     */
    public void printContacts() {
        contactRepository.findAll().forEach(System.out::println);
        log.info("Listed {} contacts", contactRepository.count());
    }
}
