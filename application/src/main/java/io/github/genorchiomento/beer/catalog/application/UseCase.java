package io.github.genorchiomento.beer.catalog.application;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;

public class UseCase {
    public Beer execute() {
        return new Beer();
    }
}