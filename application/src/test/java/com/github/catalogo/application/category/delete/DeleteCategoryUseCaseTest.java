package com.github.catalogo.application.category.delete;

import com.github.catalogo.application.UseCaseTest;
import com.github.catalogo.domain.Fixture;
import com.github.catalogo.domain.category.CategoryGateway;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteCategoryUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenValidId_whenCallsDelete_shouldBeOk() {
        // given
        final var aulas = Fixture.Categories.aulas();
        final var expectedId = aulas.id();
        doNothing().when(this.categoryGateway).deleteById(anyString());

        assertDoesNotThrow(() -> this.useCase.execute(expectedId));

        verify(this.categoryGateway).deleteById(eq(expectedId));
    }
    @Test
    public void givenInvalidId_whenCallsDelete_shouldBeOk() {
        // given
        final String expectedId = null;
        // when
        assertDoesNotThrow(() -> this.useCase.execute(expectedId));
        // then
        verify(this.categoryGateway, never()).deleteById(eq(expectedId));
    }
}