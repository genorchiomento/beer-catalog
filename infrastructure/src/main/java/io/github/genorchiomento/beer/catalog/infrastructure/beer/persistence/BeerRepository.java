package io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<BeerJpaEntity, String> {

    Page<BeerJpaEntity> findAll(Specification<BeerJpaEntity> whereCause, Pageable page);
}
