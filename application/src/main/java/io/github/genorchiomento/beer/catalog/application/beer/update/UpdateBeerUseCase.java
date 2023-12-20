package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.application.UseCase;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateBeerUseCase
        extends UseCase<UpdateBeerCommand, Either<Notification, UpdateBeerOutput>> {
}
