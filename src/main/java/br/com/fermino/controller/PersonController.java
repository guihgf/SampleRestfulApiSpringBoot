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

import br.com.fermino.data.vo.v1.PersonVO;
import br.com.fermino.services.PersonService;

@RestController
@RequestMapping("api/person/v1")
public class PersonController {
	
	@Autowired
	private PersonService services;
		
	@GetMapping(produces= {"application/json", "application/xml"})
    public List<PersonVO> findAll() {
		List<PersonVO> persons= services.findAll();
		
		persons.stream().forEach(p->p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
    	
		return persons;
    }
	   
	@GetMapping(value="/{id}",produces= {"application/json", "application/xml"})
    public PersonVO findById(@PathVariable("id")  Long id) {
    	PersonVO personVO= services.findById(id);
    	personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    	return personVO;
    }
    
    @PostMapping(produces= {"application/json", "application/xml"},
    		consumes= {"application/json", "application/xml"})
    public PersonVO create(@RequestBody PersonVO person) {
    	PersonVO personVO= services.Create(person);
    	personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
    	return personVO;
    	
    }
    
    
    @PutMapping(produces= {"application/json", "application/xml"},
    		consumes= {"application/json", "application/xml"})
    public PersonVO update(@RequestBody PersonVO person) {
    	PersonVO personVO= services.Update(person);
    	personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
    	return personVO;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    	services.delete(id); 	
    	return ResponseEntity.ok().build();
    }
    

}
