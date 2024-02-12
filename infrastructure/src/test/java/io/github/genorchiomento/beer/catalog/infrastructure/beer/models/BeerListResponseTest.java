package io.github.genorchiomento.beer.catalog.infrastructure.beer.models;

import io.github.genorchiomento.beer.catalog.JacksonTest;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.BeerListResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

@JacksonTest
public class BeerListResponseTest {

    @Autowired
    private JacksonTester<BeerListResponse> json;

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
        final var expectedDeletedAt = Instant.now();

        final var response = new BeerListResponse(
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
                .hasJsonPathValue("$.deleted_at", expectedDeletedAt.toString());
    }
}
