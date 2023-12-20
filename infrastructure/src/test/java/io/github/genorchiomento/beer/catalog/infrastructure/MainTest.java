package io.github.genorchiomento.beer.catalog.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.AbstractEnvironment;

class MainTest {

    @Test
    public void testMain() {
        System.setProperty(
                AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME,
                "test"
        );
        new Main();
        Main.main(new String[]{});
    }
}