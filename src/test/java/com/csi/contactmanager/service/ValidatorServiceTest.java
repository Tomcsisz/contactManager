package com.csi.contactmanager.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidatorServiceTest {

    ValidatorService validatorService = new ValidatorService();

    @Test
    public void nameValidatorTest() {
        assertEquals("Foobar",validatorService.validateName("Foobar"));
        assertEquals("XXX",validatorService.validateName("#@$#@$"));
        assertEquals("Tamas",validatorService.validateName("\"Tamas\""));
    }

    @Test
    public void phoneValidatorTest() {
        assertEquals("0630/888-8888",validatorService.validatePhone("(0630)8888888"));
        assertEquals("0630/888-8",validatorService.validatePhone("0630)8888"));
        assertEquals("INVALID",validatorService.validatePhone("(0630888"));
    }

    @Test
    public void emailValidatorTest() {
        assertEquals("no@email.org",validatorService.validateEmail("no@email.org"));
        assertEquals("Empty",validatorService.validateEmail("no@email"));
        assertEquals("asd@email.com",validatorService.validateEmail("asd@email.com"));
        assertEquals("Empty",validatorService.validateEmail("no@email.foo"));
    }
}