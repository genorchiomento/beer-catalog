package io.github.genorchiomento.beer.catalog.application.beer.retrieve.list;

import io.github.genorchiomento.beer.catalog.IntegrationTest;
import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerJpaEntity;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Stream;

@IntegrationTest
public class ListBeersUseCaseIT {

    @Autowired
    private ListBeerUseCase useCase;

    @Autowired
    private BeerRepository repository;

    @BeforeEach
    void MockUp() {
        final var beers = Stream.of(
                        Beer.newBeer(
                                "Skol",
                                null,
                                null,
                                20.0,
                                5.0,
                                null,
                                null,
                                null,
                                null,
                                true
                        ),

                        Beer.newBeer(
                                "Colorado",
                                null,
                                null,
                                20.0,
                                5.0,
                                null,
                                null,
                                null,
                                null,
                                true
                        ),

                        Beer.newBeer(
                                "Bragantina",
                                null,
                                "Bragança Paulista",
                                20.0,
                                5.0,
                                null,
                                null,
                                null,
                                null,
                                true
                        ),

                        Beer.newBeer(
                                "3 Americas",
                                null,
                                "Itatiba",
                                20.0,
                                5.0,
                                null,
                                null,
                                null,
                                null,
                                true
                        )
                )
                .map(BeerJpaEntity::from)
                .toList();

        repository.saveAllAndFlush(beers);
    }

    @Test
    public void givenAValidTerm_whenTermDoesNotMatchsPrePersisted_shouldReturnEmptyPage() {

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "Netflix";
        final var expectedSort = "name";
        final var expectedDirection = "asc";
        final var expectedItemsCount = 0;
        final var expectedTotal = 0;

        final var query = new BeerSearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
    }

    @ParameterizedTest
    @CsvSource({
            "Sk,0,10,1,1,Skol",
            "COLO,0,10,1,1,Colorado",
            "brAga,0,10,1,1,Bragantina",
            "3,0,10,1,1,3 Americas",
            "Itatiba,0,10,1,1,3 Americas"
    })
    public void givenAValidTerm_whenCallsListBeers_shouldReturnBeersFiltered(
            final String expectedTerms,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedBeerName
    ) {
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var query = new BeerSearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedBeerName, actualResult.items().get(0).name());
    }


    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,4,4,3 Americas",
            "name,desc,0,10,4,4,Skol",
            "createdAt,asc,0,10,4,4,Skol",
            "createdAt,desc,0,10,4,4,3 Americas"
    })
    public void givenAValidSortAndDirection_whenCallsListbeers_thenShouldReturnBeersOrdered(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedBeerName
    ) {
        final var expectedTerms = "";

        final var query = new BeerSearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedBeerName, actualResult.items().get(0).name());
    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,4,3 Americas;Bragantina",
            "1,2,2,4,Colorado;Skol"
    })
    public void givenAValidPage_whenCallsListBeers_shouldReturnCategoriesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedBeersName
    ) {
        final var expectedTerms = "";
        final var expectedSort = "name";
        final var expectedDirection = "asc";

        final var query = new BeerSearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );

        final var actualResult = useCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String expectedName : expectedBeersName.split(";")) {
            final var actualName = actualResult.items().get(index).name();
            Assertions.assertEquals(expectedName, actualName);
            index++;
        }
    }
}
