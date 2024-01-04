package io.github.genorchiomento.beer.catalog.infrastructure.beer;

import io.github.genorchiomento.beer.catalog.domain.beer.Beer;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerGateway;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerID;
import io.github.genorchiomento.beer.catalog.domain.beer.BeerSearchQuery;
import io.github.genorchiomento.beer.catalog.domain.pagination.Pagination;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerJpaEntity;
import io.github.genorchiomento.beer.catalog.infrastructure.beer.persistence.BeerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static io.github.genorchiomento.beer.catalog.infrastructure.utils.SpecificationUtils.like;

@Service
public class BeerMySQLGateway implements BeerGateway {

    private final BeerRepository repository;

    public BeerMySQLGateway(final BeerRepository repository) {
        this.repository = repository;
    }

    @Override
    public Beer create(Beer aBeer) {
        return save(aBeer);
    }

    @Override
    public void deleteById(final BeerID anId) {
        final String idValue = anId.getValue();

        if (repository.existsById(idValue)) {
            repository.deleteById(idValue);
        }
    }

    @Override
    public Optional<Beer> findById(final BeerID anId) {
        return repository.findById(anId.getValue())
                .map(BeerJpaEntity::toAggregate);

    }

    @Override
    public Beer update(final Beer aBeer) {
        return save(aBeer);
    }

    @Override
    public Pagination<Beer> findAll(final BeerSearchQuery aQuery) {
        //Pagination
        final var page = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        //Dinamic search
        final var specifications = Optional.ofNullable(aQuery.terms())
                .filter(str -> !str.isBlank())
                .map(str -> {
                    final Specification<BeerJpaEntity> nameLike = like("name", str);
                    final Specification<BeerJpaEntity> originLike = like("origin", str);

                    return nameLike.or(originLike);
                })
                .orElse(null);

        final var pageResult = repository.findAll(Specification.where(specifications), page);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(BeerJpaEntity::toAggregate).toList()
        );
    }

    private Beer save(Beer aBeer) {
        return repository.save(BeerJpaEntity.from(aBeer)).toAggregate();
    }
}
