package io.github.genorchiomento.beer.catalog.infrastructure.beer;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BeerMySQLGateway implements BeerGateway {

    private final BeerRepository repository;

    public BeerMySQLGateway(final BeerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Beer create(Beer aBeer) {
        return null;
    }

    @Override
    public void deleteById(BeerID anId) {

    }

    @Override
    public Optional<Beer> findById(BeerID anId) {
        return Optional.empty();
    }

    @Override
    public Beer update(Beer aBeer) {
        return null;
    }

    @Override
    public Pagination<Beer> findAll(BeerSearchQuery aQuery) {
        return null;
    }
}
