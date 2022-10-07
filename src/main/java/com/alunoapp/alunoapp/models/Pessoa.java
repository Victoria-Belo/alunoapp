package com.alunoapp.alunoapp.models;

//clase abstrata não funcionou, 
//pesquisei mas não entendi como fazer com que os seus atributos fossem implementados na coluna de Aluno no BD
//tentei deixar explicito no construtor com super e usei setters mas não funcionou
//tentei fazer anotações como Component e Service mas não entendo bem como trabalham
public abstract class Pessoa {
	

	private String nome;

	private String cpf;

	private String email;
	
	public Pessoa (String nome, String cpf, String email) {
		this.nome = nome;
		this.cpf= cpf;
		this.email= email;
		this.setNome(nome);		
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome.toLowerCase();
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
