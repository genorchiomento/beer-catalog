package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.validation.Error;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateBeerUseCase extends UpdateBeerUseCase {

    private final BeerGateway beerGateway;

    public DefaultUpdateBeerUseCase(final BeerGateway beerGateway) {
        this.beerGateway = Objects.requireNonNull(beerGateway);
    }


    @Override
    public Either<Notification, UpdateBeerOutput> execute(final UpdateBeerCommand aCommand) {
        final var anId = BeerID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aStyle = aCommand.style();
        final var anOrigin = aCommand.origin();
        final var anIbu = aCommand.ibu();
        final var anAbv = aCommand.abv();
        final var aColor = aCommand.color();
        final var anIngredients = aCommand.ingredients();
        final var aFlavorDescription = aCommand.flavorDescription();
        final var aAromaDescription = aCommand.aromaDescription();
        final var isActive = aCommand.isActive();

        final var aBeer = beerGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        final var aBeerUpdate = aBeer.update(
                aName,
                aStyle,
                anOrigin,
                anIbu,
                anAbv,
                aColor,
                anIngredients,
                aFlavorDescription,
                aAromaDescription,
                isActive
        );

        aBeerUpdate.validate(notification);

        return notification.hasErrors() ? API.Left(notification) : update(aBeer);
    }

    private Either<Notification, UpdateBeerOutput> update(final Beer aBeer) {
        return API.Try(() -> beerGateway.update(aBeer))
                .toEither()
                .bimap(Notification::create, UpdateBeerOutput::from);
    }

    private Supplier<DomainException> notFound(final BeerID anId) {
        return () -> DomainException.with(
                new Error("Beer with ID %s was not found".formatted(anId.getValue()))
        );
    }
}
