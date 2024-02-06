package io.github.genorchiomento.beer.catalog.infrastructure.api.controllers;

import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerCommand;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.GetBeerByIdUseCase;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.github.genorchiomento.beer.catalog.infrastructure.api.BeerAPI;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerApiOutput;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.CreateBeerApiInput;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.presenter.BeerApiPresenter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Objects;
import java.util.function.Function;

@RestController
public class BeerController implements BeerAPI {

    private final CreateBeerUseCase createBeerUseCase;
    private final GetBeerByIdUseCase getBeerByIdUseCase;

    public BeerController(final CreateBeerUseCase createBeerUseCase, GetBeerByIdUseCase getBeerByIdUseCase) {
        this.createBeerUseCase = Objects.requireNonNull(createBeerUseCase);
        this.getBeerByIdUseCase = Objects.requireNonNull(getBeerByIdUseCase);
    }

    @Override
    public ResponseEntity<?> createBeer(final CreateBeerApiInput input) {
        final var aCommand = CreateBeerCommand.with(
                input.name(),
                input.style(),
                input.origin(),
                input.ibu(),
                input.abv(),
                input.color(),
                input.ingredients(),
                input.flavorDescription(),
                input.aromaDescription(),
                input.active() != null ? input.active() : true
        );

        final Function<Notification, ResponseEntity<?>> onError = notification ->
                ResponseEntity.unprocessableEntity().body(notification);

        final Function<CreateBeerOutput, ResponseEntity<?>> onSuccess = output ->
                ResponseEntity.created(URI.create("/beers/" + output.id())).body(output);

        return createBeerUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public Pagination<?> listBeers(String search, int page, int perPage, String sort, String direction) {
        return null;
    }

    @Override
    public BeerApiOutput getById(final String id) {
        return BeerApiPresenter.present(getBeerByIdUseCase.execute(id));
    }
}
