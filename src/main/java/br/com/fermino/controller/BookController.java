package br.com.fermino.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fermino.data.vo.v1.BookVO;
import br.com.fermino.services.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Book Endpoint",description = "Description for book", tags={"BookEndpoint"})
@RestController
@RequestMapping("api/book/v1")
public class BookController {
	
	@Autowired
	private BookService service;
	
	@ApiOperation(value="Request to find all books recorded")
	@GetMapping(produces = {"application/json","application/xml"})
	public List<BookVO> findAll(){
		List<BookVO> books = service.findAll();
		
		books.stream().forEach(b->b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
		
		return books;
	}
	
	@ApiOperation(value="Get book by id")
	@GetMapping(value="/{id}",produces = {"application/json","application/xml"})
	public BookVO findById(@PathVariable("id") Long id) {
		BookVO book = service.findById(id);
		
		book.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		
		return book;
	}
	
	@ApiOperation(value="Create a new book")
	@PostMapping(produces = {"application/json","application/xml"},consumes = {"application/json","application/xml"})
	public BookVO create(@RequestBody BookVO book) {
		BookVO vo = service.create(book);
		
		book.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;
	}
	
	@ApiOperation(value="Update a book")
	@PutMapping(produces = {"application/json","application/xml"},consumes = {"application/json","application/xml"})
	public BookVO update(@RequestBody BookVO book) {
		BookVO vo = service.update(book);
		
		book.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
		
		return vo;	
	}
	
	@ApiOperation(value="delete a book")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		service.delete(id); 	
    	return ResponseEntity.ok().build();
    }
	
	
}
