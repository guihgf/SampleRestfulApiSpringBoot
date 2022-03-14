package br.com.fermino.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fermino.data.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

}
