package com.siscom.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vendedor extends Pessoa {
	@Id
	private String cpf;
	private double metaMensal;

	public Vendedor(int codigo, String nome, String telefones, String email, Date dataCad, String cpf,
			double metaMensal) {
		super(codigo, nome, telefones, email, dataCad);
		this.cpf = cpf;
		this.metaMensal = metaMensal;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public double getMetaMensal() {
		return metaMensal;
	}

	public void setMetaMensal(double metaMensal) {
		this.metaMensal = metaMensal;
	}

	@Override
	public String getTipoPessoa() {
		return "Vendedor";
	}

	@Override
	public String toString() {
		return "Vendedor [cpf=" + cpf + ", metaMensal=" + metaMensal + "]";
	}

}
