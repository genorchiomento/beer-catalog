package io.github.genorchiomento.beer.catalog.application.beer.retrieve.list;

import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListBeersUseCase extends ListBeerUseCase {

    private final BeerGateway beerGateway;

    public DefaultListBeersUseCase(final BeerGateway beerGateway) {
        this.beerGateway = Objects.requireNonNull(beerGateway);
    }

    @Override
    public Pagination<BeerListOutput> execute(final BeerSearchQuery aQuery) {
        return beerGateway.findAll(aQuery)
                .map(BeerListOutput::from);
    }
}
