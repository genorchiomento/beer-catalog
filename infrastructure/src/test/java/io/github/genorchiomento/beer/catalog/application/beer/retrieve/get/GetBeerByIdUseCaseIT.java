package io.github.genorchiomento.beer.catalog.application.beer.retrieve.get;

import io.github.genorchiomento.beer.catalog.IntegrationTest;
import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.domain.exceptions.NotFoundException;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerJpaEntity;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;

import static org.mockito.Mockito.doThrow;

@IntegrationTest
public class GetBeerByIdUseCaseIT {

    @Autowired
    private GetBeerByIdUseCase useCase;

    @Autowired
    private BeerRepository repository;

    @SpyBean
    private BeerGateway gateway;

    @Test
    public void givenAValidId_whenCallsGetBeer_thenShouldReturnBeer() {
        final var expectedName = "Heineken";
        final var expectedStyle = StyleEnum.LAGER;
        final var expectedOrigin = "Holanda";
        final var expectedIbu = 20.0;
        final var expectedAbv = 5.0;
        final var expectedColor = ColorEnum.CLARA;
        final var expectedIngredients = "Água, Malte e Lúpulo";
        final var expectedFlavorDescription = "Suave e refrescante";
        final var expectedAromaDescription = "Cítrico e maltado";
        final var expectedActive = true;

        final var aBeer = Beer.newBeer(
                expectedName,
                expectedStyle,
                expectedOrigin,
                expectedIbu,
                expectedAbv,
                expectedColor,
                expectedIngredients,
                expectedFlavorDescription,
                expectedAromaDescription,
                expectedActive
        );

        final var expectedId = aBeer.getId();

        save(aBeer);

        final var actualBeer = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(expectedId, actualBeer.id());
        Assertions.assertEquals(expectedName, actualBeer.name());
        Assertions.assertEquals(expectedStyle, actualBeer.style());
        Assertions.assertEquals(expectedOrigin, actualBeer.origin());
        Assertions.assertEquals(expectedIbu, actualBeer.ibu());
        Assertions.assertEquals(expectedAbv, actualBeer.abv());
        Assertions.assertEquals(expectedColor, actualBeer.color());
        Assertions.assertEquals(expectedIngredients, actualBeer.ingredients());
        Assertions.assertEquals(expectedFlavorDescription, actualBeer.flavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualBeer.aromaDescription());
        Assertions.assertEquals(expectedActive, actualBeer.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualBeer.createdAt());
        Assertions.assertEquals(aBeer.getUpdatedAt(), actualBeer.updatedAt());
        Assertions.assertEquals(aBeer.getDeletedAt(), actualBeer.deletedAt());
    }

    @Test
    public void givenAnInvalidId_whenCallsGetBeer_thenShouldReturnNotFound() {
        final var expectedErrorMessage = "Beer with ID 123 was not found";
        final var expectedId = BeerID.from("123");

        final var actualException = Assertions.assertThrows(
                NotFoundException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    public void givenAValidId_whenGatewayThrowsException_thenShouldReturnException() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = BeerID.from("123");

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(gateway).findById(expectedId);

        final var actualException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue())
        );

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    private void save(final Beer... aBeer) {
        repository.saveAllAndFlush(
                Arrays.stream(aBeer)
                        .map(BeerJpaEntity::from)
                        .toList()
        );
    }
}
