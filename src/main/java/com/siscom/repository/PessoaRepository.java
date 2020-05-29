package com.siscom.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.siscom.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {

	List<Pessoa> findByLastName(String lastName);

	Pessoa findById(long id);
}
