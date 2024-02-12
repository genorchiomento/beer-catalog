package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;

public record UpdateBeerOutput(
        String id
) {

    public static UpdateBeerOutput from(final String anId) {
        return new UpdateBeerOutput(anId);
    }

    public static UpdateBeerOutput from(final Beer aBeer) {
        return new UpdateBeerOutput(aBeer.getId().getValue());
    }
}
