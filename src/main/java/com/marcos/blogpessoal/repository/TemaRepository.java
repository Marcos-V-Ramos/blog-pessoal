package com.marcos.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import com.marcos.blogpessoal.entity.Tema;

public interface TemaRepository extends JpaRepository<Tema, Long> {
	
	public abstract List<Tema> findAllByDescricaoContainingIgnoreCase(@RequestParam String descricao);

}