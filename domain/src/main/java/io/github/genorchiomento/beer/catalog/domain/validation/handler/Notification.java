package io.github.genorchiomento.beer.catalog.domain.validation.handler;

import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.validation.Error;
import io.github.genorchiomento.beer.catalog.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    public static Notification create(final Error anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    @Override
    public Notification append(final Error error) {
        errors.add(error);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler handler) {
        errors.addAll(handler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation validation) {
        try {
            validation.validate();
        } catch (final DomainException ex) {
            errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            errors.add(new Error(t.getMessage()));
        }
        return this;
    }

    @Override
    public List<Error> getErrors() {
        return errors;
    }
}
