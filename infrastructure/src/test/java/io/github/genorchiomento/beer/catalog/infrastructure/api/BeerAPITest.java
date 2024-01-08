package io.github.genorchiomento.beer.catalog.infrastructure.api;

import io.github.genorchiomento.beer.catalog.ControllerTest;
import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@ControllerTest(controllers = BeerAPI.class)
public class BeerAPITest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateBeerUseCase useCase;

    @Test
    public void test() {

    }
}
