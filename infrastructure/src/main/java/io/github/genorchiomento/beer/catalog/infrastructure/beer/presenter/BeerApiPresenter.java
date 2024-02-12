package io.github.genorchiomento.beer.catalog.infrastructure.beer.presenter;

import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.BeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.list.BeerListOutput;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerListResponse;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerResponse;

import java.util.function.Function;

public interface BeerApiPresenter {

    Function<BeerOutput, BeerResponse> present =
            output -> new BeerResponse(
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

    static BeerResponse present(final BeerOutput output) {
        return new BeerResponse(
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

    static BeerListResponse present(final BeerListOutput output) {
        return new BeerListResponse(
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
                output.deletedAt()
        );
    }
}
