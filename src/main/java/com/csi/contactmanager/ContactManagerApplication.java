package com.csi.contactmanager;

import com.csi.contactmanager.service.ContactFileMappingService;
import com.csi.contactmanager.service.ContactService;
import com.csi.contactmanager.resource.MenuService;
import com.csi.contactmanager.resource.PromptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;


@Slf4j
@SpringBootApplication
public class ContactManagerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ContactManagerApplication.class, args);
    }

    @Autowired
    MenuService menuService;
    @Autowired
    ContactFileMappingService fileMappingService;
    @Autowired
    ContactService contactService;
    @Autowired
    PromptService promptService;

    /**
     * Loads the first starting argument as a file
     * If not provided it prompts for the file name
     * @param args
     */
    @Override
    public void run(String... args) {

        if (args.length > 0) {
            File argProvidedFile = new File(args[0]);
            if (argProvidedFile.exists()) {
                fileMappingService.importFromFile(argProvidedFile.getAbsolutePath());
                menuService.start(contactService);
            } else {
                log.error("The file provided in starting parameters is missing!");
                log.info("Path is " + argProvidedFile.getAbsolutePath());
            }
        } else {
            log.warn("No starting parameters detected!");
            File promptedFile = new File(promptService.readFileName());
            if (promptedFile.exists()) {
                fileMappingService.importFromFile(promptedFile.getAbsolutePath());
                menuService.start(contactService);
            } else {
                log.error("Cannot find file specified! Path: {} Exiting...", promptedFile.getAbsolutePath());
            }
        }
    }
}
