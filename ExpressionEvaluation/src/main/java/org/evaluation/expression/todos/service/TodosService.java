package org.evaluation.expression.todos.service;

import org.evaluation.expression.todos.Todos;

public interface TodosService {

	// save todos
	public Todos save(Todos todos);

	// find a todos by id
	public Todos findById(Long id);

	// find all todos
	public Iterable<Todos> findAll();

	// delete a todos
	void deleteById(Long id);

	// check todos is exists or not
	boolean existsById(Long id);
}
