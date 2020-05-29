package com.siscom.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Fornecedor extends Pessoa {
	@Id
	private String cnpj;
	private String nomeContato;

	public Fornecedor(int codigo, String nome, String telefones, String email, Date dataCad, String cnpj,
			String nomeContato) {
		super(codigo, nome, telefones, email, dataCad);
		this.cnpj = cnpj;
		this.nomeContato = nomeContato;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNomeContato() {
		return nomeContato;
	}

	public void setNomeContato(String nomeContato) {
		this.nomeContato = nomeContato;
	}

	@Override
	public String getTipoPessoa() {
		return "Fornecedor";
	}

	@Override
	public String toString() {
		return "Fornecedor [cnpj=" + cnpj + ", nomeContato=" + nomeContato + "]";
	}

}
