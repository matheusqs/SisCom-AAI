package com.siscom.repository;

import org.springframework.data.repository.CrudRepository;

import com.siscom.model.Compra;

public interface CompraRepository extends CrudRepository<Compra, Long> {
	Compra findById(long id);
}
