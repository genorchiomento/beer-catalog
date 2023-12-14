package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.application.UseCase;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateBeerUseCase
        extends UseCase<CreateBeerCommand, Either<Notification, CreateBeerOutput>> {
}
