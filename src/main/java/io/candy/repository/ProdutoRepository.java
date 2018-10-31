package io.candy.repository;

import io.candy.domain.Produto;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Produto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {

}
