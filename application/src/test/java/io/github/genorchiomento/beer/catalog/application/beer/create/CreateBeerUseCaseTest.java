package io.github.genorchiomento.beer.catalog.application.beer.create;

import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

public class CreateBeerUseCaseTest {
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

        final BeerGateway beerGateway = mock(BeerGateway.class);

        when(beerGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateBeerUseCase(beerGateway);

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(beerGateway, times(1)).create(argThat(aBeer ->
                        Objects.equals(expectedName, aBeer.getName())
                                && Objects.equals(expectedOrigin, aBeer.getOrigin())
                                && Objects.equals(expectedActive, aBeer.isActive())
                                && Objects.nonNull(aBeer.getId())
                                && Objects.nonNull(aBeer.getCreatedAt())
                                && Objects.nonNull(aBeer.getUpdatedAt())
                                && Objects.isNull(aBeer.getDeletedAt())
                ));
    }
}
