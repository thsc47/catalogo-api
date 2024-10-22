package com.github.catalogo.domain.category;

import com.github.catalogo.domain.UnitTest;
import com.github.catalogo.domain.exceptions.DomainException;
import com.github.catalogo.domain.utils.InstantUtils;
import com.github.catalogo.domain.validation.ThrowsValidationHandler;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest extends UnitTest {

    @Test
    public void givenAValidParams_whenCallWith_thenInstantiateACategory() {
        // given
        final var expectedID = UUID.randomUUID().toString();
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();
        // when
        final var actualCategory =
                Category.with(expectedID, expectedName, expectedDescription, expectedIsActive, expectedDates, expectedDates, expectedDates);
        // then
        assertNotNull(actualCategory);
        assertEquals(expectedID, actualCategory.id());
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(expectedIsActive, actualCategory.active());
        assertEquals(expectedDates, actualCategory.createdAt());
        assertEquals(expectedDates, actualCategory.updatedAt());
        assertEquals(expectedDates, actualCategory.deletedAt());
    }

    @Test
    public void givenAValidParams_whenCallWithCategory_thenInstantiateACategory() {
        // given
        final var expectedID = UUID.randomUUID().toString();
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();
        final var aCategory =
                Category.with(expectedID, expectedName, expectedDescription, expectedIsActive, expectedDates, expectedDates, expectedDates);
        // then
        final var actualCategory = Category.with(aCategory);
        // when
        assertNotNull(actualCategory);
        assertEquals(aCategory.id(), actualCategory.id());
        assertEquals(aCategory.name(), actualCategory.name());
        assertEquals(aCategory.description(), actualCategory.description());
        assertEquals(aCategory.active(), actualCategory.active());
        assertEquals(aCategory.createdAt(), actualCategory.createdAt());
        assertEquals(aCategory.updatedAt(), actualCategory.updatedAt());
        assertEquals(aCategory.deletedAt(), actualCategory.deletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // given
        final String expectedName = null;
        final var expectedID = UUID.randomUUID().toString();
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        // when
        final var actualCategory =
                Category.with(expectedID, expectedName, expectedDescription, expectedIsActive, expectedDates, expectedDates, expectedDates);
        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // given
        final String expectedName = " ";
        final var expectedID = UUID.randomUUID().toString();
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        // when
        final var actualCategory =
                Category.with(expectedID, expectedName, expectedDescription, expectedIsActive, expectedDates, expectedDates, expectedDates);
        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidNullId_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // given
        final String expectedID = null;
        final var expectedName = "Aulas";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'id' should not be empty";
        // when
        final var actualCategory =
                Category.with(expectedID, expectedName, expectedDescription, expectedIsActive, expectedDates, expectedDates, expectedDates);
        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnInvalidEmptyId_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        // given
        final var expectedID = " ";
        final var expectedName = "Aulas";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedDates = InstantUtils.now();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'id' should not be empty";
        // when
        final var actualCategory =
                Category.with(expectedID, expectedName, expectedDescription, expectedIsActive, expectedDates, expectedDates, expectedDates);
        final var actualException =
                assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }
}
