package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.exceptions.NotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateBeerUseCaseTest {

    @InjectMocks
    private DefaultUpdateBeerUseCase useCase;

    @Mock
    private BeerGateway beerGateway;

    @BeforeEach
    void cleanUp() {
        reset(beerGateway);
    }

    // 1. Teste do caminho feliz
    // 2. Teste passando uma propriedade inválida (name)
    // 3. Teste atualizando uma cerveja para inativa
    // 4. Teste simulando um erro generico vindo do gateway
    // 5. Teste atualizar beer passando um ID invalido

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

        when(beerGateway.findById(eq(expectedId))).thenReturn(Optional.of(Beer.withClone(aBeer)));
        when(beerGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(beerGateway, times(1)).findById(expectedId);
        verify(beerGateway, times(1)).update(argThat(
                aUpatedBeer -> Objects.equals(expectedName, aUpatedBeer.getName())
                        && Objects.equals(expectedOrigin, aUpatedBeer.getOrigin())
                        && Objects.equals(expectedStyle, aUpatedBeer.getStyle())
                        && Objects.equals(expectedActive, aUpatedBeer.isActive())
                        && Objects.equals(expectedId, aUpatedBeer.getId())
                        && Objects.equals(aBeer.getCreatedAt(), aUpatedBeer.getCreatedAt())
                        && aBeer.getUpdatedAt().isBefore(aUpatedBeer.getUpdatedAt())
                        && Objects.isNull(aUpatedBeer.getDeletedAt())
        ));
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

        when(beerGateway.findById(eq(expectedId))).thenReturn(Optional.of(Beer.withClone(aBeer)));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(beerGateway, times(0)).update(any());
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

        when(beerGateway.findById(eq(expectedId))).thenReturn(Optional.of(Beer.withClone(aBeer)));
        when(beerGateway.update(any())).thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aBeer.isActive());
        Assertions.assertNull(aBeer.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(beerGateway, times(1)).findById(expectedId);
        verify(beerGateway, times(1)).update(argThat(
                aUpatedBeer -> Objects.equals(expectedName, aUpatedBeer.getName())
                        && Objects.equals(expectedOrigin, aUpatedBeer.getOrigin())
                        && Objects.equals(expectedStyle, aUpatedBeer.getStyle())
                        && Objects.equals(expectedActive, aUpatedBeer.isActive())
                        && Objects.equals(expectedId, aUpatedBeer.getId())
                        && Objects.equals(aBeer.getCreatedAt(), aUpatedBeer.getCreatedAt())
                        && aBeer.getUpdatedAt().isBefore(aUpatedBeer.getUpdatedAt())
                        && Objects.nonNull(aUpatedBeer.getDeletedAt())
        ));
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

        when(beerGateway.findById(eq(expectedId))).thenReturn(Optional.of(Beer.withClone(aBeer)));

        when(beerGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        verify(beerGateway, times(1)).update(argThat(
                aUpatedBeer -> Objects.equals(expectedName, aUpatedBeer.getName())
                        && Objects.equals(expectedOrigin, aUpatedBeer.getOrigin())
                        && Objects.equals(expectedStyle, aUpatedBeer.getStyle())
                        && Objects.equals(expectedActive, aUpatedBeer.isActive())
                        && Objects.equals(expectedId, aUpatedBeer.getId())
                        && Objects.equals(aBeer.getCreatedAt(), aUpatedBeer.getCreatedAt())
                        && aBeer.getUpdatedAt().isBefore(aUpatedBeer.getUpdatedAt())
                        && Objects.isNull(aUpatedBeer.getDeletedAt())
        ));
    }

    @Test
    public void givenACommandWithInvalidID_whenUpdateABeer_thenShouldReturnNotFoundException() {

        final String expectedName = "Heineken";
        final var expectedStyle = StyleEnum.LAGER;
        final var expectedOrigin = "Holanda";
        final var expectedIbu = 20.0;
        final var expectedAbv = 5.0;
        final var expectedColor = ColorEnum.CLARA;
        final var expectedIngredients = "Água, Malte e Lúpulo";
        final var expectedFlavorDescription = "Suave e refrescante";
        final var expectedAromaDescription = "Cítrico e maltado";
        final var expectedActive = false;
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

        when(beerGateway.findById(eq(BeerID.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException =
                Assertions.assertThrows(NotFoundException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        verify(beerGateway, times(1)).findById(eq(BeerID.from(expectedId)));
        verify(beerGateway, times(0)).update(any());
    }
}
