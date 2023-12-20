package io.github.genorchiomento.beer.catalog.application.beer.retrieve.get;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;

import java.time.Instant;

public record BeerOutput(
        BeerID id,
        String name,
        StyleEnum style,
        String origin,
        double ibu,
        double abv,
        ColorEnum color,
        String ingredients,
        String flavorDescription,
        String aromaDescription,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt,
        Instant deletedAt
) {

    public static BeerOutput from(final Beer aBeer) {
        return new BeerOutput(
                aBeer.getId(),
                aBeer.getName(),
                aBeer.getStyle(),
                aBeer.getOrigin(),
                aBeer.getIbu(),
                aBeer.getAbv(),
                aBeer.getColor(),
                aBeer.getIngredients(),
                aBeer.getFlavorDescription(),
                aBeer.getAromaDescription(),
                aBeer.isActive(),
                aBeer.getCreatedAt(),
                aBeer.getUpdatedAt(),
                aBeer.getDeletedAt()
        );
    }
}
