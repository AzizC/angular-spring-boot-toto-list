package com.example.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin
@RestController
@RequestMapping("/todos")
public class TodoController {
	
	@Autowired
	private TodoService myService;
	
	
	@RequestMapping(value="/user/{idUser}", method = RequestMethod.GET)
	public List<Todo> getAllTodos(@PathVariable long idUser) {
		return myService.findAllByUser(idUser);
	}
	
	@RequestMapping(value="/user/{idUser}/stat/{stat}", method = RequestMethod.GET)
	public List<Todo> getTodosByStat(@PathVariable long idUser, @PathVariable String stat) {
		return myService.findByStat(idUser, stat);
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public Todo getOneTodo(@PathVariable long id) {
		return myService.findOne(id);
	}
	
	@RequestMapping(value="/user/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable long id) {
		myService.delete(id);
	}
	
	@RequestMapping( method = RequestMethod.POST)
	public Todo addTodo(@RequestBody Todo todo) {
		return myService.add(todo);

	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.PUT)
	public Todo updateTodo(@RequestBody Todo todo) {
		return myService.update(todo);
	}

}
