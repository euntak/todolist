package kr.or.connect.todo.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import kr.or.connect.todo.TodoApplication;
import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.persistence.TodoDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TodoApplication.class)
@Transactional
public class TodoDaoTest {

	@Autowired
	private TodoDao dao;

	
	@Test
	public void shouldGetAllList() {
		List<Todo> allTodoList = dao.selectAllTodoList();
		assertThat(allTodoList, is(notNullValue()));
	}
	
	@Test
	public void shouldAddTodo() {
		// given
		Todo todo = new Todo();
		todo.setTodo("JAVA WEB");
		
		// when
		Integer id = dao.addTodo(todo);
		
		// then
		Todo selectedTodo = dao.selectTodoById(id);
		assertThat(selectedTodo.getTodo(), is("JAVA WEB"));
	}
	
	@Test
	public void shouldUpdateTodo() {
		// given
		Todo todo = new Todo();
		todo.setTodo("JAVA WEB");
		
		// when
		Integer id = dao.addTodo(todo);
		
		Todo updateTodo = new Todo();
		updateTodo.setId(id);
		updateTodo.setCompleted(1);
		Integer affectId = dao.updateTodo(updateTodo);
		
		// then
		assertThat(affectId, is(1));
	}
	
	@Test
	public void shouldDeleteTodo() {
		// given
		Todo todo = new Todo();
		todo.setTodo("JAVA WEB");
		
		// when
		String id = String.valueOf(dao.addTodo(todo));
		Integer affectId = dao.deleteTodo(id);

		// then
		assertThat(affectId, is(1));
	}
	
	@Test
	public void shouldRemoveCompletedTodoList() {
		// given
		Todo todo1 = new Todo();
		todo1.setTodo("todo1");
	
		int id1 = dao.addTodo(todo1);
	
		todo1.setId(id1);
		todo1.setCompleted(1);
		
		dao.updateTodo(todo1);
		
		// when
		int affect = dao.deleteCompletedTodoList();
		
		// then
		assertThat(affect, is(1));
	}
}
