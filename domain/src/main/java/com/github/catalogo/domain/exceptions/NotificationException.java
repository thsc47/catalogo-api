package com.github.catalogo.domain.exceptions;

import com.github.catalogo.domain.validation.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}

