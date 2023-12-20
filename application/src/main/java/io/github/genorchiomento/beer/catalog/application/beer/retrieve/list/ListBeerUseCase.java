package io.github.genorchiomento.beer.catalog.application.beer.retrieve.list;

import io.github.genorchiomento.beer.catalog.application.UseCase;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;

public abstract class ListBeerUseCase extends UseCase<BeerSearchQuery, Pagination<BeerListOutput>> {
}
