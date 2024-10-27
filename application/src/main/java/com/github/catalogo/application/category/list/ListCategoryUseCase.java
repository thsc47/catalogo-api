package com.github.catalogo.application.category.list;

import com.github.catalogo.application.UseCase;
import com.github.catalogo.domain.category.CategoryGateway;
import com.github.catalogo.domain.category.CategorySearchQuery;
import com.github.catalogo.domain.category.Pagination;

import static java.util.Objects.requireNonNull;

public class ListCategoryUseCase extends UseCase<CategorySearchQuery, Pagination<ListCategoryOutput>> {

    private final CategoryGateway categoryGateway;

    public ListCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<ListCategoryOutput> execute(final CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(ListCategoryOutput::from);
    }
}
