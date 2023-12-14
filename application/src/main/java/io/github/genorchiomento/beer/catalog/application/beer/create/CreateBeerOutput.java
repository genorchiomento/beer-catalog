package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;

public record CreateBeerOutput(
        BeerID id
) {

    public static CreateBeerOutput from(Beer aBeer) {
        return new CreateBeerOutput(aBeer.getId());
    }
}
