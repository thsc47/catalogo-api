package com.github.catalogo.domain.category.save;

import com.github.catalogo.domain.Fixture;
import com.github.catalogo.domain.UnitTest;
import com.github.catalogo.domain.category.Category;
import com.github.catalogo.domain.category.CategoryGateway;
import com.github.catalogo.domain.exceptions.DomainException;
import com.github.catalogo.domain.utils.InstantUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.UUID;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

public class SaveCategoryUseCaseTest extends UnitTest {

    @InjectMocks
    private SaveCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenValidCategory_whenCallsSave_shouldPersistIt() {
        // given
        final var aCategory = Fixture.Categories.aulas();
        when(categoryGateway.save(any()))
                .thenAnswer(returnsFirstArg());
        // when
        this.useCase.execute(aCategory);
        // then
        verify(categoryGateway, never()).save(eq(aCategory));
    }
    @Test
    public void givenInvalidId_whenCallsSave_shouldReturnError() {
        // given
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'id' should not be empty";
        final var aCategory = Category.with(
                "",
                "Aulas",
                "Conteudo gravado",
                true,
                InstantUtils.now(),
                InstantUtils.now(),
                null
        );
        // when
        final var actualError = assertThrows(DomainException.class, () -> this.useCase.execute(aCategory));
        // then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
        verify(categoryGateway, never()).save(eq(aCategory));
    }
    @Test
    public void givenInvalidName_whenCallsSave_shouldReturnError() {
        // given
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var aCategory = Category.with(
                UUID.randomUUID().toString().replace("-", ""),
                "",
                "Conteudo gravado",
                true,
                InstantUtils.now(),
                InstantUtils.now(),
                null
        );
        // when
        final var actualError = assertThrows(DomainException.class, () -> this.useCase.execute(aCategory));
        // then
        assertEquals(expectedErrorCount, actualError.getErrors().size());
        assertEquals(expectedErrorMessage, actualError.getErrors().get(0).message());
        verify(categoryGateway, never()).save(eq(aCategory));
    }
}