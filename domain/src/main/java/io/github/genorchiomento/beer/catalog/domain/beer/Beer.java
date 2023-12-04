package io.github.genorchiomento.beer.catalog.domain.beer;

import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;

import java.time.Instant;
import java.util.UUID;

public class Beer {

    private final String id;
    private final String name;
    private final StyleEnum style;
    private final String origin;
    private final Double ibu;
    private final Double abv;
    private final ColorEnum color;
    private final String ingredients;
    private final String flavorDescription;
    private final String aromaDescription;
    private final boolean active;
    private final Instant createdAt;
    private final Instant updatedAt;
    private final Instant deletedAt;

    private Beer(
            final String id,
            final String name,
            final StyleEnum style,
            final String origin,
            final Double ibu,
            final Double abv,
            final ColorEnum color,
            final String ingredients,
            final String flavorDescription,
            final String aromaDescription,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.style = style;
        this.origin = origin;
        this.ibu = ibu;
        this.abv = abv;
        this.color = color;
        this.ingredients = ingredients;
        this.flavorDescription = flavorDescription;
        this.aromaDescription = aromaDescription;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Beer newBeer(
            final String aName,
            final StyleEnum aStyle,
            final String anOrigin,
            final Double anIbu,
            final Double anAbv,
            final ColorEnum aColor,
            final String anIngredients,
            final String aFlavorDescription,
            final String anAromaDescription,
            final boolean isActive
    ) {
        final var id = UUID.randomUUID().toString();
        final var now = Instant.now();

        return new Beer(
                id,
                aName,
                aStyle,
                anOrigin,
                anIbu,
                anAbv,
                aColor,
                anIngredients,
                aFlavorDescription,
                anAromaDescription,
                isActive,
                now,
                now,
                null
        );
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public StyleEnum getStyle() {
        return style;
    }

    public String getOrigin() {
        return origin;
    }

    public Double getIbu() {
        return ibu;
    }

    public Double getAbv() {
        return abv;
    }

    public ColorEnum getColor() {
        return color;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getFlavorDescription() {
        return flavorDescription;
    }

    public String getAromaDescription() {
        return aromaDescription;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}