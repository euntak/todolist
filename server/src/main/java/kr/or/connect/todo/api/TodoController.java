package kr.or.connect.todo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.service.TodoService;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	
	@GetMapping
	List<Todo> getAllTodoList() {
		return service.getAllTodoList();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	int addTodo(@RequestBody Todo todo) {
		return service.addTodo(todo);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	int updateTodo(@RequestBody Todo todo) {
		return service.updateTodo(todo);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.OK)
	int deleteCompletedTodoList() {
		return service.deleteCompletedTodoList();
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	int deleteTodo(@PathVariable("id") String id) {
		return service.deleteTodo(id);
	}
	
}
