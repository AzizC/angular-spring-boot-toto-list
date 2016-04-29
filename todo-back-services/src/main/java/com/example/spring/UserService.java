package com.example.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	

	@Autowired
	private UserRepository userRepository;

	public User findOne(long id) {
		return userRepository.findOne(id);
	}

	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	public void delete(long id) {
		userRepository.delete(id);
	}

	public User add(User user) {
		return userRepository.save(user);
	}

	public  User  update(User  user) {
		return userRepository.save(user);
	}
	
	public User findByEmail(String email){
		return userRepository.findByEmail(email);
	}

}
