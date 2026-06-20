package com.chriscodelab.contact.service;

import com.chriscodelab.contact.dto.request.ContactRequestDTO;

public interface EmailService {

    void sendContactEmails(ContactRequestDTO request);

}
