package model;

import java.util.Date;

public class Cliente extends Pessoa {
	private String cpf;
	private double limiteCredito;

	public Cliente(int codigo, String nome, String telefones, String email, Date dataCad, String cpf,
			double limiteCredito) {
		super(codigo, nome, telefones, email, dataCad);
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
