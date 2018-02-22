package com.csi.contactmanager.resource;

import com.csi.contactmanager.model.Contact;
import com.csi.contactmanager.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;

/**
 * A service to handle user input from console. Easily swappable to any kind of input
 */
@Service
public class PromptService {

    @Autowired
    ValidatorService validatorService;

    private Scanner reader = new Scanner(System.in);

    public Contact readContactData() {
        System.out.println("Enter contact name:");
        String enteredName = reader.next();
        System.out.println("Enter contact phone number:");
        String enteredPhone = reader.next();
        System.out.println("Enter contact email:");
        String enteredEmail = reader.next();

        /**
         * Using the name as the ID for h2 db
         * When entering an invalid name as a new user, it will get translated to the required 'XXX'
         * If there are more than one invalid names, it will prompt for permission to overwrite each time         *
         */
        return new Contact(validatorService.validateName(enteredName),
                validatorService.validatePhone(enteredPhone),
                validatorService.validateEmail(enteredEmail)
        );
    }

    public String readContactName() {
        System.out.println("Enter contact name:");
        return reader.next();
    }

    public String readFileName() {
        System.out.println("Enter file name:");
        return reader.next();
    }

    public boolean overWritePermission() {
        System.out.println("Would you like to overwrite? Y/N");
        return reader.next().toLowerCase().equals("y");
    }
}
