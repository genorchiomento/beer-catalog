package io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<BeerJpaEntity, String> {
}
