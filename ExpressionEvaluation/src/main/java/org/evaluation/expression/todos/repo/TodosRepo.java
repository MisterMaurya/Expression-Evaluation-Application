package org.evaluation.expression.todos.repo;

import org.evaluation.expression.todos.Todos;
import org.springframework.data.repository.CrudRepository;

public interface TodosRepo extends CrudRepository<Todos, Long> {

}
