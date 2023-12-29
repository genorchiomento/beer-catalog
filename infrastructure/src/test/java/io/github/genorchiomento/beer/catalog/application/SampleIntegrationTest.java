package io.github.genorchiomento.beer.catalog.application;

import io.github.genorchiomento.beer.catalog.IntegrationTest;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@IntegrationTest
public class SampleIntegrationTest {

    @Autowired
    private CreateBeerUseCase useCase;

    @Autowired
    private BeerRepository repository;

    @Test
    public void testIntegrations() {
        Assertions.assertNotNull(useCase);
        Assertions.assertNotNull(repository);
    }
}
