package com.example.spring;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TodoRepository extends CrudRepository<Todo, Long> {
	
	//public List<Todo> findByStat(String stat);
	
	// liste des créneaux horaires d'un médecin
	@Query("select t from Todo t where t.idUser=?1")
	public List<Todo> findTodos(long idUser);
	
	@Query("select t from Todo t where t.idUser=?1 and t.stat=?2")
	public List<Todo> findTodosByStat(long idUser, String stat);

}