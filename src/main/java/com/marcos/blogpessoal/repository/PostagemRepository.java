package com.marcos.blogpessoal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.marcos.blogpessoal.entity.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long>{
	
}