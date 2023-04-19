package com.marcos.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcos.blogpessoal.entity.Tema;
import com.marcos.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/temas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TemaController {
	
	@Autowired
	TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Tema>> getAll() {
		return ResponseEntity.status(200).body(temaRepository.findAll());
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<Tema> getById(@PathVariable Long id) {
		Optional<Tema> handleTema = temaRepository.findById(id);
		
		if (handleTema.isPresent()) {
			return ResponseEntity.status(200).body(handleTema.get());
		} else {
			return ResponseEntity.status(204).build();
		}
	}
	
	@PutMapping
	public ResponseEntity<Tema> updateTema(@Valid @RequestBody Tema tema) {
		Optional<Tema> temaProcurado = temaRepository.findById(tema.getId());
		
		if (temaProcurado.isPresent()) {
			return ResponseEntity.status(201).body(temaRepository.save(tema));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<Tema> addTema(@Valid @RequestBody Tema tema) {
		return ResponseEntity.status(200).body(temaRepository.save(tema));
	}
	
	@GetMapping("descricao/{descricao}")
	public ResponseEntity<List<Tema>> findAllByDescricao(@PathVariable String descricao) {
		return ResponseEntity.status(200)
				.body(temaRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}
	
	@DeleteMapping("id/{id}")
	public ResponseEntity<?> removeById(@PathVariable Long id) {
		Optional<Tema> temaProcurado = temaRepository.findById(id);
		
		if (temaProcurado.isPresent()) {
			temaRepository.delete(temaProcurado.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
