package io.github.genorchiomento.beer.catalog.infrastructure.beer;

import io.github.genorchiomento.beer.catalog.infrastructure.MySQLGatewayTest;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class BeerMySQLGatewayTest {

    @Autowired
    private BeerMySQLGateway beerMySQLGateway;

    @Autowired
    private BeerRepository beerRepository;

    @Test
    public void testInjectedDependencies() {
        Assertions.assertNotNull(beerMySQLGateway);
        Assertions.assertNotNull(beerRepository);
    }
}


