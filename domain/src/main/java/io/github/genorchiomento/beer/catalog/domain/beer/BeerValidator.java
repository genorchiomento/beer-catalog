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
        if (beer.getName() == null) {
            validationHandler().append(new Error("'name' should not be null"));
        }
    }
}
