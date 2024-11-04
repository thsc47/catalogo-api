package com.github.catalogo.infrastructure;

import com.github.catalogo.infrastructure.configuration.ObjectMapperConfig;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@GraphQlTest
@Import(ObjectMapperConfig.class)
@Tag("integrationTest")
public @interface GraphQLControllerTest {
    @AliasFor(annotation = GraphQlTest.class, attribute = "controllers")
    Class<?>[] controllers() default {};
}
