package io.github.genorchiomento.beer.catalog.infrastructure.api.controllers;

import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;
import io.github.genorchiomento.beer.catalog.infrastructure.api.BeerAPI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class BeerController implements BeerAPI {

    private final CreateBeerUseCase createBeerUseCase;

    public BeerController(final CreateBeerUseCase createBeerUseCase) {
        this.createBeerUseCase = Objects.requireNonNull(createBeerUseCase);
    }

    @Override
    public ResponseEntity<?> createBeer() {
        return null;
    }

    @Override
    public Pagination<?> listBeers(String search, int page, int perPage, String sort, String direction) {
        return null;
    }
}
