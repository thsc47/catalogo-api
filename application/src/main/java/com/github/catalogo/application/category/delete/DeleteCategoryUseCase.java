package com.github.catalogo.application.category.delete;

import com.github.catalogo.application.UnitUseCase;
import com.github.catalogo.domain.category.CategoryGateway;

import static java.util.Objects.requireNonNull;

public class DeleteCategoryUseCase extends UnitUseCase<String> {

    private final CategoryGateway categoryGateway;

    public DeleteCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = requireNonNull(categoryGateway);
    }

    @Override
    public void execute(final String anIn) {
        if (anIn == null) {
            return;
        }

        this.categoryGateway.deleteById(anIn);
    }
}
