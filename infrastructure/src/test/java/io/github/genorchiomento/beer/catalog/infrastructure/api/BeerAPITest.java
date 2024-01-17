package io.github.genorchiomento.beer.catalog.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.genorchiomento.beer.catalog.ControllerTest;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.validation.Error;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.CreateBeerApiInput;
import io.vavr.API;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                .thenReturn(API.Right(CreateBeerOutput.from("123")));

        final var request = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/beers/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

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

    @Test
    public void givenAnInvalidName_whenCreateABeer_thenShouldReturnNotification() throws Exception {

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
        final var expectedMessage = "'name' should not be null";

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
                .thenReturn(API.Left(Notification.create(new Error(expectedMessage))));

        final var request = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", Matchers.equalTo(expectedMessage)));

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

    @Test
    public void givenAnInvalidCommand_whenCreateABeer_thenShouldReturnDomainException() throws Exception {

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
        final var expectedMessage = "'name' should not be null";

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
                .thenReturn(API.Left(Notification.create(new Error(expectedMessage))));

        Mockito.when(useCase.execute(Mockito.any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        final var request = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.equalTo(expectedMessage)))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", Matchers.equalTo(expectedMessage)));

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
