package org.evaluation.expression.todos.service.impl;

import org.evaluation.expression.todos.Todos;
import org.evaluation.expression.todos.repo.TodosRepo;
import org.evaluation.expression.todos.service.TodosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodosServiceImpl implements TodosService {

	@Autowired
	private TodosRepo todosRepo;

	@Override
	public Todos save(Todos todos) {
		return todosRepo.save(todos);
	}

	@Override
	public Todos findById(Long id) {
		return todosRepo.findById(id).get();
	}

	@Override
	public Iterable<Todos> findAll() {
		return todosRepo.findAll();
	}

	@Override
	public void deleteById(Long id) {
		todosRepo.deleteById(id);

	}

	@Override
	public boolean existsById(Long id) {
		return todosRepo.existsById(id);
	}

}
