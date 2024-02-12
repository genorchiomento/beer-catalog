package io.github.genorchiomento.beer.catalog.infrastructure.beer.models;

import io.github.genorchiomento.beer.catalog.JacksonTest;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
public class BeerResponseTest {

    @Autowired
    private JacksonTester<BeerResponse> json;

    @Test
    public void testMarshall() throws Exception {

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
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var response = new BeerResponse(
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
                expectedActive,
                expectedCreatedAt,
                expectedUpdatedAt,
                expectedDeletedAt
        );


        final var actualJson = this.json.write(response);

        Assertions.assertThat(actualJson)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.style", expectedStyle)
                .hasJsonPathValue("$.origin", expectedOrigin)
                .hasJsonPathValue("$.ibu", expectedIbu)
                .hasJsonPathValue("$.abv", expectedAbv)
                .hasJsonPathValue("$.color", expectedColor)
                .hasJsonPathValue("$.ingredients", expectedIngredients)
                .hasJsonPathValue("$.flavor_description", expectedFlavorDescription)
                .hasJsonPathValue("$.aroma_description", expectedAromaDescription)
                .hasJsonPathValue("$.is_active", expectedActive)
                .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
                .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString())
                .hasJsonPathValue("$.updated_at", expectedUpdatedAt.toString());
    }

    @Test
    public void testUnmarshall() throws Exception {

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
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();
        final var expectedDeletedAt = Instant.now();

        final var json = """
                {
                  "id": "%s",
                  "name": "%s",
                  "style": "%s",
                  "origin": "%s",
                  "ibu": "%s",
                  "abv": "%s",
                  "color": "%s",
                  "ingredients": "%s",
                  "flavor_description": "%s",
                  "aroma_description": "%s",
                  "is_active": %s,
                  "created_at": "%s",
                  "deleted_at": "%s",
                  "updated_at": "%s"
                }
                """.formatted(
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
                expectedActive,
                expectedCreatedAt.toString(),
                expectedDeletedAt.toString(),
                expectedUpdatedAt.toString()
        );

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("id", expectedId)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("style", expectedStyle)
                .hasFieldOrPropertyWithValue("origin", expectedOrigin)
                .hasFieldOrPropertyWithValue("ibu", expectedIbu)
                .hasFieldOrPropertyWithValue("abv", expectedAbv)
                .hasFieldOrPropertyWithValue("color", expectedColor)
                .hasFieldOrPropertyWithValue("ingredients", expectedIngredients)
                .hasFieldOrPropertyWithValue("flavorDescription", expectedFlavorDescription)
                .hasFieldOrPropertyWithValue("aromaDescription", expectedAromaDescription)
                .hasFieldOrPropertyWithValue("active", expectedActive)
                .hasFieldOrPropertyWithValue("createdAt", expectedCreatedAt)
                .hasFieldOrPropertyWithValue("deletedAt", expectedDeletedAt)
                .hasFieldOrPropertyWithValue("updatedAt", expectedUpdatedAt);
    }
}
