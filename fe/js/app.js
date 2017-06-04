(function (window) {
	'use strict';

	var ENTER_KEY = 13;
	var todos = [];
	var filters = '/all';

	var Todo = {

		init: function () {
			Todo.getAllList();
			Todo.bindEvents();
		},
		bindEvents: function () {
			$('.todoapp input.new-todo').on('keyup', Todo.addTodo);
			$('.todo-list').on('change', '.toggle', Todo.toggle)
						   .on('click', '.destroy', Todo.removeTodo);

			$('footer ul.filters li a').on('click', Todo.setFilters);
			$('footer button.clear-completed').on('click', Todo.removeCompletedlist);
		},
		getTodoTemplete: function (t) {
			return (t.completed
				? '<li class="completed" data-id=' + t.id + '>'
				: '<li data-id=' + t.id + '>')
				+ '<div class="view">'
				+ '<input class="toggle" type="checkbox"' + (t.completed ? 'checked' : '') + '>'
				+ '<label>' + t.todo + '</label>'
				+ '<button class="destroy"></button>'
				+ '</div>'
				+ '<input class="edit" value="' + t.todo + '">'
				+ '</li>';
		},
		getAllList: function () {
			var result = Todo.todoApi('GET', 'api/todos');
			result.done(function (res) {
				todos = res;
				Todo.rendering();
			})
		},
		toggle: function (event) {
			event.preventDefault();

			var $list = $(this).closest('li');
			var $checkbox = $list.find('input[type="checkbox"]');

			var id = $list.data('id');
			var index = Todo.getIndexOfArray(id);

			todos[index].completed = !todos[index].completed;

			var data = { 'id': id, 'completed': ((todos[index].completed) ? 1 : 0) };
			var result = Todo.todoApi('PUT', '/api/todos/', JSON.stringify(data));

			result.then(function (res) {
				if (res) {
					if ($list.hasClass('completed')) {
						$checkbox.attr('checked', false);
						$list.removeClass('completed');
					} else {
						$checkbox.attr('checked', true);
						$list.addClass('completed');
					}
				}
				Todo.rendering();
			});
		},
		addTodo: function (event) {
			event.preventDefault();

			var $input = $(event.target);
			var val = $input.val().trim();

			if (event.which !== ENTER_KEY || !val) {
				return;
			}

			var result = Todo.todoApi('POST', '/api/todos', JSON.stringify({ todo: val }));
			result.then(function (res) {
				todos.push({
					id: res,
					completed: false,
					todo: val,
				});
				$input.val('');
				Todo.rendering();
			})
		},
		getActiveList: function () {
			return todos.filter(function (todo) {
				return !todo.completed;
			});
		},
		getCompletedList: function () {
			return todos.filter(function (todo) {
				return todo.completed;
			});
		},
		setFilters: function (event) {
			event.preventDefault();

			var $element = $(this);
			var currentFilter = $element.attr('href').split('/')[1];

			$('.selected').removeClass('selected');
			$element.addClass('selected');

			if (currentFilter === 'active') filters = 'Active';
			else if (currentFilter === 'completed') filters = 'Completed';
			else filters = 'All';

			Todo.getFilteredTodoList();
			Todo.rendering();
		},
		getIndexOfArray: function (id) {
			for (var i = 0; i < todos.length; i++) {
				if (todos[i].id === id) return i;
			}
		},
		removeTodo: function (event) {
			var id = $(this).closest('li').data('id');
			var deleteTodoId = Todo.getIndexOfArray(id);
			var requestURL = '/api/todos/' + id;

			// server 
			var result = Todo.todoApi('DELETE', requestURL);
			result.then(function (res) {
				if (res) {
					todos.splice(deleteTodoId, 1);
				} else {
					console.log('failure to delete todo item');
				}
				Todo.rendering();
			});
		},
		todoApi: function (method, url, data = null) {
			return $.ajax({
				contentType: 'application/json; charset=UTF-8',
				method: method,
				url: url,
				data: data,
				dataType: 'json',
			});
		},
		getFilteredTodoList: function () {
			if (filters === 'Active') {
				return Todo.getActiveList();
			}

			if (filters === 'Completed') {
				return Todo.getCompletedList();
			}

			return todos;
		},
		rendering: function (event) {
			var filteredTodo = Todo.getFilteredTodoList();
			var $todoList = $('.todo-list');
			// clear all list 
			$todoList.html('');

			Todo.updateActiveTodoListCount();

			// rendering
			filteredTodo.map(function (t) {
				var html = Todo.getTodoTemplete(t);
				$todoList.prepend(html);
			});

			$(".new-todo").focus();
		},
		updateActiveTodoListCount: function () {
			var count = 0;
			todos.map(function (todo) {
				if (!todo.completed) count++;
			});

			$('.footer .todo-count strong').html(count);

			return count;
		},
		removeCompletedlist: function () {
			var result = Todo.todoApi('DELETE', '/api/todos');

			result.then(function (res) {
				todos = Todo.getActiveList();
				Todo.rendering();
			});
		}
	};

	Todo.init();

})(window);