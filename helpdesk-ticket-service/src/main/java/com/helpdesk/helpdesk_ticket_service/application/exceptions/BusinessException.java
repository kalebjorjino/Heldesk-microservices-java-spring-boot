package com.helpdesk.helpdesk_ticket_service.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // Esto devolverá un error 400 Bad Request
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}
