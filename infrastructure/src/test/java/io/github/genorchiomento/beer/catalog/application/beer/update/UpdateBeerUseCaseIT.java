package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.IntegrationTest;
import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.exceptions.NotFoundException;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerJpaEntity;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@IntegrationTest
public class UpdateBeerUseCaseIT {

    @Autowired
    private UpdateBeerUseCase useCase;

    @Autowired
    private BeerRepository repository;

    @SpyBean
    private BeerGateway gateway;

    @Test
    public void givenAValidCommand_WhenUpdateBeer_ThenShouldReturnBeerId() {

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
        final var expectedId = aBeer.getId();

        save(aBeer);

        final var aCommand = UpdateBeerCommand.with(
                expectedId.getValue(),
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

        Assertions.assertEquals(1, repository.count());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        final var actualBeer = repository.findById(expectedId.getValue()).get();

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
        Assertions.assertTrue(aBeer.getUpdatedAt().isBefore(actualBeer.getUpdatedAt()));
        Assertions.assertNull(actualBeer.getDeletedAt());
    }

    @Test
    public void givenAnInvalidName_whenUpdateABeer_thenShouldReturnDomainException() {
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
        final var expectedId = aBeer.getId();

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        save(aBeer);

        final var aCommand = UpdateBeerCommand.with(
                expectedId.getValue(),
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

        verify(gateway, times(0)).update(any());
    }

    @Test
    public void givenAValidInactiveCommand_WhenUpdateBeer_ThenShouldReturnInactivedBeerId() {

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
        final var expectedId = aBeer.getId();

        save(aBeer);

        final var aCommand = UpdateBeerCommand.with(
                expectedId.getValue(),
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

        Assertions.assertTrue(aBeer.isActive());
        Assertions.assertNull(aBeer.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        final var actualBeer = repository.findById(expectedId.getValue()).get();

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
        Assertions.assertTrue(aBeer.getUpdatedAt().isBefore(actualBeer.getUpdatedAt()));
        Assertions.assertNotNull(actualBeer.getDeletedAt());
    }

    @Test
    public void givenAValidName_whenThrowsException_thenShouldReturnAException() {

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
        final var expectedId = aBeer.getId();

        save(aBeer);

        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateBeerCommand.with(
                expectedId.getValue(),
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

        doThrow(new IllegalStateException(expectedErrorMessage))
                .when(gateway).update(any());

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        final var actualBeer = repository.findById(expectedId.getValue()).get();

        Assertions.assertEquals(aBeer.getName(), actualBeer.getName());
        Assertions.assertEquals(aBeer.getStyle(), actualBeer.getStyle());
        Assertions.assertEquals(aBeer.getOrigin(), actualBeer.getOrigin());
        Assertions.assertEquals(aBeer.getIbu(), actualBeer.getIbu());
        Assertions.assertEquals(aBeer.getAbv(), actualBeer.getAbv());
        Assertions.assertEquals(aBeer.getColor(), actualBeer.getColor());
        Assertions.assertEquals(aBeer.getIngredients(), actualBeer.getIngredients());
        Assertions.assertEquals(aBeer.getFlavorDescription(), actualBeer.getFlavorDescription());
        Assertions.assertEquals(aBeer.getAromaDescription(), actualBeer.getAromaDescription());
        Assertions.assertEquals(aBeer.isActive(), actualBeer.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualBeer.getCreatedAt());
        Assertions.assertEquals(aBeer.getUpdatedAt(), actualBeer.getUpdatedAt());
        Assertions.assertEquals(aBeer.getDeletedAt(), actualBeer.getDeletedAt());
    }

    @Test
    public void givenACommandWithInvalidID_whenUpdateABeer_thenShouldReturnNotFoundException() {

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
        final var expectedId = "123";

        final var expectedErrorMessage = "Beer with ID 123 was not found";
        final var expectedErrorCount = 1;

        final var aCommand = UpdateBeerCommand.with(
                expectedId,
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

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

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
