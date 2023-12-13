package io.github.genorchiomento.beer.catalog.domain.beer;

import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;

import java.util.Optional;

public interface BeerGateway {

    Beer create(Beer aBeer);
    void delete(BeerID anId);
    Optional<Beer> findById(BeerID anId);
    Beer update(Beer aBeer);
    Pagination<Beer> findAll(BeerSearchQuery aQuery);
}
