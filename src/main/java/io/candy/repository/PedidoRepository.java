package io.candy.repository;

import io.candy.domain.Pedido;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Pedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends MongoRepository<Pedido, String> {
    @Query("{}")
    Page<Pedido> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Pedido> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Pedido> findOneWithEagerRelationships(String id);

}
