package com.example.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;

	public Todo findOne(long id) {
		return todoRepository.findOne(id);
	}
	
	public List<Todo> findAllByUser(long idUser) {
		return (List<Todo>) todoRepository.findTodos(idUser);
	}

	public void delete(long id) {
		todoRepository.delete(id);
	}

	public Todo add(Todo todo) {
		return todoRepository.save(todo);
	}

	public Todo update(Todo todo) {
		return todoRepository.save(todo);
	}

	public List<Todo> findByStat(long idUser, String stat) {
		return todoRepository.findTodosByStat(idUser, stat);
	}

}
