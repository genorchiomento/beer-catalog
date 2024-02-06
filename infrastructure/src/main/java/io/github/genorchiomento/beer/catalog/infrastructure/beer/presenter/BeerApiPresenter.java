package io.github.genorchiomento.beer.catalog.infrastructure.beer.presenter;

import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.BeerOutput;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerApiOutput;

import java.util.function.Function;

public interface BeerApiPresenter {

    Function<BeerOutput, BeerApiOutput> present =
            output -> new BeerApiOutput(
                    output.id().getValue(),
                    output.name(),
                    output.style(),
                    output.origin(),
                    output.ibu(),
                    output.abv(),
                    output.color(),
                    output.ingredients(),
                    output.flavorDescription(),
                    output.aromaDescription(),
                    output.isActive(),
                    output.createdAt(),
                    output.updatedAt(),
                    output.deletedAt()
            );

    static BeerApiOutput present(final BeerOutput output) {
        return new BeerApiOutput(
                output.id().getValue(),
                output.name(),
                output.style(),
                output.origin(),
                output.ibu(),
                output.abv(),
                output.color(),
                output.ingredients(),
                output.flavorDescription(),
                output.aromaDescription(),
                output.isActive(),
                output.createdAt(),
                output.updatedAt(),
                output.deletedAt()
        );
    }
}
