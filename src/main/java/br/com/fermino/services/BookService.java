package br.com.fermino.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fermino.adapter.DozerAdapter;
import br.com.fermino.data.model.Book;
import br.com.fermino.data.vo.v1.BookVO;
import br.com.fermino.exception.ResourceNotFoundException;
import br.com.fermino.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository repository;
	
	public BookVO create (BookVO book) {
		var entity= DozerAdapter.parseObject(book, Book.class);
		var vo=DozerAdapter.parseObject(repository.save(entity), BookVO.class);
		return vo;
	}
	
	public List<BookVO> findAll(){
		return DozerAdapter.parseListObjects(repository.findAll(), BookVO.class);
	}
	
	public BookVO findById(Long id) {
		var entity= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records founds for this ID"));
		
		return DozerAdapter.parseObject(entity, BookVO.class); 
	}
	
	public BookVO update (BookVO book) {
		var entity= repository.findById(book.getKey()).orElseThrow(()-> new ResourceNotFoundException("No records founds for this ID"));
		
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setPrice(book.getPrice());
		
		var vo=DozerAdapter.parseObject(repository.save(entity), BookVO.class);
		
		return vo;
	}
	
	public void delete(Long id) {
		Book entity= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records founds for this ID"));
		repository.delete(entity);
	}
	
	
}
