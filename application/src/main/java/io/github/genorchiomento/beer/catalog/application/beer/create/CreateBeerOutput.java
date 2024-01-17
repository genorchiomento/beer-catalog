package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;

public record CreateBeerOutput(
        String id
) {

    public static CreateBeerOutput from(final String anIn) {
        return new CreateBeerOutput(anIn);
    }

    public static CreateBeerOutput from(final Beer aBeer) {
        return new CreateBeerOutput(aBeer.getId().getValue());
    }
}
