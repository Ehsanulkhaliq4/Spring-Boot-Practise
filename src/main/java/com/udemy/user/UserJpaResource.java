package com.udemy.user; 

import java.net.URI;
import java.rmi.server.UID;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.udemy.jpa.UserRepository;

import jakarta.validation.Valid;


@RestController
public class UserJpaResource {
	
	private UserDaoService service;
	
	private UserRepository repository;
	
	public UserJpaResource(UserDaoService service , UserRepository repository) {
		this.service = service;
		this.repository = repository;
	}
	
	@GetMapping("/jpa/users")
	public List<User> reterieveAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping("/jpa/users/{userId}")
	public EntityModel<User> getUserById(@PathVariable("userId") int userId) {
		Optional<User> user = repository.findById(userId);
		if (user.isEmpty()) 
			throw new UserNotFoundException("ID :" + userId); 
		EntityModel<User> entityModel = EntityModel.of(user.get());
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).reterieveAllUsers());
		entityModel.add(link.withRel("all-uers"));
		return entityModel;
	}
	
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@DeleteMapping("/jpa/users/{userId}")
	public void deleteUser(@PathVariable("userId") int userId) {
		repository.deleteById(userId);
	}
	

}
