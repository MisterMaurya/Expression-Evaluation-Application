package org.evaluation.expression.controller;

import org.evaluation.expression.constants.APIConstants;
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

@RestController
@RequestMapping(value = APIConstants.REST_BASE_URL + APIConstants.REST_EVALUATE_URL)
@Api(tags = { APIConstants.EXPRESSION_EVALUATION_TAG })
public class ExpressionController {

	@Autowired
	private EvaluationService evaluationService;

	@RequestMapping(method = RequestMethod.POST, produces = APIConstants.REST_JSON_CONTENT_TYPE)
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

		jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.OPERATION_NOT_DECLARED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}
}
