package io.github.genorchiomento.beer.catalog.domain.beer;

public record BeerSearchQuery(
        int page,
        int perPage,
        String terms,
        String sort,
        String direction
) {
}
