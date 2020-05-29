package com.siscom.repository;

import org.springframework.data.repository.CrudRepository;

import com.siscom.model.Fornecedor;

public interface FornecedorRepository extends CrudRepository<Fornecedor, String>{
	Fornecedor findByCnpj(long cnpj);
}
