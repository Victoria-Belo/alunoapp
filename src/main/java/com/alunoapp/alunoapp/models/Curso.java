package com.alunoapp.alunoapp.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Curso implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id //cod é um Id no BD
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long codigo;
	
	@NotEmpty
	private String nome;	
	
	private String descricao;
	
	@NotEmpty
	private String periodo;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "curso") // um curso possui muitos alunos;
	private List <Aluno> aluno;
		

	public Curso() {}
	
		
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
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	
	public List<Aluno> getAluno() {
		return aluno;
	}

	public void setAluno(List<Aluno> aluno) {
		this.aluno = aluno;
	}

	@Override
	public String toString() {
		return "Curso [codigo=" + codigo + ", nome=" + nome + ", descricao=" + descricao + ", periodo=" + periodo
				+ ", aluno=" + aluno + "]";
	}


	

		
}
