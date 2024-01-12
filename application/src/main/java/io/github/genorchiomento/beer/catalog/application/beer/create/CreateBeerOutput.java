package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;

public record CreateBeerOutput(
        BeerID id
) {

    public static CreateBeerOutput from(final BeerID anIn) {
        return new CreateBeerOutput(anIn);
    }

    public static CreateBeerOutput from(final Beer aBeer) {
        return new CreateBeerOutput(aBeer.getId());
    }
}
