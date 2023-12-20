package io.github.genorchiomento.beer.catalog.application.beer.retrieve.get;

import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetBeerByIdUseCase extends GetBeerByIdUseCase {

    private final BeerGateway beerGateway;

    public DefaultGetBeerByIdUseCase(final BeerGateway beerGateway) {
        this.beerGateway = Objects.requireNonNull(beerGateway);
    }

    @Override
    public BeerOutput execute(final String anIn) {
        final var aBeerID = BeerID.from(anIn);

        return beerGateway.findById(aBeerID)
                .map(BeerOutput::from)
                .orElseThrow(notFound(aBeerID));
    }

    private Supplier<DomainException> notFound(final BeerID anId) {
        return () -> DomainException.with(
                new Error("Beer with ID %s was not found".formatted(anId.getValue()))
        );
    }
}
