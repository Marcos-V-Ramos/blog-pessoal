package com.marcos.blogpessoal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import org.springframework.web.client.HttpStatusCodeException;

import com.marcos.blogpessoal.entity.Postagem;
import com.marcos.blogpessoal.repository.PostagemRepository;
import com.marcos.blogpessoal.repository.TemaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins="*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;
	
	@Autowired
	private TemaRepository temaRepository;
	
	@GetMapping
	public ResponseEntity<List<Postagem>> getAll(){
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id) {
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
	}
	
	@PostMapping
	public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem) {
		if (temaRepository.existsById(postagem.getTema().getId())) {
			return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping
	public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem) {
//		Optional<Postagem> findPostagem = postagemRepository.findById(postagem.getId());
//		
//		if (findPostagem.isPresent()) {
//			return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
//		}
//		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//		
		if (postagemRepository.existsById(postagem.getId())) {
			
			if (temaRepository.existsById(postagem.getTema().getId())) {
				return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
			}
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Optional<Postagem> post = postagemRepository.findById(id);
		
		if (post.isPresent()) {
			postagemRepository.delete(post.get());
			return ResponseEntity.noContent().build();
		}
		
		return ResponseEntity.notFound().build();
	}
}
