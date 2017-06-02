package kr.or.connect.todo.service;

import java.sql.Timestamp;
import java.util.Calendar;
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
	
	public List<Todo> getActiveTodoList() {
		return dao.selectActiveTodoList();
	}
	
	public List<Todo> getCompletedTodoList() {
		return dao.selectCompletedTodoList();
	}
	
	public int getUncompletedCount() {
		return dao.unCompletedCount();
	}

	public int addTodo(Todo todo) {
		return dao.addTodo(todo);
	}
	
	public int updateTodo(Todo todo) {
		return dao.updateTodo(todo);
	}
	
	public int deleteCompletedTodoList() {
		return dao.deleteCompletedTodoList();
	}

}
