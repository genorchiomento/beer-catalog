package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateBeerUseCaseTest {

    @InjectMocks
    private DefaultCreateBeerUseCase useCase;

    @Mock
    private BeerGateway beerGateway;

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (name)
    // 3. Teste criando uma cerveja inativa
    // 4. Teste simulando um erro generico vindo do gateway

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

        when(beerGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(beerGateway, times(1)).create(argThat(aBeer ->
                        Objects.equals(expectedName, aBeer.getName())
                                && Objects.equals(expectedStyle, aBeer.getStyle())
                                && Objects.equals(expectedOrigin, aBeer.getOrigin())
                                && Objects.equals(expectedIbu, aBeer.getIbu())
                                && Objects.equals(expectedAbv, aBeer.getAbv())
                                && Objects.equals(expectedColor, aBeer.getColor())
                                && Objects.equals(expectedIngredients, aBeer.getIngredients())
                                && Objects.equals(expectedFlavorDescription, aBeer.getFlavorDescription())
                                && Objects.equals(expectedAromaDescription, aBeer.getAromaDescription())
                                && Objects.equals(expectedActive, aBeer.isActive())
                                && Objects.nonNull(aBeer.getId())
                                && Objects.nonNull(aBeer.getCreatedAt())
                                && Objects.nonNull(aBeer.getUpdatedAt())
                                && Objects.isNull(aBeer.getDeletedAt())
                ));
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

        verify(beerGateway, times(0)).create(any());
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

        when(beerGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(beerGateway, times(1)).create(argThat(aBeer ->
                Objects.equals(expectedName, aBeer.getName())
                        && Objects.equals(expectedStyle, aBeer.getStyle())
                        && Objects.equals(expectedOrigin, aBeer.getOrigin())
                        && Objects.equals(expectedIbu, aBeer.getIbu())
                        && Objects.equals(expectedAbv, aBeer.getAbv())
                        && Objects.equals(expectedColor, aBeer.getColor())
                        && Objects.equals(expectedIngredients, aBeer.getIngredients())
                        && Objects.equals(expectedFlavorDescription, aBeer.getFlavorDescription())
                        && Objects.equals(expectedAromaDescription, aBeer.getAromaDescription())
                        && Objects.equals(expectedActive, aBeer.isActive())
                        && Objects.nonNull(aBeer.getId())
                        && Objects.nonNull(aBeer.getCreatedAt())
                        && Objects.nonNull(aBeer.getUpdatedAt())
                        && Objects.nonNull(aBeer.getDeletedAt())
        ));
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

        when(beerGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(beerGateway, times(1)).create(argThat(aBeer ->
                Objects.equals(expectedName, aBeer.getName())
                        && Objects.equals(expectedStyle, aBeer.getStyle())
                        && Objects.equals(expectedOrigin, aBeer.getOrigin())
                        && Objects.equals(expectedIbu, aBeer.getIbu())
                        && Objects.equals(expectedAbv, aBeer.getAbv())
                        && Objects.equals(expectedColor, aBeer.getColor())
                        && Objects.equals(expectedIngredients, aBeer.getIngredients())
                        && Objects.equals(expectedFlavorDescription, aBeer.getFlavorDescription())
                        && Objects.equals(expectedAromaDescription, aBeer.getAromaDescription())
                        && Objects.equals(expectedActive, aBeer.isActive())
                        && Objects.nonNull(aBeer.getId())
                        && Objects.nonNull(aBeer.getCreatedAt())
                        && Objects.nonNull(aBeer.getUpdatedAt())
                        && Objects.isNull(aBeer.getDeletedAt())
        ));
    }
}
