package org.evaluation.expression.controller;

import java.util.ArrayList;

import org.evaluation.expression.constants.APIConstants;
import org.evaluation.expression.constants.ExpressionConstants;
import org.evaluation.expression.service.EvaluationService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fathzer.soft.javaluator.DoubleEvaluator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author Pawan-Maurya
 *
 */

@RestController
@RequestMapping(value = APIConstants.REST_BASE_URL + APIConstants.REST_EVALUATE_URL)
@Api(tags = { APIConstants.EXPRESSION_EVALUATION_TAG })
public class ExpressionController {

	@Autowired
	private EvaluationService evaluationService;

	// EVALUATE MATHEMATICS EXPRESSION USING EXTERNAL LIBRARY
	// -------------------------------------------------------

	/**
	 * @param expression
	 * @return evaluate expression result
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstants.EVALUATE_EXPRESSION, notes = APIConstants.EXPRESSION_SOLVE_USING_API)
	@RequestMapping(value = APIConstants.LIBRARY, method = RequestMethod.POST, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> evaluateExpression(@RequestBody String expression) throws JSONException {
		JSONObject jsonObject = new JSONObject();

		Double result = 0.0;
		int counter = 0;
		if (expression.startsWith("=sum(") && expression.endsWith(")")) {

			for (String getOneExpression : evaluationService.findAllExpression(expression)) {
				try {
					result = Double.sum(result, new DoubleEvaluator().evaluate(getOneExpression));
				} catch (IllegalArgumentException e) {
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
			}
			jsonObject.put(APIConstants.RESULT, result.toString());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		} else if (expression.startsWith("=max") && expression.endsWith(")")) {
			result = 0.0;
			for (String getOneExpression : evaluationService.findAllExpression(expression)) {
				try {
					if (counter == 0) {
						result = new DoubleEvaluator().evaluate(getOneExpression);
					}
					result = Math.max(result, new DoubleEvaluator().evaluate(getOneExpression));
				} catch (IllegalArgumentException e) {
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
				counter++;
			}
			jsonObject.put(APIConstants.RESULT, result.toString());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

		} else if (expression.startsWith("=min") && expression.endsWith(")")) {
			result = 0.0;
			for (String getOneExpression : evaluationService.findAllExpression(expression)) {
				try {
					if (counter == 0) {
						result = new DoubleEvaluator().evaluate(getOneExpression);
					}
					result = Math.min(result, new DoubleEvaluator().evaluate(getOneExpression));
				} catch (IllegalArgumentException e) {
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
				counter++;
			}
			jsonObject.put(APIConstants.RESULT, result.toString());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		} else if (expression.startsWith("=avg") && expression.endsWith(")")) {
			result = 0.0;

			for (String getOneExpression : evaluationService.findAllExpression(expression)) {
				try {
					result = Double.sum(result, new DoubleEvaluator().evaluate(getOneExpression));
					counter++;
				} catch (IllegalArgumentException e) {
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
			}

			jsonObject.put(APIConstants.RESULT, (counter == 1) ? result : result / counter);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}

		try {
			jsonObject.put(APIConstants.RESULT, new DoubleEvaluator().evaluate(expression));
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		} catch (Exception e) {
			jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.OPERATION_NOT_DECLARED);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
	}

	// EVALUATE MATHEMATICS EXPRESSION Without EXTERNAL LIBRARY
	// -------------------------------------------------------

	/**
	 * @param expression
	 * @return evaluate expression result
	 * @throws JSONException
	 */

