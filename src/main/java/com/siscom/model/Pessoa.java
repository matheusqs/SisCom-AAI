package com.siscom.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public abstract class Pessoa implements Comparable<Pessoa> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String nome;
	private String telefones;
	private String email;
	private Date dataCad;
	
	protected Pessoa() {}

	public Pessoa(int codigo, String nome, String telefones, String email, Date dataCad) {
		this.id = codigo;
		this.nome = nome;
		this.telefones = telefones;
		this.email = email;
		this.dataCad = dataCad;
	}

	public int getCodigo() {
		return id;
	}

	public void setCodigo(int codigo) {
		this.id = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefones() {
		return telefones;
	}

	public void setTelefones(String telefones) {
		this.telefones = telefones;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDataCad() {
		return dataCad;
	}

	public void setDataCad(Date dataCad) {
		this.dataCad = dataCad;
	}

	public abstract String getTipoPessoa(); // TODO verificar retorno

	public int compareTo(Pessoa pessoa) {
		return pessoa.getNome().compareTo(this.nome);
	}

	@Override
	public String toString() {
		return "Pessoa [codigo=" + id + ", nome=" + nome + ", telefones=" + telefones + ", email=" + email
				+ ", dataCad=" + dataCad + "]";
	}

}
