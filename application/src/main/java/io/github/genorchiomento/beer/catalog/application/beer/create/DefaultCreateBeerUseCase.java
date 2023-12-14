package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateBeerUseCase extends CreateBeerUseCase {

    private final BeerGateway beerGateway;

    public DefaultCreateBeerUseCase(final BeerGateway beerGateway) {
        this.beerGateway = Objects.requireNonNull(beerGateway);
    }

    @Override
    public CreateBeerOutput execute(final CreateBeerCommand aCommand) {
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

        aBeer.validate(new ThrowsValidationHandler());

        return CreateBeerOutput.from(beerGateway.create(aBeer));
    }
}
