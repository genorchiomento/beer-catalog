package io.github.genorchiomento.beer.catalog.application.beer.retrieve.list;


import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ListBeersUseCaseTest {

    @InjectMocks
    private DefaultListBeersUseCase useCase;

    @Mock
    private BeerGateway beerGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(beerGateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste retorna vazio
    // 3. Teste simulando um erro generico vindo do gateway

    @Test
    public void givenAValidQuery_whenCallsListBeers_thenShouldReturnBeers() {

        final var beers = List.of(
                Beer.newBeer(
                        "Skol",
                        null,
                        null,
                        null,
                        null,
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
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        true
                )
        );

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var query = new BeerSearchQuery(
                expectedPage,
                expectedPerPage,
                expectedTerms,
                expectedSort,
                expectedDirection
        );


        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, beers.size(), beers);

        final var expectedItemsCount = 2;
        final var expectedResult = expectedPagination.map(BeerListOutput::from);

        when(beerGateway.findAll(eq(query))).thenReturn(expectedPagination);

        final var result = useCase.execute(query);

        Assertions.assertEquals(expectedItemsCount, result.items().size());
        Assertions.assertEquals(expectedResult, result);
        Assertions.assertEquals(expectedPage, result.page());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(beers.size(), result.total());
    }

    @Test
    public void givenAValidQuery_whenHasNoResults_thenShouldReturnEmptyBeers() {

    }

    @Test
    public void givenAValidQuery_whenGatewayThrowsException_thenShouldReturnException() {

    }
}
