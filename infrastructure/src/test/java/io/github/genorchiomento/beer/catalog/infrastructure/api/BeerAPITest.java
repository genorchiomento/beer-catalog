package io.github.genorchiomento.beer.catalog.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.genorchiomento.beer.catalog.ControllerTest;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.BeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.GetBeerByIdUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.update.UpdateBeerOutput;
import io.github.genorchiomento.beer.catalog.application.beer.update.UpdateBeerUseCase;
import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.domain.exceptions.DomainException;
import io.github.genorchiomento.beer.catalog.domain.exceptions.NotFoundException;
import io.github.genorchiomento.beer.catalog.domain.validation.Error;
import io.github.genorchiomento.beer.catalog.domain.validation.handler.Notification;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.CreateBeerApiInput;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.UpdateBeerApiInput;
import io.vavr.API;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ControllerTest(controllers = BeerAPI.class)
public class BeerAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CreateBeerUseCase createBeerUseCase;

    @MockBean
    private GetBeerByIdUseCase getBeerByIdUseCase;

    @MockBean
    private UpdateBeerUseCase updateBeerUseCase;

    @Test
    public void givenAValidCommand_WhenCreateBeer_ThenShouldReturnBeerId() throws Exception {
        //given
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

        when(createBeerUseCase.execute(any()))
                .thenReturn(API.Right(CreateBeerOutput.from("123")));

        //when
        final var request = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(status().isCreated())
                .andExpect(header().string("Location", "/beers/123"))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo("123")));

        verify(createBeerUseCase, times(1)).execute(argThat(cmd ->
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
        //given
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

        when(createBeerUseCase.execute(any()))
                .thenReturn(API.Left(Notification.create(new Error(expectedMessage))));

        //when
        final var request = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", Matchers.equalTo(expectedMessage)));

        verify(createBeerUseCase, times(1)).execute(argThat(cmd ->
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

        //given
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

        when(createBeerUseCase.execute(any()))
                .thenReturn(API.Left(Notification.create(new Error(expectedMessage))));

        when(createBeerUseCase.execute(any()))
                .thenThrow(DomainException.with(new Error(expectedMessage)));

        //when
        final var request = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(anInput));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(status().isUnprocessableEntity())
                .andExpect(header().string("Location", Matchers.nullValue()))
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.message", Matchers.equalTo(expectedMessage)))
                .andExpect(jsonPath("$.errors", Matchers.hasSize(1)))
                .andExpect(jsonPath("$.errors[0].message", Matchers.equalTo(expectedMessage)));

        verify(createBeerUseCase, times(1)).execute(argThat(cmd ->
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
    public void givenAValidId_whenCallsGetBeer_thenShouldReturnBeer() throws Exception {
        //given
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

        final var expectedId = aBeer.getId().getValue();

        when(getBeerByIdUseCase.execute(any()))
                .thenReturn(BeerOutput.from(aBeer));

        //when
        final var request =
                MockMvcRequestBuilders.get("/beers/{id}", expectedId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(status().isOk())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id", equalTo(expectedId)))
                .andExpect(jsonPath("$.name", equalTo(expectedName)))
                .andExpect(jsonPath("$.style", equalTo(expectedStyle.name())))
                .andExpect(jsonPath("$.origin", equalTo(expectedOrigin)))
                .andExpect(jsonPath("$.ibu", equalTo(expectedIbu)))
                .andExpect(jsonPath("$.abv", equalTo(expectedAbv)))
                .andExpect(jsonPath("$.color", equalTo(expectedColor.name())))
                .andExpect(jsonPath("$.ingredients", equalTo(expectedIngredients)))
                .andExpect(jsonPath("$.flavor_description", equalTo(expectedFlavorDescription)))
                .andExpect(jsonPath("$.aroma_description", equalTo(expectedAromaDescription)))
                .andExpect(jsonPath("$.is_active", equalTo(expectedActive)))
                .andExpect(jsonPath("$.created_at", equalTo(aBeer.getCreatedAt().toString())))
                .andExpect(jsonPath("$.updated_at", equalTo(aBeer.getUpdatedAt().toString())))
                .andExpect(jsonPath("$.deleted_at", equalTo(aBeer.getDeletedAt())));

        verify(getBeerByIdUseCase, times(1)).execute(eq(expectedId));
    }

    @Test
    public void givenAnInvalidId_whenCallsGetBeer_thenShouldReturnNotFound() throws Exception {
        //given
        final var expectedErrorMessage = "Beer with ID 123 was not found";
        final var expectedId = BeerID.from("123");

        when(getBeerByIdUseCase.execute(any()))
                .thenThrow(NotFoundException.with(Beer.class, expectedId));

        //when
        final var request =
                MockMvcRequestBuilders.get("/beers/{id}", expectedId.getValue())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON);

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", equalTo(expectedErrorMessage)));
    }

    @Test
    public void givenAValidCommand_WhenUpdateBeer_ThenShouldReturnBeerId() throws Exception {
        //given
        final var expectedId = "123";
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

        final var aCommand = new UpdateBeerApiInput(
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

        when(updateBeerUseCase.execute(any()))
                .thenReturn(API.Right(UpdateBeerOutput.from(expectedId)));

        //when
        final var request =
                MockMvcRequestBuilders.put("/beers/{id}", expectedId)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(aCommand));

        final var response = mvc.perform(request)
                .andDo(MockMvcResultHandlers.print());

        //then
        response.andExpect(status().isNoContent())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE));

        verify(updateBeerUseCase, times(1)).execute(argThat(cmd ->
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
