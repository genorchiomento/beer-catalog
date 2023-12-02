package io.github.genorchiomento.beer.catalog.infrastructure;

import io.github.genorchiomento.beer.catalog.application.UseCase;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        System.out.println(new UseCase().execute());
    }
}