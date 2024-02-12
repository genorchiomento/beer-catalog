package io.github.genorchiomento.beer.catalog.infrastructure.beer.models;

import io.github.genorchiomento.beer.catalog.JacksonTest;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.model.UpdateBeerRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

@JacksonTest
public class UpdateBeerRequestTest {

    @Autowired
    private JacksonTester<UpdateBeerRequest> json;

    @Test
    public void testUnmarshall() throws Exception {

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

        final var json = """
                {
                  "name": "%s",
                  "style": "%s",
                  "origin": "%s",
                  "ibu": "%s",
                  "abv": "%s",
                  "color": "%s",
                  "ingredients": "%s",
                  "flavor_description": "%s",
                  "aroma_description": "%s",
                  "is_active": %s
                }
                """.formatted(
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

        final var actualJson = this.json.parse(json);

        Assertions.assertThat(actualJson)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("style", expectedStyle)
                .hasFieldOrPropertyWithValue("origin", expectedOrigin)
                .hasFieldOrPropertyWithValue("ibu", expectedIbu)
                .hasFieldOrPropertyWithValue("abv", expectedAbv)
                .hasFieldOrPropertyWithValue("color", expectedColor)
                .hasFieldOrPropertyWithValue("ingredients", expectedIngredients)
                .hasFieldOrPropertyWithValue("flavorDescription", expectedFlavorDescription)
                .hasFieldOrPropertyWithValue("aromaDescription", expectedAromaDescription)
                .hasFieldOrPropertyWithValue("active", expectedActive);
    }
}
