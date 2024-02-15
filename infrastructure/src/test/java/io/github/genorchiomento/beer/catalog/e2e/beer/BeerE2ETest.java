package io.github.genorchiomento.beer.catalog.e2e.beer;

import io.github.genorchiomento.beer.catalog.E2ETest;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerResponse;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.CreateBeerRequest;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import io.github.genorchiomento.beer.catalog.infrastructure.configuration.json.Json;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@E2ETest
@Testcontainers
public class BeerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BeerRepository beerRepository;

    @Container
    private static final MySQLContainer MYSQL_CONTAINER = new MySQLContainer("mysql:latest")
            .withPassword("123456")
            .withUsername("root")
            .withDatabaseName("adm_beer");

    @DynamicPropertySource
    public static void setDatasourceProperties(final DynamicPropertyRegistry registry) {
        registry.add("mysql.port", () -> MYSQL_CONTAINER.getMappedPort(3306));
    }

    @Test
    public void asACatalogAdminIShouldBeAbleToCreateNewBeerWithValidValues() throws Exception {
        Assertions.assertTrue(MYSQL_CONTAINER.isRunning());

        Assertions.assertEquals(0, beerRepository.count());

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

        final var actualId = givenABeer(
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

        final var actualBeer = retrieveABeer(actualId.getValue());

        Assertions.assertEquals(expectedName, actualBeer.name());
        Assertions.assertEquals(expectedStyle, actualBeer.style());
        Assertions.assertEquals(expectedOrigin, actualBeer.origin());
        Assertions.assertEquals(expectedIbu, actualBeer.ibu());
        Assertions.assertEquals(expectedAbv, actualBeer.abv());
        Assertions.assertEquals(expectedColor, actualBeer.color());
        Assertions.assertEquals(expectedIngredients, actualBeer.ingredients());
        Assertions.assertEquals(expectedFlavorDescription, actualBeer.flavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualBeer.aromaDescription());
        Assertions.assertEquals(expectedActive, actualBeer.active());
        Assertions.assertNotNull(actualBeer.createdAt());
        Assertions.assertNotNull(actualBeer.updatedAt());
        Assertions.assertNull(actualBeer.deletedAt());
    }

    private BeerID givenABeer(
            final String aName,
            final StyleEnum aStyle,
            final String anOrigin,
            final Double anIbu,
            final Double anAbv,
            final ColorEnum aColor,
            final String anIngredients,
            final String aFlavorDescription,
            final String anAromaDescription,
            final boolean isActive
    ) throws Exception {
        final var createBeerRequest = new CreateBeerRequest(
                aName,
                aStyle,
                anOrigin,
                anIbu,
                anAbv,
                aColor,
                anIngredients,
                aFlavorDescription,
                anAromaDescription,
                isActive
        );

        final var aRequest = MockMvcRequestBuilders.post("/beers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Json.writeValueAsString(createBeerRequest));

        final var actualId = mockMvc.perform(aRequest)
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn()
                .getResponse().getHeader("Location")
                .replace("/beers/", "");

        return BeerID.from(actualId);
    }

    private BeerResponse retrieveABeer(final String id) throws Exception {

        final var aRequest = MockMvcRequestBuilders.get("/beers/" + id)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        final var json = mockMvc.perform(aRequest)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        return Json.readValue(json, BeerResponse.class);
    }
}