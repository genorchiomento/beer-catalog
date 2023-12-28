package io.github.genorchiomento.beer.catalog.infrastructure.beer;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.MySQLGatewayTest;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
public class BeerMySQLGatewayTest {

    @Autowired
    private BeerMySQLGateway gateway;

    @Autowired
    private BeerRepository repository;

    @Test
    public void givenAValidBeer_whenCallsCreate_shouldReturnANewBeer() {
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

        Assertions.assertEquals(0, repository.count());

        final var actualBeer = gateway.create(aBeer);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertEquals(aBeer.getId(), actualBeer.getId());
        Assertions.assertEquals(expectedName, actualBeer.getName());
        Assertions.assertEquals(expectedStyle, actualBeer.getStyle());
        Assertions.assertEquals(expectedOrigin, actualBeer.getOrigin());
        Assertions.assertEquals(expectedIbu, actualBeer.getIbu());
        Assertions.assertEquals(expectedAbv, actualBeer.getAbv());
        Assertions.assertEquals(expectedColor, actualBeer.getColor());
        Assertions.assertEquals(expectedIngredients, actualBeer.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualBeer.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualBeer.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualBeer.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualBeer.getCreatedAt());
        Assertions.assertEquals(aBeer.getUpdatedAt(), actualBeer.getUpdatedAt());
        Assertions.assertEquals(aBeer.getDeletedAt(), actualBeer.getDeletedAt());
        Assertions.assertNull(actualBeer.getDeletedAt());

        final var actualEntity = repository.findById(aBeer.getId().getValue()).get();

        Assertions.assertEquals(aBeer.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedStyle, actualEntity.getStyle());
        Assertions.assertEquals(expectedOrigin, actualEntity.getOrigin());
        Assertions.assertEquals(expectedIbu, actualEntity.getIbu());
        Assertions.assertEquals(expectedAbv, actualEntity.getAbv());
        Assertions.assertEquals(expectedColor, actualEntity.getColor());
        Assertions.assertEquals(expectedIngredients, actualEntity.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualEntity.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualEntity.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualEntity.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aBeer.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aBeer.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }
}


