package com.generation.blogpessoal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.generation.blogpessoal.model.Postagem;

/* Criar repository depois criar herança da tabela postagem
Jpa Repository (vem da biblioteca Jpa) é um classe que possui vários métodos
Postagem é a classe/entidade que criamos e Long é referente ao ID (Para saber com qual tabela iremos trabalhar)
depois disso ---> ir para classe controladora. */

@Repository
public interface PostagemRepository extends JpaRepository <Postagem,Long>{

    public List <Postagem> findAllByTituloContainingIgnoreCase(@Param("titulo")String titulo);
}
