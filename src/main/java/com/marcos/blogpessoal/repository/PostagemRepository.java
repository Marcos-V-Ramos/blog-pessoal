package com.marcos.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import com.marcos.blogpessoal.entity.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
	public List<Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo") String titulo);
}