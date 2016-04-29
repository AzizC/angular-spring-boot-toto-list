package com.example.spring;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long>  {

	public User findByEmail(String email);
	
}
