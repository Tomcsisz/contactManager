package com.csi.contactmanager.service;

import com.csi.contactmanager.model.Contact;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class ContactExtractorServiceTest {

    ValidatorService validatorService = new ValidatorService();

    @Test
    public void extractorServiceTest() {
        ContactExtractorService contactExtractorService = new ContactExtractorService(validatorService);
        assertEquals(new Contact("Tamas","0744/163-728","tomcsi.szatmari@gmail.com"),
                contactExtractorService.extractFromLine("\"Tamas\": \"(0744)163728\", tomcsi.szatmari@gmail.com")
                );

        assertEquals(new Contact("Geza","INVALID","gezageza@g@mail.com"),
                contactExtractorService.extractFromLine("\"Geza\": \"(0744)8\", gezageza@g@mail.com")
        );

        assertEquals(new Contact("XXX","INVALID","Empty"),
                contactExtractorService.extractFromLine("\"\": \"(0744)8\", thexx.com")
        );
    }
}