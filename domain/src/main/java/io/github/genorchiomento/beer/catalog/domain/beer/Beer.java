package io.github.genorchiomento.beer.catalog.domain.beer;

import io.github.genorchiomento.beer.catalog.domain.AggregateRoot;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.domain.validation.ValidationHandler;

import java.time.Instant;

public class Beer extends AggregateRoot<BeerID> {

    private String name;
    private StyleEnum style;
    private String origin;
    private Double ibu;
    private Double abv;
    private ColorEnum color;
    private String ingredients;
    private String flavorDescription;
    private String aromaDescription;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Beer(
            final BeerID id,
            final String name,
            final StyleEnum style,
            final String origin,
            final Double ibu,
            final Double abv,
            final ColorEnum color,
            final String ingredients,
            final String flavorDescription,
            final String aromaDescription,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.style = style;
        this.origin = origin;
        this.ibu = ibu;
        this.abv = abv;
        this.color = color;
        this.ingredients = ingredients;
        this.flavorDescription = flavorDescription;
        this.aromaDescription = aromaDescription;
        this.isActive = isActive;
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
        final var id = BeerID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

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
                deletedAt
        );
    }

    public static Beer withClone(final Beer aBeer) {
        return new Beer(
                aBeer.getId(),
                aBeer.name,
                aBeer.style,
                aBeer.origin,
                aBeer.ibu,
                aBeer.abv,
                aBeer.color,
                aBeer.ingredients,
                aBeer.flavorDescription,
                aBeer.aromaDescription,
                aBeer.isActive,
                aBeer.createdAt,
                aBeer.updatedAt,
                aBeer.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new BeerValidator(this, handler).validate();
    }

    public Beer activate() {
        deletedAt = null;
        isActive = true;
        updatedAt = Instant.now();

        return this;
    }

    public Beer deactivate() {
        if (getDeletedAt() == null) {
            deletedAt = Instant.now();
        }

        isActive = false;
        updatedAt = Instant.now();

        return this;
    }

    public Beer update(
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
        updatedAt = Instant.now();
        name = aName;
        style = aStyle;
        origin = anOrigin;
        ibu = anIbu;
        abv = anAbv;
        color = aColor;
        ingredients = anIngredients;
        flavorDescription = aFlavorDescription;
        aromaDescription = anAromaDescription;
        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        return this;
    }

    public BeerID getId() {
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
        return isActive;
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