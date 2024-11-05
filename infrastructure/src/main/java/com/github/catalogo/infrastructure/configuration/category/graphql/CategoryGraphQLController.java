package com.github.catalogo.infrastructure.configuration.category.graphql;

import com.github.catalogo.application.category.list.ListCategoryOutput;
import com.github.catalogo.application.category.list.ListCategoryUseCase;
import com.github.catalogo.domain.category.CategorySearchQuery;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

import static java.util.Objects.requireNonNull;

@Controller
public class CategoryGraphQLController {

    private final ListCategoryUseCase listCategoryUseCase;

    public CategoryGraphQLController(final ListCategoryUseCase listCategoryUseCase) {
        this.listCategoryUseCase = requireNonNull(listCategoryUseCase);
    }

    @QueryMapping
    public List<ListCategoryOutput> categories(
            @Argument final String search,
            @Argument final int page,
            @Argument final int perPage,
            @Argument final String sort,
            @Argument final String direction
    ) {
        final var aQuery =
                new CategorySearchQuery(page, perPage, search, sort, direction);
        return this.listCategoryUseCase.execute(aQuery).data();
    }
}