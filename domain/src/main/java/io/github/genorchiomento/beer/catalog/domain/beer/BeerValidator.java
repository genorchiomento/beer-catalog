package io.github.genorchiomento.beer.catalog.domain.beer;

import io.github.genorchiomento.beer.catalog.domain.validation.Error;
import io.github.genorchiomento.beer.catalog.domain.validation.ValidationHandler;
import io.github.genorchiomento.beer.catalog.domain.validation.Validator;

public class BeerValidator extends Validator {

    private final Beer beer;

    public BeerValidator(
            final Beer beer,
            final ValidationHandler handler
    ) {
        super(handler);
        this.beer = beer;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = beer.getName();

        if (name == null) {
            validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.isBlank()) {
            validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        final var length = name.trim().length();
        if (length < 3 || length > 255) {
            validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
