package io.github.genorchiomento.beer.catalog.infrastructure.beer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;

import java.time.Instant;

public record BeerResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("style") StyleEnum style,
        @JsonProperty("origin") String origin,
        @JsonProperty("ibu") Double ibu,
        @JsonProperty("abv") Double abv,
        @JsonProperty("color") ColorEnum color,
        @JsonProperty("ingredients") String ingredients,
        @JsonProperty("flavor_description") String flavorDescription,
        @JsonProperty("aroma_description") String aromaDescription,
        @JsonProperty("is_active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt,
        @JsonProperty("deleted_at") Instant deletedAt
) {
}
