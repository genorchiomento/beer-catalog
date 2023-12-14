package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateBeerUseCase extends CreateBeerUseCase {

    private final BeerGateway beerGateway;

    public DefaultCreateBeerUseCase(final BeerGateway beerGateway) {
        this.beerGateway = Objects.requireNonNull(beerGateway);
    }

    @Override
    public Either<Notification, CreateBeerOutput> execute(final CreateBeerCommand aCommand) {
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

        final var notification = Notification.create();

        final var aBeer = Beer.newBeer(
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

        aBeer.validate(notification);

        return notification.hasErrors() ? API.Left(notification) : create(aBeer);
    }

    private Either<Notification, CreateBeerOutput> create(final Beer aBeer) {
        return API.Try(() -> beerGateway.create(aBeer))
                .toEither()
                .bimap(Notification::create, CreateBeerOutput::from);
    }

    //Este metodo é a mesma coisa que o de cima, porem mais verboso e o de cima mais expressivo.
    private Either<Notification, CreateBeerOutput> otherCreateExample(final Beer aBeer) {
        try {
            return API.Right(CreateBeerOutput.from(beerGateway.create(aBeer)));
        } catch (Throwable t) {
            return API.Left(Notification.create(t));
        }
    }
}
