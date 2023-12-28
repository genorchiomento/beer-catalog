package io.github.genorchiomento.beer.catalog.infrastructure;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.ColorEnum;
import io.github.genorchiomento.beer.catalog.domain.beer.enumerable.StyleEnum;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerJpaEntity;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import io.github.genorchiomento.beer.catalog.infrastructure.configuration.WebServerConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        System.setProperty(
                AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME,
                "development"
        );
        SpringApplication.run(WebServerConfig.class, args);
    }

    @Bean
    public ApplicationRunner runner(BeerRepository beerRepository) {
        return args -> {

            List<BeerJpaEntity> all = beerRepository.findAll();

            Beer beer = Beer.newBeer(
                    "Brahma",
                    StyleEnum.ALE,
                    "Brasil",
                    50.0,
                    6.6,
                    ColorEnum.CLARA,
                    "",
                    "",
                    "",
                    true
            );

            beerRepository.saveAndFlush(BeerJpaEntity.from(beer));

            beerRepository.deleteAll();
        };
    }
}