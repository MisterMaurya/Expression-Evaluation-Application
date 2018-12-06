package org.evaluation.expression.controller;

import org.evaluation.expression.constants.APIConstants;
import org.evaluation.expression.todos.Todos;
import org.evaluation.expression.todos.service.TodosService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = APIConstants.REST_BASE_TODOS_URL)
@Api(tags = { APIConstants.TODOS_TAG })
public class TodosController {

	@Autowired
	private TodosService todosService;

	/**************************************************************************
	 * (1). CREATE A TODOS
	 **************************************************************************/

	/**
	 * @param todos
	 * @return Response code 200 if todos successfully created
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstants.ADD_TODOS)
	@RequestMapping(method = RequestMethod.POST, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> addTodos(@RequestBody Todos todos) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		if (todos.getContent().length() == 0) {
			jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.CONTENT_CAN_NOT_EMPTY);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);

		}
		todosService.save(todos);
		jsonObject.put(APIConstants.STATUS, APIConstants.TODOS_ADD_SUCCESSFULLY);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

	/**************************************************************************
	 * (2). GET A TODOS
	 **************************************************************************/

	/**
	 * @param id
	 * @return Response code 200 if todos successfully get
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstants.GET_A_TODOS)
	@RequestMapping(value = APIConstants.REST_PATH_ID, method = RequestMethod.GET, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> findOneTodos(@PathVariable Long id) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		boolean isTodosExists = todosService.existsById(id);

		if (!isTodosExists) {
			jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.TODOS_NOT_EXISIS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(todosService.findById(id), HttpStatus.OK);
	}

	/**************************************************************************
	 * (3). DELETE A TODOS
	 **************************************************************************/

	/**
	 * @param id
	 * @return Response code 200 if todos successfully delete
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstants.DELETE_A_TODOS)
	@RequestMapping(value = APIConstants.REST_PATH_ID, method = RequestMethod.DELETE, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> deleteTodos(@PathVariable Long id) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		boolean isTodosExists = todosService.existsById(id);

		if (!isTodosExists) {
			jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.TODOS_NOT_EXISIS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		todosService.deleteById(id);
		jsonObject.put(APIConstants.STATUS, APIConstants.TODOS_DELETE_SUCCESSFULLY);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	/**************************************************************************
	 * (4). UPDATE A TODOS
	 **************************************************************************/

	/**
	 * @param id
	 * @param content
	 * @return Response code 200 if todos successfully updated
	 * @throws JSONException
	 */
	@ApiOperation(value = APIConstants.UPDATE_A_TODOS)
	@RequestMapping(value = APIConstants.REST_PATH_ID, method = RequestMethod.PUT, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> updateTodos(@PathVariable Long id, @RequestBody String content) throws JSONException {

		System.out.println(content);

		JSONObject jsonObject = new JSONObject();
		boolean isTodosExists = todosService.existsById(id);

		if (!isTodosExists) {
			jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.TODOS_NOT_EXISIS);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
		}
		Todos existingTodos = todosService.findById(id);
		existingTodos.setContent(content);
		todosService.save(existingTodos);
		jsonObject.put(APIConstants.STATUS, APIConstants.TODOS_UPDATE_SUCCESSFULLY);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

	/**************************************************************************
	 * (5). GET ALL TODOS
	 **************************************************************************/

	/**
	 * @return Response code 200 if all todos successfully get
	 * @throws JSONException
	 */
	@ApiOperation(value = APIConstants.GET_ALL_TODOS)
	@RequestMapping(value = APIConstants.TODOS_LIST, method = RequestMethod.GET, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> getAllTodos() throws JSONException {

		return new ResponseEntity<>(todosService.findAll(), HttpStatus.OK);
	}

}