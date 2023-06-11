package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Users;
import com.example.demo.repository.UserRepository;

@RestController
@CrossOrigin("http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserRepository Repository;
	
	@PostMapping("/saveUser")
	Users newUser(@RequestBody Users newUser) {
		return Repository.save(newUser);
	}
	
	@GetMapping("/getUsers")
	List <Users> getAllUser(){
		return Repository.findAll();
	}
	
	@GetMapping("/user/{id}")
	Users getUserById(@PathVariable Long id) {
		return Repository.findById(id).orElseThrow();
	}
	
	@PutMapping("/user/{id}")
	Users updateUser(@RequestBody Users updateUser,@PathVariable Long id) {
		return Repository.findById(id)
				.map(use->{
					use.setName(updateUser.getName());
					use.setUsername(updateUser.getUsername());
					use.setEmail(updateUser.getEmail());
					return Repository.save(use);
				}).orElseThrow();
	}
	
	@DeleteMapping("/user/{id}")
	String deleteUser(@PathVariable Long id) {
		if(!Repository.existsById(id)) {
			throw new RuntimeException("User with "+id+" not present");
		}
		Repository.deleteById(id);
		return "User with "+id+" has been deleted";
	}
	

}
