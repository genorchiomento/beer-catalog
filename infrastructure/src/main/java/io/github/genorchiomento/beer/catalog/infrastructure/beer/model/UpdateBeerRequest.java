package io.github.genorchiomento.beer.catalog.infrastructure.beer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;

public record UpdateBeerRequest(
        @JsonProperty("name") String name,
        @JsonProperty("style") StyleEnum style,
        @JsonProperty("origin") String origin,
        @JsonProperty("ibu") Double ibu,
        @JsonProperty("abv") Double abv,
        @JsonProperty("color") ColorEnum color,
        @JsonProperty("ingredients") String ingredients,
        @JsonProperty("flavor_description") String flavorDescription,
        @JsonProperty("aroma_description") String aromaDescription,
        @JsonProperty("is_active") Boolean active
) {
}
