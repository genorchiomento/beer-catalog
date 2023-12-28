package io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence;


import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "beer")
public class BeerJpaEntity {
    @Id
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "style")
    private StyleEnum style;

    @Column(name = "origin")
    private String origin;

    @Column(name = "ibu")
    private Double ibu;

    @Column(name = "abv")
    private Double abv;

    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private ColorEnum color;

    @Column(name = "ingredients", length = 4000)
    private String ingredients;

    @Column(name = "flavor_description", length = 4000)
    private String flavorDescription;

    @Column(name = "aroma_description", length = 4000)
    private String aromaDescription;

    @Column(name = "active", nullable = false)
    private boolean isActive;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant updatedAt;

    @Column(name = "deleted_at", columnDefinition = "DATETIME(6)")
    private Instant deletedAt;

    public BeerJpaEntity() {
    }

    private BeerJpaEntity(
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
            final boolean isActive,
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
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static BeerJpaEntity from(final Beer aBeer) {
        return new BeerJpaEntity(
                aBeer.getId().getValue(),
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

    public Beer toAggregate() {
        return Beer.with(
                BeerID.from(getId()),
                getName(),
                getStyle(),
                getOrigin(),
                getIbu(),
                getAbv(),
                getColor(),
                getIngredients(),
                getFlavorDescription(),
                getAromaDescription(),
                isActive(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public StyleEnum getStyle() {
        return style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getIbu() {
        return ibu;
    }

    public void setIbu(Double ibu) {
        this.ibu = ibu;
    }

    public Double getAbv() {
        return abv;
    }

    public void setAbv(Double abv) {
        this.abv = abv;
    }

    public ColorEnum getColor() {
        return color;
    }

    public void setColor(ColorEnum color) {
        this.color = color;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getFlavorDescription() {
        return flavorDescription;
    }

    public void setFlavorDescription(String flavorDescription) {
        this.flavorDescription = flavorDescription;
    }

    public String getAromaDescription() {
        return aromaDescription;
    }

    public void setAromaDescription(String aromaDescription) {
        this.aromaDescription = aromaDescription;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }
}
