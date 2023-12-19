package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;

public record UpdateBeerOutput(
        BeerID id
) {

    public static UpdateBeerOutput from(final Beer aBeer) {
        return new UpdateBeerOutput(aBeer.getId());
    }
}
