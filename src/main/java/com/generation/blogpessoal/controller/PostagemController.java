package com.generation.blogpessoal.controller;

import  java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
import org.springframework.web.server.ResponseStatusException;

import com.generation.blogpessoal.model.Postagem;
import com.generation.blogpessoal.repository.PostagemRepository;

@RestController
@RequestMapping("/postagem")
@CrossOrigin(origins="*",allowedHeaders="*")
public class PostagemController {
	
	@Autowired
	private PostagemRepository postagemRepository;

	@GetMapping //busca a resposta da aquisição
	public ResponseEntity <List<Postagem>>getAll(){
		return ResponseEntity.ok(postagemRepository.findAll()); // select * from tb_postagens
	}
	
	@GetMapping("/{id}")
	public ResponseEntity <Postagem> getById(@PathVariable Long id){
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());
		// select* from tb_postagens where id = id
	}
	//@GetMapping("/titulo/{titulo}")
    //public ResponseEntity <Postagem> getByTitulo(@PathVariable String titulo){
      //  return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));}
	
	@PostMapping
    public ResponseEntity <Postagem> postPostagem(@Valid @RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
    
	}
	//Metódo Put
	@PutMapping
    public ResponseEntity <Postagem> putPostagem(@Valid @RequestBody Postagem postagem){
        return ResponseEntity.status(HttpStatus.OK).body(postagemRepository.save(postagem));
    }
	//Metódo Delete
    @DeleteMapping("/{id}")
    @ResponseStatus (HttpStatus.NO_CONTENT)//Para trazer o status sem conteúdo
    public void deletePostagem (@Valid @PathVariable Long id){
        Optional<Postagem> postagem = postagemRepository.findById(id); //como se fosse map

        if (postagem.isEmpty()) //checagem se é vazio...
            throw new ResponseStatusException(HttpStatus.NOT_FOUND); //caso não encontre "joga" uma nova resposta

        postagemRepository.deleteById(id); //chamo o método postagemRepository
    }

}
