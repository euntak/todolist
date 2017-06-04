package kr.or.connect.todo.persistence;

public class TodoSqls {

	// count
	static final String UNCOMPLETED_COUNT =
			"SELECT COUNT(*) FROM todo WHERE completed = 0";
	
	// select
	static final String SELECT_ALL_LIST =
			"SELECT * FROM todo";
	
	// delete
	static final String DELETE_COMPLETED_LIST = 
			"DELETE FROM todo WHERE completed = 1";
	static final String DELETE_TODO_BY_ID = 
			"DELETE FROM todo WHERE id = :id";
	
	// update
	static final String UPDATE_STATUS_BY_ID = 
			"UPDATE todo SET completed = :completed WHERE id = :id";

}
