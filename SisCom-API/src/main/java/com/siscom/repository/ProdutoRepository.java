package com.siscom.repository;

import org.springframework.data.repository.CrudRepository;

import com.siscom.model.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Long> {

}
