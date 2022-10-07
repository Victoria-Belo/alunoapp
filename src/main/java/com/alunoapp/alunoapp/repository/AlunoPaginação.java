package com.alunoapp.alunoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.alunoapp.alunoapp.models.Aluno;

public interface AlunoPaginação extends JpaRepository<Aluno, Long>{
	
	 @Query(value = "SELECT * FROM aluno a WHERE a.nome like %:nome_aluno% ", nativeQuery = true)
	 List <Aluno> findByAluno(@Param("nome_aluno") String nome_aluno);
	 
	
	
}
