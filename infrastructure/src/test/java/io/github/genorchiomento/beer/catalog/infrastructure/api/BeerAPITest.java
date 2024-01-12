package io.github.genorchiomento.beer.catalog.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.genorchiomento.beer.catalog.ControllerTest;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.CreateBeerApiInput;
import io.vavr.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

@ControllerTest(controllers = BeerAPI.class)
public class BeerAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateBeerUseCase useCase;

    @Test
    public void givenAValidCommand_WhenCreateBeer_ThenShouldReturnBeerId() throws Exception {

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

        final var anInput = new CreateBeerApiInput(
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

        Mockito.when(useCase.execute(Mockito.any()))
                .thenReturn(API.Right(CreateBeerOutput.from(BeerID.from("123"))));

        final var request = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        MockMvcResultMatchers.header().string("Location", "/beers/123")
                );

        Mockito.verify(useCase, Mockito.times(1)).execute(Mockito.argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                        && Objects.equals(expectedStyle, cmd.style())
                        && Objects.equals(expectedOrigin, cmd.origin())
                        && Objects.equals(expectedIbu, cmd.ibu())
                        && Objects.equals(expectedAbv, cmd.abv())
                        && Objects.equals(expectedColor, cmd.color())
                        && Objects.equals(expectedIngredients, cmd.ingredients())
                        && Objects.equals(expectedFlavorDescription, cmd.flavorDescription())
                        && Objects.equals(expectedAromaDescription, cmd.aromaDescription())
                        && Objects.equals(expectedActive, cmd.isActive())
        ));
    }
}
