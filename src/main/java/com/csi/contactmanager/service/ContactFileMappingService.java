package com.csi.contactmanager.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

@Slf4j
@Service
public class ContactFileMappingService {

    @Autowired
    ContactExtractorService extractorService;

    @Autowired
    ContactService contactService;

    /**
     * This will fill the repository with entries from a provided file
     * @param fileWithPath
     */
    public void importFromFile(String fileWithPath) {
        String fileName = fileWithPath;

        log.info("Opening file {}", fileWithPath);
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();

                //Basic check for the line we process, ignore the errored lines
                if (line != "" && line.contains(":") && line.contains(",")) {
                    try {
                        contactService.addContact(extractorService.extractFromLine(line));
                    } catch (Exception ex) {
                        log.error("Can't extract contact from line '{}' an exception occurred: {}", line, ex.getMessage());
                    }
                }
                else {
                    log.warn("Disregarding line '{}' because it doesn't have the correct format", line);
                }
            }
        } catch (IOException ex) {
            // IO errors like no read permission, corrupt file or any other IO exception
            log.error("Error occurred when parsing the file {}! Message: {}", fileWithPath, ex.getMessage());
        }

    }
}
