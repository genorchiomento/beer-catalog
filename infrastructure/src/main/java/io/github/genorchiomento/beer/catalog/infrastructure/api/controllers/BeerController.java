package io.github.genorchiomento.beer.catalog.infrastructure.api.controllers;

import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerCommand;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.delete.DeleteBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.GetBeerByIdUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.list.ListBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.update.UpdateBeerCommand;
import io.github.genorchiomento.beer.catalog.application.beer.update.UpdateBeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.update.UpdateBeerUseCase;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.github.genorchiomento.beer.catalog.infrastructure.api.BeerAPI;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerListResponse;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerResponse;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.CreateBeerRequest;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.UpdateBeerRequest;
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
    private final UpdateBeerUseCase updateBeerUseCase;
    private final DeleteBeerUseCase deleteBeerUseCase;
    private final ListBeerUseCase listBeerUseCase;

    public BeerController(
            final CreateBeerUseCase createBeerUseCase,
            final GetBeerByIdUseCase getBeerByIdUseCase,
            final UpdateBeerUseCase updateBeerUseCase,
            final DeleteBeerUseCase deleteBeerUseCase,
            final ListBeerUseCase listBeerUseCase
    ) {
        this.createBeerUseCase = Objects.requireNonNull(createBeerUseCase);
        this.getBeerByIdUseCase = Objects.requireNonNull(getBeerByIdUseCase);
        this.updateBeerUseCase = Objects.requireNonNull(updateBeerUseCase);
        this.deleteBeerUseCase = Objects.requireNonNull(deleteBeerUseCase);
        this.listBeerUseCase = Objects.requireNonNull(listBeerUseCase);
    }

    @Override
    public ResponseEntity<?> createBeer(final CreateBeerRequest input) {
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
    public Pagination<BeerListResponse> listBeers(
            final String search,
            final int page,
            final int perPage,
            final String sort,
            final String direction
    ) {
        return listBeerUseCase.execute(new BeerSearchQuery(page, perPage, search, sort, direction))
                .map(BeerApiPresenter::present);
    }

    @Override
    public BeerResponse getById(final String id) {
        return BeerApiPresenter.present(getBeerByIdUseCase.execute(id));
    }

    @Override
    public ResponseEntity<?> updateById(
            final String id,
            final UpdateBeerRequest input
    ) {
        final var aCommand = UpdateBeerCommand.with(
                id,
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

        final Function<UpdateBeerOutput, ResponseEntity<?>> onSuccess = ResponseEntity::ok;

        return updateBeerUseCase.execute(aCommand)
                .fold(onError, onSuccess);
    }

    @Override
    public void deleteById(final String id) {
        deleteBeerUseCase.execute(id);
    }
}
