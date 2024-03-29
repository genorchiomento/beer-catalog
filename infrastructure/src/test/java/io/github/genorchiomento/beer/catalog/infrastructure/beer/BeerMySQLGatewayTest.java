package io.github.genorchiomento.beer.catalog.infrastructure.beer;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.MySQLGatewayTest;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerJpaEntity;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@MySQLGatewayTest
public class BeerMySQLGatewayTest {

    @Autowired
    private BeerMySQLGateway gateway;

    @Autowired
    private BeerRepository repository;

    @Test
    public void givenAValidBeer_whenCallsCreate_shouldReturnANewBeer() {
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

        Assertions.assertEquals(0, repository.count());

        final var actualBeer = gateway.create(aBeer);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertEquals(aBeer.getId(), actualBeer.getId());
        Assertions.assertEquals(expectedName, actualBeer.getName());
        Assertions.assertEquals(expectedStyle, actualBeer.getStyle());
        Assertions.assertEquals(expectedOrigin, actualBeer.getOrigin());
        Assertions.assertEquals(expectedIbu, actualBeer.getIbu());
        Assertions.assertEquals(expectedAbv, actualBeer.getAbv());
        Assertions.assertEquals(expectedColor, actualBeer.getColor());
        Assertions.assertEquals(expectedIngredients, actualBeer.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualBeer.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualBeer.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualBeer.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualBeer.getCreatedAt());
        Assertions.assertEquals(aBeer.getUpdatedAt(), actualBeer.getUpdatedAt());
        Assertions.assertEquals(aBeer.getDeletedAt(), actualBeer.getDeletedAt());
        Assertions.assertNull(actualBeer.getDeletedAt());

        final var actualEntity = repository.findById(aBeer.getId().getValue()).get();

        Assertions.assertEquals(aBeer.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedStyle, actualEntity.getStyle());
        Assertions.assertEquals(expectedOrigin, actualEntity.getOrigin());
        Assertions.assertEquals(expectedIbu, actualEntity.getIbu());
        Assertions.assertEquals(expectedAbv, actualEntity.getAbv());
        Assertions.assertEquals(expectedColor, actualEntity.getColor());
        Assertions.assertEquals(expectedIngredients, actualEntity.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualEntity.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualEntity.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualEntity.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(aBeer.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(aBeer.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    public void givenAValidBeer_whenCallsUpdate_shouldReturnBeerUpdated() {
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
                "Hein",
                expectedStyle,
                null,
                expectedIbu,
                expectedAbv,
                expectedColor,
                expectedIngredients,
                expectedFlavorDescription,
                expectedAromaDescription,
                expectedActive
        );

        Assertions.assertEquals(0, repository.count());

        repository.saveAndFlush(BeerJpaEntity.from(aBeer));

        Assertions.assertEquals(1, repository.count());

        final var actualInvalidEntity = repository.findById(aBeer.getId().getValue()).get();

        Assertions.assertEquals("Hein", actualInvalidEntity.getName());
        Assertions.assertEquals(expectedStyle, actualInvalidEntity.getStyle());
        Assertions.assertNull(actualInvalidEntity.getOrigin());
        Assertions.assertEquals(expectedIbu, actualInvalidEntity.getIbu());
        Assertions.assertEquals(expectedAbv, actualInvalidEntity.getAbv());
        Assertions.assertEquals(expectedColor, actualInvalidEntity.getColor());
        Assertions.assertEquals(expectedIngredients, actualInvalidEntity.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualInvalidEntity.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualInvalidEntity.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualInvalidEntity.isActive());

        final var anUpdatedBeer = Beer.withClone(aBeer).update(
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

        final var actualBeer = gateway.update(anUpdatedBeer);

        Assertions.assertEquals(1, repository.count());

        Assertions.assertEquals(aBeer.getId(), actualBeer.getId());
        Assertions.assertEquals(expectedName, actualBeer.getName());
        Assertions.assertEquals(expectedStyle, actualBeer.getStyle());
        Assertions.assertEquals(expectedOrigin, actualBeer.getOrigin());
        Assertions.assertEquals(expectedIbu, actualBeer.getIbu());
        Assertions.assertEquals(expectedAbv, actualBeer.getAbv());
        Assertions.assertEquals(expectedColor, actualBeer.getColor());
        Assertions.assertEquals(expectedIngredients, actualBeer.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualBeer.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualBeer.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualBeer.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualBeer.getCreatedAt());
        Assertions.assertTrue(aBeer.getUpdatedAt().isBefore(actualBeer.getUpdatedAt()));
        Assertions.assertEquals(aBeer.getDeletedAt(), actualBeer.getDeletedAt());
        Assertions.assertNull(actualBeer.getDeletedAt());

        final var actualEntity = repository.findById(aBeer.getId().getValue()).get();

        Assertions.assertEquals(aBeer.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedStyle, actualEntity.getStyle());
        Assertions.assertEquals(expectedOrigin, actualEntity.getOrigin());
        Assertions.assertEquals(expectedIbu, actualEntity.getIbu());
        Assertions.assertEquals(expectedAbv, actualEntity.getAbv());
        Assertions.assertEquals(expectedColor, actualEntity.getColor());
        Assertions.assertEquals(expectedIngredients, actualEntity.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualEntity.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualEntity.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualEntity.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertTrue(aBeer.getUpdatedAt().isBefore(actualBeer.getUpdatedAt()));
        Assertions.assertEquals(aBeer.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());
    }

    @Test
    public void givenAPrePersistedBeerAndValidBeerID_whenTryToDeleteIt_shouldDeleteBeer() {
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

        Assertions.assertEquals(0, repository.count());

        repository.saveAndFlush(BeerJpaEntity.from(aBeer));

        Assertions.assertEquals(1, repository.count());

        gateway.deleteById(aBeer.getId());

        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenAnInvalidBeerID_whenTryToDeleteIt_shouldDeleteBeer() {
        Assertions.assertEquals(0, repository.count());

        gateway.deleteById(BeerID.from("invalid"));

        Assertions.assertEquals(0, repository.count());
    }

    @Test
    public void givenAPrePersistedBeerAndValidBeerId_whenCallsFindById_shouldReturnABeer() {
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

        Assertions.assertEquals(0, repository.count());

        repository.saveAndFlush(BeerJpaEntity.from(aBeer));

        Assertions.assertEquals(1, repository.count());

        final var actualBeer = gateway.findById(aBeer.getId()).get();

        Assertions.assertEquals(1, repository.count());

        Assertions.assertEquals(aBeer.getId(), actualBeer.getId());
        Assertions.assertEquals(expectedName, actualBeer.getName());
        Assertions.assertEquals(expectedStyle, actualBeer.getStyle());
        Assertions.assertEquals(expectedOrigin, actualBeer.getOrigin());
        Assertions.assertEquals(expectedIbu, actualBeer.getIbu());
        Assertions.assertEquals(expectedAbv, actualBeer.getAbv());
        Assertions.assertEquals(expectedColor, actualBeer.getColor());
        Assertions.assertEquals(expectedIngredients, actualBeer.getIngredients());
        Assertions.assertEquals(expectedFlavorDescription, actualBeer.getFlavorDescription());
        Assertions.assertEquals(expectedAromaDescription, actualBeer.getAromaDescription());
        Assertions.assertEquals(expectedActive, actualBeer.isActive());
        Assertions.assertEquals(aBeer.getCreatedAt(), actualBeer.getCreatedAt());
        Assertions.assertEquals(aBeer.getUpdatedAt(), actualBeer.getUpdatedAt());
        Assertions.assertEquals(aBeer.getDeletedAt(), actualBeer.getDeletedAt());
        Assertions.assertNull(actualBeer.getDeletedAt());
    }

    @Test
    public void givenAValidBeerIdNotStored_whenCallsFindById_shouldReturnEmpty() {
        Assertions.assertEquals(0, repository.count());

        final var actualBeer = gateway.findById(BeerID.from("empty"));

        Assertions.assertTrue(actualBeer.isEmpty());
    }

    @Test
    public void givenPrePersistedBeers_whenCallsFindAll_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var heineken = Beer.newBeer(
                "Heineken",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var brahma = Beer.newBeer(
                "Brahma",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var skol = Beer.newBeer(
                "Skol",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        Assertions.assertEquals(0, repository.count());

        repository.saveAll(
                List.of(
                        BeerJpaEntity.from(heineken),
                        BeerJpaEntity.from(brahma),
                        BeerJpaEntity.from(skol)
                )
        );

        Assertions.assertEquals(3, repository.count());

        final var query = new BeerSearchQuery(0, 1, "", "name", "asc");
        final var result = gateway.findAll(query);

        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(expectedTotal, result.total());
        Assertions.assertEquals(expectedPerPage, result.items().size());
        Assertions.assertEquals(brahma.getId(), result.items().get(0).getId());
    }

    @Test
    public void givenEmptyBeersTable_whenCallsFindAll_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, repository.count());

        final var query = new BeerSearchQuery(0, 1, "", "name", "asc");
        final var result = gateway.findAll(query);

        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(expectedTotal, result.total());
        Assertions.assertEquals(0, result.items().size());
    }

    @Test
    public void givenFollowPagination_whenCallsFindAllWithPage1_shouldReturnPaginated() {
        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var heineken = Beer.newBeer(
                "Heineken",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var brahma = Beer.newBeer(
                "Brahma",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var skol = Beer.newBeer(
                "Skol",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        Assertions.assertEquals(0, repository.count());

        repository.saveAll(
                List.of(
                        BeerJpaEntity.from(heineken),
                        BeerJpaEntity.from(brahma),
                        BeerJpaEntity.from(skol)
                )
        );

        Assertions.assertEquals(3, repository.count());

        var query = new BeerSearchQuery(0, 1, "", "name", "asc");
        var result = gateway.findAll(query);

        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(expectedTotal, result.total());
        Assertions.assertEquals(expectedPerPage, result.items().size());
        Assertions.assertEquals(brahma.getId(), result.items().get(0).getId());

        //Page 1
        expectedPage = 1;
        query = new BeerSearchQuery(1, 1, "", "name", "asc");
        result = gateway.findAll(query);

        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(expectedTotal, result.total());
        Assertions.assertEquals(expectedPerPage, result.items().size());
        Assertions.assertEquals(heineken.getId(), result.items().get(0).getId());

        //Page 1
        expectedPage = 2;
        query = new BeerSearchQuery(2, 1, "", "name", "asc");
        result = gateway.findAll(query);

        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(expectedTotal, result.total());
        Assertions.assertEquals(expectedPerPage, result.items().size());
        Assertions.assertEquals(skol.getId(), result.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedBeersAndBraAsTerms_whenCallsFindAllAndTermsMatchsBeerName_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var heineken = Beer.newBeer(
                "Heineken",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var brahma = Beer.newBeer(
                "Brahma",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var skol = Beer.newBeer(
                "Skol",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        Assertions.assertEquals(0, repository.count());

        repository.saveAll(
                List.of(
                        BeerJpaEntity.from(heineken),
                        BeerJpaEntity.from(brahma),
                        BeerJpaEntity.from(skol)
                )
        );

        Assertions.assertEquals(3, repository.count());

        final var query = new BeerSearchQuery(0, 1, "bra", "name", "asc");
        final var result = gateway.findAll(query);

        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(expectedTotal, result.total());
        Assertions.assertEquals(expectedPerPage, result.items().size());
        Assertions.assertEquals(brahma.getId(), result.items().get(0).getId());
    }

    @Test
    public void givenPrePersistedBeersAndHolandaAsTerms_whenCallsFindAllAndTermsMatchsBeerOrigin_shouldReturnPaginated() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var heineken = Beer.newBeer(
                "Heineken",
                null,
                "Holanda",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var brahma = Beer.newBeer(
                "Brahma",
                null,
                "Brasil",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        final var skol = Beer.newBeer(
                "Skol",
                null,
                "Brasil",
                null,
                null,
                null,
                null,
                null,
                null,
                true
        );

        Assertions.assertEquals(0, repository.count());

        repository.saveAll(
                List.of(
                        BeerJpaEntity.from(heineken),
                        BeerJpaEntity.from(brahma),
                        BeerJpaEntity.from(skol)
                )
        );

        Assertions.assertEquals(3, repository.count());

        final var query = new BeerSearchQuery(0, 1, "HOLANDA", "name", "asc");
        final var result = gateway.findAll(query);

        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(expectedTotal, result.total());
        Assertions.assertEquals(expectedPerPage, result.items().size());
        Assertions.assertEquals(heineken.getId(), result.items().get(0).getId());
    }
}


