package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.IntegrationTest;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@IntegrationTest
public class CreateBeerUseCaseIT {

    @Autowired
    private CreateBeerUseCase useCase;

    @Autowired
    private BeerRepository repository;

    @SpyBean
    private BeerGateway gateway;

    @Test
    public void givenAValidCommand_WhenCreateBeer_ThenShouldReturnBeerId() {

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

        Assertions.assertEquals(0, repository.count());

        final var aCommand = CreateBeerCommand.with(
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

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Assertions.assertEquals(1, repository.count());

        final var actualBeer = repository.findById(actualOutput.id().getValue()).get();

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
        Assertions.assertNotNull(actualBeer.getColor());
        Assertions.assertNotNull(actualBeer.getUpdatedAt());
        Assertions.assertNull(actualBeer.getDeletedAt());
    }

    @Test
    public void givenAnInvalidName_whenCreateABeer_thenShouldReturnDomainException() {

        final String expectedName = null;
        final var expectedStyle = StyleEnum.LAGER;
        final var expectedOrigin = "Holanda";
        final var expectedIbu = 20.0;
        final var expectedAbv = 5.0;
        final var expectedColor = ColorEnum.CLARA;
        final var expectedIngredients = "Água, Malte e Lúpulo";
        final var expectedFlavorDescription = "Suave e refrescante";
        final var expectedAromaDescription = "Cítrico e maltado";
        final var expectedActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        Assertions.assertEquals(0, repository.count());

        final var aCommand = CreateBeerCommand.with(
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

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Assertions.assertEquals(0, repository.count());

        verify(gateway, times(0)).create(any());
    }

    @Test
    public void givenAValidCommandWithInactiveBeer_WhenCreateBeer_ThenShouldReturnInactiveBeerId() {

        final var expectedName = "Heineken";
        final var expectedStyle = StyleEnum.LAGER;
        final var expectedOrigin = "Holanda";
        final var expectedIbu = 20.0;
        final var expectedAbv = 5.0;
        final var expectedColor = ColorEnum.CLARA;
        final var expectedIngredients = "Água, Malte e Lúpulo";
        final var expectedFlavorDescription = "Suave e refrescante";
        final var expectedAromaDescription = "Cítrico e maltado";
        final var expectedActive = false;

        Assertions.assertEquals(0, repository.count());

        final var aCommand = CreateBeerCommand.with(
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

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Assertions.assertEquals(1, repository.count());

        final var actualBeer = repository.findById(actualOutput.id().getValue()).get();

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
        Assertions.assertNotNull(actualBeer.getCreatedAt());
        Assertions.assertNotNull(actualBeer.getUpdatedAt());
        Assertions.assertNotNull(actualBeer.getDeletedAt());
    }

    @Test
    public void givenAValidName_whenThrowsException_thenShouldReturnAException() {

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
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateBeerCommand.with(
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

        Mockito.doThrow(new IllegalStateException(expectedErrorMessage))
                .when(gateway).create(any());

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
    }
}
