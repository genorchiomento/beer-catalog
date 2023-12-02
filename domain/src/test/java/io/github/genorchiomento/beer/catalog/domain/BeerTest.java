package io.github.genorchiomento.beer.catalog.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class BeerTest {
    @Test
    public void testNewBeer() {
        Assertions.assertNotNull(new Beer());
    }
}