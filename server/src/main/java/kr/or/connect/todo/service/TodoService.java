package kr.or.connect.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@Service
public class TodoService {
	
	@Autowired
	private TodoDao dao;
	
	public List<Todo> getAllTodoList() {
		return dao.selectAllTodoList();
	}

	public int addTodo(Todo todo) {
		return dao.addTodo(todo);
	}
	
	public int updateTodo(Todo todo) {
		return dao.updateTodo(todo);
	}
	
	public int deleteTodo(String id) {
		return dao.deleteTodo(id);
	}
	
	public int deleteCompletedTodoList() {
		return dao.deleteCompletedTodoList();
	}

}
