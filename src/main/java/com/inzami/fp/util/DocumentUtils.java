package com.inzami.fp.util;

import com.inzami.fp.domain.Client;
import com.inzami.fp.enums.DocumentType;

import java.time.ZonedDateTime;

public class DocumentUtils {
    
    public static String generateDocumentNumber(Client client, DocumentType documentType){
        ZonedDateTime now = ZonedDateTime.now();
        String documentNumber = "" + documentType.name().charAt(0) + client.getFirstName().charAt(0) + client.getLastName().charAt(0)
                + "/" + now.getDayOfYear() + "-" + now.getHour() + "-" + now.getMinute() + now.getSecond();
        return documentNumber;
    }
}
