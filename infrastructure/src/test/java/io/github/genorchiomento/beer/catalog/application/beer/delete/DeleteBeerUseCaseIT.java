package io.github.genorchiomento.beer.catalog.application.beer.delete;

import io.github.genorchiomento.beer.catalog.IntegrationTest;
import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerJpaEntity;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@IntegrationTest
public class DeleteBeerUseCaseIT {

    @Autowired
    private DeleteBeerUseCase useCase;

    @Autowired
    private BeerRepository repository;

    @SpyBean
    private BeerGateway gateway;

    @Test
    public void givenAValidID_whenCallsDeleteBeer_thenShouldBeOK() {
        final var aBeer = Beer.newBeer(
                "Heineken",
                StyleEnum.LAGER,
                "Holanda",
                20.0,
                5.0,
                ColorEnum.CLARA,
                "Água, Malte e Lúpulo",
                "Suave e refrescante",
                "Cítrico e maltado",
                true
        );

        final var expectedId = aBeer.getId();

        save(aBeer);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenAnInvalidID_whenCallsDeleteBeer_thenShouldBeOK() {
        final var expectedId = BeerID.from("123");

        Assertions.assertEquals(0, repository.count());

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenAValidID_whenGatewayThrowsError_thenShouldReturnException() {
        final var aBeer = Beer.newBeer(
                "Heineken",
                StyleEnum.LAGER,
                "Holanda",
                20.0,
                5.0,
                ColorEnum.CLARA,
                "Água, Malte e Lúpulo",
                "Suave e refrescante",
                "Cítrico e maltado",
                true
        );

        final var expectedId = aBeer.getId();

        doThrow(new IllegalStateException("Gateway error")).when(gateway).deleteById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(gateway, times(1)).deleteById(eq(expectedId));
    }

    private void save(final Beer... aBeer) {
        repository.saveAllAndFlush(
                Arrays.stream(aBeer)
                        .map(BeerJpaEntity::from)
                        .toList()
        );
    }
}
