package br.com.fermino.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fermino.data.vo.v1.PersonVO;
import br.com.fermino.services.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

//@CrossOrigin
@Api(value="Person Endpoint",description = "Description for person", tags={"PersonEndpoint"})
@RestController
@RequestMapping("api/v1/person")
public class PersonController {
	
	@Autowired
	private PersonService services;
	
	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;
		
	@ApiOperation(value="Request to find all people recorded")
	@GetMapping(produces= {"application/json", "application/xml"})
	//@CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<?> findAll(
    		@RequestParam(value="page", defaultValue = "0") int page,
    		@RequestParam(value="limit", defaultValue = "12") int limit,
    		@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit,Sort.by(sortDirection,"firstName"));
		
		Page<PersonVO> persons= services.findAll(pageable);
		
		persons.stream().forEach(p->p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
    	
		PagedResources<?> resources = assembler.toResource(persons);
		
		return new ResponseEntity<>(resources,HttpStatus.OK);
    }
	
	@ApiOperation(value="Request to find people by name")
	@GetMapping(value="/findPersonByName/{firstName}",produces= {"application/json", "application/xml"})
	//@CrossOrigin(origins = "http://localhost:8080")
    public ResponseEntity<?> findPersonByName(
    		@PathVariable("firstName")  String firstName,
    		@RequestParam(value="page", defaultValue = "0") int page,
    		@RequestParam(value="limit", defaultValue = "12") int limit,
    		@RequestParam(value="direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
		
		Pageable pageable = PageRequest.of(page, limit,Sort.by(sortDirection,"firstName"));
		
		Page<PersonVO> persons= services.findPersonByName(firstName,pageable);
		
		persons.stream().forEach(p->p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
    	
		PagedResources<?> resources = assembler.toResource(persons);
		
		return new ResponseEntity<>(resources,HttpStatus.OK);
    }
	
	@ApiOperation(value="Get person by id")
	@GetMapping(value="/{id}",produces= {"application/json", "application/xml"})
    public PersonVO findById(@PathVariable("id")  Long id) {
    	PersonVO personVO= services.findById(id);
    	personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    	return personVO;
    }
    
	@ApiOperation(value="Create a new person")
    @PostMapping(produces= {"application/json", "application/xml"},
    		consumes= {"application/json", "application/xml"})
    public PersonVO create(@RequestBody PersonVO person) {
    	PersonVO personVO= services.Create(person);
    	personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
    	return personVO;
    	
    }
    
	@ApiOperation(value="Update a person")
    @PutMapping(produces= {"application/json", "application/xml"},
    		consumes= {"application/json", "application/xml"})
    public PersonVO update(@RequestBody PersonVO person) {
    	PersonVO personVO= services.Update(person);
    	personVO.add(linkTo(methodOn(PersonController.class).findById(personVO.getKey())).withSelfRel());
    	return personVO;
    }
	
	@ApiOperation(value="Disable a specific person by your Id ")
	@PatchMapping(value="/{id}",produces= {"application/json", "application/xml"})
    public PersonVO disablePerson(@PathVariable("id")  Long id) {
    	PersonVO personVO= services.disablePerson(id);
    	personVO.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    	return personVO;
    }
    
	@ApiOperation(value="Delete a person")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
    	services.delete(id); 	
    	return ResponseEntity.ok().build();
    }
	
	
    

}
