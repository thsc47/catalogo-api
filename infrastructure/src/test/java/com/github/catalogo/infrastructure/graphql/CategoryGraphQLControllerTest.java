package com.github.catalogo.infrastructure.graphql;

import com.github.catalogo.application.category.list.ListCategoryOutput;
import com.github.catalogo.application.category.list.ListCategoryUseCase;
import com.github.catalogo.application.category.save.SaveCategoryUseCase;
import com.github.catalogo.domain.Fixture;
import com.github.catalogo.domain.category.Category;
import com.github.catalogo.domain.category.CategorySearchQuery;
import com.github.catalogo.domain.category.Pagination;
import com.github.catalogo.domain.utils.IdUtils;
import com.github.catalogo.domain.utils.InstantUtils;
import com.github.catalogo.infrastructure.GraphQLControllerTest;
import com.github.catalogo.infrastructure.configuration.category.graphql.CategoryGraphQLController;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.GraphQlTester;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@GraphQLControllerTest(controllers = CategoryGraphQLController.class)
public class CategoryGraphQLControllerTest {

    @MockBean
    private ListCategoryUseCase listCategoryUseCase;

    @MockBean
    private SaveCategoryUseCase saveCategoryUseCase;

    @Autowired
    private GraphQlTester graphql;

    @Test
    public void givenDefaultArgumentsWhenCallsListCategoriesShouldReturn() {
        // given
        final var expectedCategories = List.of(
                ListCategoryOutput.from(Fixture.Categories.lives()),
                ListCategoryOutput.from(Fixture.Categories.aulas())
        );
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedSearch = "";
        when(this.listCategoryUseCase.execute(any()))
                .thenReturn(new Pagination<>(
                        expectedPage, expectedPerPage, expectedCategories.size(), expectedCategories));
        final var query = """
                {
                  categories {
                    id
                    name
                  }
                }
                """;
        final var res = this.graphql.document(query).execute();
        final var actualCategories = res.path("categories")
                .entityList(ListCategoryOutput.class)
                .get();
        assertTrue(
                actualCategories.size() == expectedCategories.size()
                        && actualCategories.containsAll(expectedCategories)
        );
        final var capturer = ArgumentCaptor.forClass(CategorySearchQuery.class);
        verify(this.listCategoryUseCase).execute(capturer.capture());

        final var actualQuery = capturer.getValue();
        assertEquals(expectedPage, actualQuery.page());
        assertEquals(expectedPerPage, actualQuery.perPage());
        assertEquals(expectedSort, actualQuery.sort());
        assertEquals(expectedDirection, actualQuery.direction());
        assertEquals(expectedSearch, actualQuery.terms());
    }

    @Test
    public void givenCustomArgumentsWhenCallsListCategoriesShouldReturn() {
        final var expectedCategories = List.of(
                ListCategoryOutput.from(Fixture.Categories.lives()),
                ListCategoryOutput.from(Fixture.Categories.aulas())
        );

        final var expectedPage = 2;
        final var expectedPerPage = 15;
        final var expectedSort = "id";
        final var expectedDirection = "desc";
        final var expectedSearch = "asd";

        when(this.listCategoryUseCase.execute(any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedCategories.size(), expectedCategories));

        final var query = """
                query AllCategories($search: String, $page: Int, $perPage: Int, $sort: String, $direction: String) {
                                
                  categories(search: $search, page: $page, perPage: $perPage, sort: $sort, direction: $direction) {
                    id
                    name
                  }
                }
                """;

        final var res = this.graphql.document(query)
                .variable("search", expectedSearch)
                .variable("page", expectedPage)
                .variable("perPage", expectedPerPage)
                .variable("sort", expectedSort)
                .variable("direction", expectedDirection)
                .execute();

        final var actualCategories = res.path("categories")
                .entityList(ListCategoryOutput.class)
                .get();

        assertTrue(
                actualCategories.size() == expectedCategories.size()
                        && actualCategories.containsAll(expectedCategories)
        );

        final var capturer = ArgumentCaptor.forClass(CategorySearchQuery.class);

        verify(this.listCategoryUseCase).execute(capturer.capture());

        final var actualQuery = capturer.getValue();
        assertEquals(expectedPage, actualQuery.page());
        assertEquals(expectedPerPage, actualQuery.perPage());
        assertEquals(expectedSort, actualQuery.sort());
        assertEquals(expectedDirection, actualQuery.direction());
        assertEquals(expectedSearch, actualQuery.terms());
    }

    @Test
    public void givenCategoryInputWhenCallsSaveCategoryMutationShouldPersistAndReturn() {
        final var expectedId = IdUtils.uniqueId();
        final var expectedName = "Aulas";
        final var expectedDescription = "A melhor categoria";
        final var expectedActive = false;
        final var expectedCreatedAt = InstantUtils.now();
        final var expectedUpdatedAt = InstantUtils.now();
        final var expectedDeletedAt = InstantUtils.now();
        final var input = Map.of(
                "id", expectedId,
                "name", expectedName,
                "description", expectedDescription,
                "active", expectedActive,
                "createdAt", expectedCreatedAt,
                "updatedAt", expectedUpdatedAt,
                "deletedAt", expectedDeletedAt
        );
        final var query = """
                mutation SaveCategory($input: CategoryInput!) {
                    category: saveCategory(input: $input) {
                        id
                        name
                        description
                    }
                }
                """;
        doAnswer(returnsFirstArg()).when(saveCategoryUseCase).execute(any());

        this.graphql.document(query)
                .variable("input", input)
                .execute()
                .path("category.id").entity(String.class).isEqualTo(expectedId)
                .path("category.name").entity(String.class).isEqualTo(expectedName)
                .path("category.description").entity(String.class).isEqualTo(expectedDescription);

        final var capturer = ArgumentCaptor.forClass(Category.class);

        verify(this.saveCategoryUseCase).execute(capturer.capture());

        final var actualCategory = capturer.getValue();
        assertEquals(expectedId, actualCategory.id());
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(expectedActive, actualCategory.active());
        assertEquals(expectedCreatedAt, actualCategory.createdAt());
        assertEquals(expectedUpdatedAt, actualCategory.updatedAt());
        assertEquals(expectedDeletedAt, actualCategory.deletedAt());
    }
}