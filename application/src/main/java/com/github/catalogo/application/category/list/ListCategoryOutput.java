package com.github.catalogo.application.category.list;

import com.github.catalogo.domain.category.Category;

public record ListCategoryOutput(
        String id,
        String name
) {
    public static ListCategoryOutput from(final Category aCategory) {
        return new ListCategoryOutput(aCategory.id(), aCategory.name());
    }
}
