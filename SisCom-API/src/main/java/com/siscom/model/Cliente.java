package com.siscom.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Cliente extends Pessoa {
	
	@Id
	private String cpf;
	private double limiteCredito;

	public Cliente(int codigo, String nome, String telefone, String email, Date dataCad, String cpf,
			double limiteCredito) {
		super(codigo, nome, telefone, email, dataCad);
		this.cpf = cpf;
		this.limiteCredito = limiteCredito;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	@Override
	public String getTipoPessoa() {
		return "Cliente";
	}

	@Override
	public String toString() {
		return "Cliente [cpf=" + cpf + ", limiteCredito=" + limiteCredito + "]";
	}

}
