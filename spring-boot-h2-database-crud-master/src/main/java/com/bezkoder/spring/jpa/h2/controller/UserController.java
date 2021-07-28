package com.bezkoder.spring.jpa.h2.controller;

import java.util.ArrayList;



import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bezkoder.spring.jpa.h2.model.User;
import com.bezkoder.spring.jpa.h2.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	UserRepository UserRepository;

	@GetMapping("/user")
	public ResponseEntity<List<User>> getAllTutorials(@RequestParam(required = false) String status) {
		try {
			List<User> tutorials = new ArrayList<User>();

			if (status == null)
				UserRepository.findAll().forEach(tutorials::add);
			else
				UserRepository.findBystatus(status).forEach(tutorials::add);

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<User> getTutorialById(@PathVariable("id") long id) {
		Optional<User> tutorialData = UserRepository.findById(id);

		if (tutorialData.isPresent()) {
			return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/user")
	public ResponseEntity<User> createTutorial(@RequestBody User tutorial) {
		try {
			User _tutorial = 
	UserRepository.save(new User(tutorial.getUsername(), tutorial.getPassword(),tutorial.getStatus()));
			return new ResponseEntity<>(_tutorial, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateTutorial(@PathVariable("id") long id, @RequestBody User tutorial) {
		Optional<User> tutorialData = UserRepository.findById(id);

		if (tutorialData.isPresent()) {
			User _tutorial = tutorialData.get();
			_tutorial.setUsername(tutorial.getUsername());
			_tutorial.setPassword(tutorial.getPassword());
			_tutorial.setStatus(tutorial.getStatus());
			return new ResponseEntity<>(UserRepository.save(_tutorial), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			UserRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
