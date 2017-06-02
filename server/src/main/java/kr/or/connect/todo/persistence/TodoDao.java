package kr.or.connect.todo.persistence;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import kr.or.connect.todo.domain.Todo;

@Repository
public class TodoDao {
	private NamedParameterJdbcTemplate jdbc;
	private SimpleJdbcInsert insertAction;
	
	@Autowired
	public TodoDao(DataSource dataSource) {
		this.jdbc = new NamedParameterJdbcTemplate(dataSource);
		this.insertAction = new SimpleJdbcInsert(dataSource)
				.withTableName("todo")
				.usingGeneratedKeyColumns("id");
	}
	
	private RowMapper<Todo> rowMapper = (rs, i) -> {
		Todo todo = new Todo();
		todo.setId(rs.getInt("id"));
		todo.setTodo(rs.getString("todo"));
		todo.setCompleted(rs.getInt("completed"));
		todo.setDate((rs.getTimestamp("date")));
		return todo;
	};
	
	public List<Todo> selectAllTodoList() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_ALL_LIST, params, rowMapper);
	}
	
	public List<Todo> selectActiveTodoList() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_ACTIVE_LIST, params, rowMapper);
	}
	
	public List<Todo> selectCompletedTodoList() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.query(TodoSqls.SELECT_COMPLETED_LIST, params, rowMapper);
	}
	
	public int unCompletedCount() {
		Map<String, Object> params = Collections.emptyMap();
		return jdbc.queryForObject(TodoSqls.UNCOMPLETED_COUNT, params, Integer.class);
	}
	
	public int addTodo(Todo todo) {
		System.out.println(todo);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("todo", todo.getTodo());
		params.put("date", new Timestamp(System.currentTimeMillis()));
		params.put("completed", 0);
		return insertAction.executeAndReturnKey(params).intValue();
	}
	
	public int updateTodo(Todo todo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", todo.getId());
		params.put("completed", todo.getCompleted());
		return jdbc.update(TodoSqls.UPDATE_STATUS_BY_ID, params);
	}
	
	public int deleteCompletedTodoList() {
		Map<String, ?> params = Collections.singletonMap("completed", 1);;
		return jdbc.update(TodoSqls.DELETE_COMPLETED_LIST, params);
	}
}
