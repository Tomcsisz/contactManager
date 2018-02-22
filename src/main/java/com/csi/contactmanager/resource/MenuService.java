package com.csi.contactmanager.resource;

import com.csi.contactmanager.model.MainMenuAction;
import com.csi.contactmanager.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.InputMismatchException;
import java.util.Scanner;

@Service
public class MenuService {

    private Scanner reader = new Scanner(System.in);

    @Autowired
    PromptService promptService;

    public void start(ContactService contactService) {
        MainMenuAction action;
        do {
                action = readAction();
            switch (action) {
                case ADD_CONTACT:
                    contactService.addContact(promptService.readContactData());
                    break;
                case UPDATE_CONTACT:
                    contactService.updateContact(promptService.readContactData());
                    break;
                case DELETE_CONTACT:
                    contactService.deleteContact(promptService.readContactName());
                    break;
                case SEARCH_CONTACT:
                    contactService.searchContact(promptService.readContactName());
                    break;
                case LIST_CONTACTS:
                    contactService.printContacts();
                    break;
                case QUIT:
                    System.out.println("Exiting");
                    //Added print to exit, it was a requirement in the task
                    contactService.printContacts();
                    break;
                case UNKNOWN_COMMAND:
                default:
                    System.out.println("Invalid selection. ");
                    break;
            }
        }while (action != MainMenuAction.QUIT);
    }

    private MainMenuAction readAction() {

        System.out.println("\n1. Enter a new contact" + "\n"
                + "2. Update contact" + "\n"
                + "3. Delete contact" + "\n"
                + "4. Search contact" + "\n"
                + "5. Print the contact list" + "\n"
                + "6. Exit"+ "\n" );

        int action;
        try {
            action = reader.nextInt();
        }
        catch (InputMismatchException ex){
            System.out.println("You must provide a number as an option!");
            // Reader will next on every non-integer provided, if the user enters a sentence it will print the menu for every word
            reader.next();
            //Set the action to unknown
            action = 7;
        }

        return MainMenuAction.valueOf(action);
    }
}
