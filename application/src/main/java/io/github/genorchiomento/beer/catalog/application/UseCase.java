package io.github.genorchiomento.beer.catalog.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIn);
}