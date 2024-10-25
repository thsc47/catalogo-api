package com.github.catalogo.domain.exceptions;

import com.github.catalogo.domain.validation.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }

    public static NotificationException with(final String message, final Notification notification) {
        return new NotificationException(message, notification);
    }
}

