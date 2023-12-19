package io.github.genorchiomento.beer.catalog.application.beer.delete;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteBeerUseCaseTest {

    @InjectMocks
    private DefaultDeleteBeerUseCase useCase;

    @Mock
    private BeerGateway beerGateway;

    @BeforeEach
    void cleanUp() {
        reset(beerGateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste passando um ID inválido
    // 3. Teste simulando um erro generico vindo do gateway

    @Test
    public void givenAValidID_whenCallsDeleteBeer_thenShouldBeOK() {
        final var aBeer = Beer.newBeer(
                "Heine",
                StyleEnum.LAGER,
                null,
                20.0,
                5.0,
                ColorEnum.CLARA,
                null,
                null,
                null,
                true
        );

        final var expectedId = aBeer.getId();

        doNothing().when(beerGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(beerGateway, times(1)).deleteById(eq(expectedId));

    }

    @Test
    public void givenAnInvalidID_whenCallsDeleteBeer_thenShouldBeOK() {
        final var expectedId = BeerID.from("123");

        doNothing().when(beerGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(beerGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void givenAValidID_whenGatewayThrowsError_thenShouldReturnException() {
        final var aBeer = Beer.newBeer(
                "Heine",
                StyleEnum.LAGER,
                null,
                20.0,
                5.0,
                ColorEnum.CLARA,
                null,
                null,
                null,
                true
        );

        final var expectedId = aBeer.getId();

        doThrow(new IllegalStateException("Gateway error")).when(beerGateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(beerGateway, times(1)).deleteById(eq(expectedId));
    }
}