	@ApiOperation(value = APIConstants.EXPRESSION_SOLVER, notes = APIConstants.EXPRESSION_WITHOUT_LIBRARY)
	@RequestMapping(value = APIConstants.WITHOUT_LIBRARY, method = RequestMethod.POST, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> expressionSolver(@RequestBody String expression) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		Double result = 0.0;
		int counter = 0;

		if (expression.startsWith(ExpressionConstants.SUM_START)
				&& (expression.endsWith(ExpressionConstants.END_WITH))) {
			System.out.println("\n--------------- Addition Operation ------------------");
			String actualExpression = evaluationService.getActualExpression(expression);
			System.out.println("\n--------------- Actual Expression--------------------");
			System.out.println(actualExpression);

			ArrayList<String> expressionList = evaluationService.storeEachExpression(actualExpression);
			if (expressionList == null) {
				System.out.println("\nYou can not perform addition because your operation is break");
				jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.CAN_NOT_PERFORM_OPERATION);
				return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}

			for (String singleExpression : expressionList) {
				try {
					System.out.println("\nProcess to solve this expression : " + singleExpression);
					System.out.println();
					result = Double.sum(result, evaluationService.evaluate(singleExpression));
				} catch (Exception e) {
					e.printStackTrace();
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
			}
			System.out.println("\n\nAdditon Result : " + result);

			jsonObject.put(APIConstants.RESULT, result.toString());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

		}
		if (expression.startsWith(ExpressionConstants.MAX_START)
				&& (expression.endsWith(ExpressionConstants.END_WITH))) {

			System.out.println("\n--------------- Max Value Operation ------------------");
			String actualExpression = evaluationService.getActualExpression(expression);
			System.out.println("\n--------------- Actual Expression--------------------");
			System.out.println(actualExpression);

			ArrayList<String> expressionList = evaluationService.storeEachExpression(actualExpression);
			if (expressionList == null) {
				System.out.println("\nYou can not perform addition because your operation is break");
				jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.CAN_NOT_PERFORM_OPERATION);
				return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}

			for (String singleExpression : expressionList) {

				try {
					System.out.println("\nProcess to solve this expression : " + singleExpression);
					System.out.println();
					if (counter == 0) {
						result = evaluationService.evaluate(singleExpression);
					}

					result = Math.max(result, evaluationService.evaluate(singleExpression));
				} catch (Exception e) {
					e.printStackTrace();
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
				counter++;
			}
			System.out.println("\n\nMax Value Result : " + result);

			jsonObject.put(APIConstants.RESULT, result.toString());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

		}
		if (expression.startsWith(ExpressionConstants.MIN_START)
				&& (expression.endsWith(ExpressionConstants.END_WITH))) {
			System.out.println("\n--------------- Min Value Operation ------------------");
			String actualExpression = evaluationService.getActualExpression(expression);
			System.out.println("\n--------------- Actual Expression--------------------");
			System.out.println(actualExpression);

			ArrayList<String> expressionList = evaluationService.storeEachExpression(actualExpression);
			if (expressionList == null) {
				System.out.println("\nYou can not perform addition because your operation is break");
				jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.CAN_NOT_PERFORM_OPERATION);
				return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}

			for (String singleExpression : expressionList) {

				try {
					System.out.println("\nProcess to solve this expression : " + singleExpression);
					System.out.println();
					if (counter == 0) {
						result = evaluationService.evaluate(singleExpression);
					}

					result = Math.min(result, evaluationService.evaluate(singleExpression));
				} catch (Exception e) {
					e.printStackTrace();
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
				counter++;
			}
			System.out.println("\n\nMin Value Result : " + result);

			jsonObject.put(APIConstants.RESULT, result.toString());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}
		if (expression.startsWith(ExpressionConstants.AVG_START)
				&& (expression.endsWith(ExpressionConstants.END_WITH))) {

			System.out.println("\n--------------- Average Operation ------------------");
			String actualExpression = evaluationService.getActualExpression(expression);
			System.out.println("\n--------------- Actual Expression--------------------");
			System.out.println(actualExpression);

			ArrayList<String> expressionList = evaluationService.storeEachExpression(actualExpression);
			if (expressionList == null) {
				System.out.println("\nYou can not perform addition because your operation is break");
				jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.CAN_NOT_PERFORM_OPERATION);
				return new ResponseEntity<>(jsonObject.toString(), HttpStatus.BAD_REQUEST);
			}

			for (String singleExpression : expressionList) {
				try {
					System.out.println("\nProcess to solve this expression : " + singleExpression);
					System.out.println();
					result = Double.sum(result, evaluationService.evaluate(singleExpression));
				} catch (Exception e) {
					e.printStackTrace();
					jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SYNTAX_ERROR);
					return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
				}
				counter++;
			}
			System.out.println("\n\nAverage Result : " + ((counter == 1) ? result : result / counter));

			jsonObject.put(APIConstants.RESULT, result.toString());
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

		}

		jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.OPERATION_NOT_DECLARED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
	}

}
