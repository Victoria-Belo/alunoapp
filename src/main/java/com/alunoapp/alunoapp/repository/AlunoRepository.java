package com.alunoapp.alunoapp.repository;
import org.springframework.data.repository.CrudRepository;
import com.alunoapp.alunoapp.models.Aluno;
import org.springframework.data.repository.query.Param;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;


public interface AlunoRepository extends CrudRepository<Aluno, String> {
	
	 Aluno findByCodigo(long codigo);
	
	
	 @Query(value = "SELECT * FROM aluno a WHERE a.nome like %:nome_aluno% ", nativeQuery = true)
	 List <Aluno> findByAluno(@Param("nome_aluno") String nome_aluno);
	 
	 

	
	
	

	
}
