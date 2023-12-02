package io.github.genorchiomento.beer.catalog.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    public void testMain() {
        Assertions.assertNotNull(new Main());
        Main.main(new String[]{});
    }
}