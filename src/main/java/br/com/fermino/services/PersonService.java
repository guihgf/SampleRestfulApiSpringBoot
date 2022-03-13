package br.com.fermino.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fermino.adapter.DozerAdapter;
import br.com.fermino.data.model.Person;
import br.com.fermino.data.vo.v1.PersonVO;
import br.com.fermino.exception.ResourceNotFoundException;
import br.com.fermino.repository.PersonRepository;

@Service
public class PersonService {
	
	@Autowired
	PersonRepository repository;
		
	public PersonVO Create(PersonVO person) {
		var entity=DozerAdapter.parseObject(person, Person.class);
		var vo=DozerAdapter.parseObject(repository.save(entity), PersonVO.class);
		return vo;
	}
		
	public Page<PersonVO> findAll(Pageable pageable) {
		var page = repository.findAll(pageable);
		
		return page.map(this :: convertToPersonVO);

	}
	
	private PersonVO convertToPersonVO(Person entity) {
		return DozerAdapter.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO findById(Long id) {
		//ResourceNotFoundExceptio via callback
		var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records founds for this ID"));
		
		return DozerAdapter.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO Update(PersonVO person) {
		var entity=repository.findById(person.getKey()).orElseThrow(()-> new ResourceNotFoundException("No records founds for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerAdapter.parseObject(repository.save(entity), PersonVO.class); 
		
		return vo;
		
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		repository.disabledPersons(id);
		
		var entity = repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records founds for this ID"));
		
		return DozerAdapter.parseObject(entity, PersonVO.class);
	}
	
	public void delete(Long id) {
		Person entity= repository.findById(id).orElseThrow(()-> new ResourceNotFoundException("No records founds for this ID"));
		repository.delete(entity);
	}
	
	
	
	
	

	
	

	
}
