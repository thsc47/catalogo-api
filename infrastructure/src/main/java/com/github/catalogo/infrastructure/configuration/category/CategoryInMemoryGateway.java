package com.github.catalogo.infrastructure.configuration.category;

import com.github.catalogo.domain.category.Category;
import com.github.catalogo.domain.category.CategoryGateway;
import com.github.catalogo.domain.category.CategorySearchQuery;
import com.github.catalogo.domain.category.Pagination;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CategoryInMemoryGateway implements CategoryGateway {

    private final ConcurrentHashMap<String, Category> db;

    public CategoryInMemoryGateway() {
        this.db = new ConcurrentHashMap<>();
    }

    @Override
    public Category save(Category aCategory) {
        this.db.put(aCategory.id(), aCategory);
        return aCategory;
    }

    @Override
    public void deleteById(String anId) {
        this.db.remove(anId);
    }

    @Override
    public Optional<Category> findById(String anId) {
        return Optional.ofNullable(this.db.get(anId));
    }

    @Override
    public Pagination<Category> findAll(CategorySearchQuery aQuery) {
        final var values = this.db.values();
        return new Pagination<>(aQuery.page(), aQuery.perPage(), values.size(), new ArrayList<>(values));
    }
}
