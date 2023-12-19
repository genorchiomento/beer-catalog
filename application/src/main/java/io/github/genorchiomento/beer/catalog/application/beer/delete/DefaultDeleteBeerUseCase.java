package io.github.genorchiomento.beer.catalog.application.beer.delete;

import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;

import java.util.Objects;

public class DefaultDeleteBeerUseCase extends DeleteBeerUseCase {

    private final BeerGateway beerGateway;

    public DefaultDeleteBeerUseCase(final BeerGateway beerGateway) {
        this.beerGateway = Objects.requireNonNull(beerGateway);
    }

    @Override
    public void execute(final String anIn) {
        beerGateway.deleteById(BeerID.from(anIn));
    }
}
