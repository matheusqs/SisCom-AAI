package com.siscom.repository;

import org.springframework.data.repository.CrudRepository;

import com.siscom.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {
	Pessoa findById(long id);
}
