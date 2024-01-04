package io.github.genorchiomento.beer.catalog.infrastructure.configuration.usecases;

import io.github.genorchiomento.beer.catalog.application.beer.create.CreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.create.DefaultCreateBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.delete.DefaultDeleteBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.delete.DeleteBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.DefaultGetBeerByIdUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.get.GetBeerByIdUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.list.DefaultListBeersUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.retrieve.list.ListBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.update.DefaultUpdateBeerUseCase;
import io.github.genorchiomento.beer.catalog.application.beer.update.UpdateBeerUseCase;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeerUseCaseConfig {

    private final BeerGateway beerGateway;

    public BeerUseCaseConfig(final BeerGateway beerGateway) {
        this.beerGateway = beerGateway;
    }

    @Bean
    public CreateBeerUseCase createBeerUseCase() {
        return new DefaultCreateBeerUseCase(beerGateway);
    }

    @Bean
    public UpdateBeerUseCase updateBeerUseCase() {
        return new DefaultUpdateBeerUseCase(beerGateway);
    }

    @Bean
    public GetBeerByIdUseCase getBeerByIdUseCase() {
        return new DefaultGetBeerByIdUseCase(beerGateway);
    }

    @Bean
    public ListBeerUseCase listBeerUseCase() {
        return new DefaultListBeersUseCase(beerGateway);
    }

    @Bean
    public DeleteBeerUseCase deleteBeerUseCase() {
        return new DefaultDeleteBeerUseCase(beerGateway);
    }
}
