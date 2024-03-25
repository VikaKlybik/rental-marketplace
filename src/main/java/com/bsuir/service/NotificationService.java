package com.bsuir.service;

import com.bsuir.entity.EmailDetails;

public interface NotificationService {
    void sendEmail(EmailDetails emailDetails);
}