package org.evaluation.expression.constants;

public class APIConstants {

	// REST
	public static final String REST_BASE_URL = "/expression/";
	public static final String REST_EVALUATE_URL = "/evaluate";
	public static final String LIBRARY = "/library";
	public static final String WITHOUT_LIBRARY = "/without/library";

	// *** SWAGGER TAG AND DESCRIPTION ***
	public static final String EXPRESSION_EVALUATION_TAG = "EXPRESSION CONTROLLER APIs";
	public static final String EXPRESSION_CONTROLLER_DESCRIPTION = "OPERATIONS PERTAINING TO EXPRESSION EVALUATION";

	// API CONTENT TYPE
	public static final String REST_JSON_CONTENT_TYPE = "application/json";
	public static final String RESULT = "result";
	public static final String RESPONSE_ERROR = "error_message";
	public static final String OPERATION_NOT_DECLARED = "Unable to find what operation want to perform";
	public static final String SYNTAX_ERROR = "Syntax Error";
	public static final String EXPRESSION_SOLVE_USING_API = "expression solve using api";
	public static final String EVALUATE_EXPRESSION = "Evaluate Expression";
	public static final String EXPRESSION_SOLVER = "Expression Solver";
	public static final String EXPRESSION_WITHOUT_LIBRARY = "expression solve without api";
	public static final String CAN_NOT_PERFORM_OPERATION = "you can not perform operation because of syntax";

}
