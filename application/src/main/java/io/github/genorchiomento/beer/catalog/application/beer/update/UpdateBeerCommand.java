package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;

public record UpdateBeerCommand(
        String id,
        String name,
        StyleEnum style,
        String origin,
        Double ibu,
        Double abv,
        ColorEnum color,
        String ingredients,
        String flavorDescription,
        String aromaDescription,
        boolean isActive
) {
    public static UpdateBeerCommand with(
            final String anId,
            final String aName,
            final StyleEnum aStyle,
            final String anOrigin,
            final Double anIbu,
            final Double anAbv,
            final ColorEnum anColor,
            final String anIngredients,
            final String aflavorDescription,
            final String anAromaDescription,
            final boolean isActive
    ) {
        return new UpdateBeerCommand(
                anId,
                aName,
                aStyle,
                anOrigin,
                anIbu,
                anAbv,
                anColor,
                anIngredients,
                aflavorDescription,
                anAromaDescription,
                isActive
        );
    }
}
