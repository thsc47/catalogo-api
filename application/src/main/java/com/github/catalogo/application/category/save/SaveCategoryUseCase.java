package com.github.catalogo.application.category.save;

import com.github.catalogo.application.UseCase;
import com.github.catalogo.domain.category.Category;
import com.github.catalogo.domain.category.CategoryGateway;
import com.github.catalogo.domain.exceptions.NotificationException;
import com.github.catalogo.domain.validation.Error;
import com.github.catalogo.domain.validation.Notification;

import java.util.Objects;

public class SaveCategoryUseCase extends UseCase<Category, Category> {

    private final CategoryGateway categoryGateway;

    public SaveCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Category execute(final Category aCategory) {
        if (aCategory == null) {
            throw NotificationException.with(new Error("'aCategory' cannot be null"));
        }
        final var notification = Notification.create();
        aCategory.validate(notification);
        if (notification.hasError()) {
            throw NotificationException.with("Invalid category", notification);
        }
        return this.categoryGateway.save(aCategory);
    }
}