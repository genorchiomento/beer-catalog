package io.github.genorchiomento.beer.catalog.domain.beer;

import io.github.genorchiomento.beer.catalog.domain.Identifier;

import java.util.Objects;
import java.util.UUID;

public class BeerID extends Identifier {

    private final String value;

    private BeerID(final String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    public static BeerID unique() {
        return BeerID.from(UUID.randomUUID());
    }

    public static BeerID from(final String id) {
        return new BeerID(id);
    }

    public static BeerID from(final UUID id) {
        return new BeerID(id.toString().toLowerCase());
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final BeerID beerID = (BeerID) o;
        return Objects.equals(getValue(), beerID.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
