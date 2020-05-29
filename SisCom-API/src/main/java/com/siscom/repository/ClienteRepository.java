package com.siscom.repository;

import org.springframework.data.repository.CrudRepository;

import com.siscom.model.Cliente;
import com.siscom.model.Pessoa;

public interface ClienteRepository extends CrudRepository<Cliente, String> {
	Cliente findByCpf(String cpf);
}
