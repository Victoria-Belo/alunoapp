package com.alunoapp.alunoapp.repository;



import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.alunoapp.alunoapp.models.Curso;


public interface CursoRepository extends CrudRepository<Curso, String>{
	
	Curso findByCodigo(long codigo);
	
	@Query(value = "SELECT * FROM curso c WHERE c.nome LIKE %:nome_curso%", nativeQuery = true )
	List <Curso> findByNome(@Param ("nome_curso") String nome_curso);
}
