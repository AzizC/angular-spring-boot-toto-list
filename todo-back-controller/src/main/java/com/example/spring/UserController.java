package com.example.spring;


import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService myService;
	
	@RequestMapping("/user")
	  public Principal user(Principal user) {
	    return user;
	  }
	
	@RequestMapping(method = RequestMethod.GET)
	public List<User> getAllUsers() {
		return myService.findAll();
	}
	
	@RequestMapping(value="/email/{email}", method = RequestMethod.GET)
	public User getTodosByEmail(@PathVariable String email) {
		return myService.findByEmail(email);
	}

	
	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public User getOneUser(@PathVariable long id) {
		return myService.findOne(id);
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		myService.delete(id);
	}
	
	@RequestMapping( method = RequestMethod.POST)
	public User addUser(@RequestBody User user) {
		// to move ------------------------------------
		user.setRole(Role.USER); 
		String crypt = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(crypt);
		//---------------------------------------------
		return myService.add(user);

	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public User updateUser(@PathVariable long id, @RequestBody User user) {
		user.setId(id);
		return myService.update(user);
	}
		

}