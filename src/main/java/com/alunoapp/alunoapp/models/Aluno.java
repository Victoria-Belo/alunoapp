package com.alunoapp.alunoapp.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotEmpty;


@Entity
public class Aluno implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id //cod é um Id no BD
	@GeneratedValue(strategy=GenerationType.AUTO) //Cod é incrementado automaticamente
	private long codigo;
	
	@NotEmpty
	private String matricula;	
	
	@NotEmpty
	private String nome;
	
	@NotEmpty
	private String cpf;
	
	private String email;
	
	@ManyToOne
	private Curso curso;	
	
	
	public Aluno() {} //precisa ter dois constructors para que MVC funcione quando chamar uma lista dos dados
	
	public Aluno(String nome, String cpf, String email, String matricula) {
		//se montarmos APENAS ESSE CONSTRUCTOR o MVC dá erro, ele precisa de um vazio
		this.nome=nome;
		this.cpf=cpf;
		this.email=email;
		this.matricula = matricula;		
		
		this.setNome(nome);
	}	

	public long getCodigo() {
		return codigo;
	}

	public void setCodigo(long codigo) {
		this.codigo = codigo;
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


	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
		
	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {		
		this.curso = curso;
	}
	
	public String toDados() {
		return "Aluno [codigo=" + codigo + ", matricula=" + matricula + ", nome=" + nome + ", cpf=" + cpf + ", email="
				+ email + "]";
	}
	
	
	@Override
	public String toString() {
		return nome ;
	}

	
	
	
}
