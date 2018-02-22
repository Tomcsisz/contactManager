package com.csi.contactmanager.service;

import com.csi.contactmanager.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class ContactExtractorService {

    ValidatorService validatorService;

    @Autowired
    /**
     * Switched to constructor based DI, so it can be unit tested
     */
    public ContactExtractorService(ValidatorService validatorService) {
        this.validatorService = validatorService;
    }

    /**
     * This function can easily break, but its tested and caught on a higher level
     * Line example:
     * "Geza : "06/309898987", bb mail@email.com"
     *
     * @param line The raw line from the input file
     * @return a contact constructed from the extracted data
     */
    public Contact extractFromLine(String line) {

        // Assuming it contains a colon, this is tested on a higher level
        String[] colonResult = line.split(":");

        if (colonResult.length <= 1) {
            log.error("Input line \"{}\" doesn't contain a colon!", line);
        }

        //it will remove anything non-alphanumeric and if the result is empty the name will be 'XXX'
        String name = validatorService.validateName(colonResult[0]);
        // Assuming it contains a comma, this is tested on a higher level
        String[] commaResult = colonResult[1].split(",");

        if (commaResult.length <= 1) {
            log.error("Input line \"{}\" doesn't contain a comma!", line);
        }

        String phoneNo = validatorService.validatePhone(commaResult[0]);
        String email = validatorService.validateEmail(commaResult[1]);

        return new Contact(name, phoneNo, email);
    }
}
