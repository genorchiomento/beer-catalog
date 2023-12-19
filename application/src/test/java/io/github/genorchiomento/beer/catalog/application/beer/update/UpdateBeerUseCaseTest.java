package io.github.genorchiomento.beer.catalog.application.beer.update;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
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
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateBeerUseCaseTest {

    @InjectMocks
    private DefaultUpdateBeerUseCase useCase;

    @Mock
    private BeerGateway beerGateway;

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
}
