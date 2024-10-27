package com.github.catalogo.application.category.list;

import com.github.catalogo.application.UseCaseTest;
import com.github.catalogo.domain.Fixture;
import com.github.catalogo.domain.category.CategoryGateway;
import com.github.catalogo.domain.category.CategorySearchQuery;
import com.github.catalogo.domain.category.Pagination;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ListCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private ListCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenValidQuery_whenCallsListCategories_shouldReturnCategories() {
        // given
        final var categories = List.of(
                Fixture.Categories.lives(),
                Fixture.Categories.aulas()
        );
        final var expectedItems = categories.stream()
                .map(ListCategoryOutput::from)
                .toList();
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "Algo";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 2;
        final var aQuery =
                new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort, expectedDirection);
        final var pagination =
                new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);
        when(this.categoryGateway.findAll(any()))
                .thenReturn(pagination);
        // when
        final var actualOutput = this.useCase.execute(aQuery);
        // then
        assertEquals(expectedPage, actualOutput.currentPage());
        assertEquals(expectedPerPage, actualOutput.perPage());
        assertEquals(expectedItemsCount, actualOutput.items().size());
        assertTrue(
                expectedItems.size() == actualOutput.items().size() &&
                        expectedItems.containsAll(actualOutput.items())
        );
    }
}
