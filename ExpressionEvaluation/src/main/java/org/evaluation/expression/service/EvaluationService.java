package org.evaluation.expression.service;

import java.util.ArrayList;

public interface EvaluationService {

	public String[] findAllExpression(String expression);

	// get actual form of expression separated by comma
	// =sum((2)*3,(3*7)*9) --->> (2)*3,(3*7)*9
	// =max((3)*6,(8)^2) --->> (3)*6,(8)^2
	public String getActualExpression(String expression);

	// Get All actual expression and store in array
	// if pattern do not match so expression do not save
	// (2)*3,(3*7)*9 --->[(2)*3,(3*7)*9]
	public ArrayList<String> storeEachExpression(String actualExpression);

	// Evaluate Expression
	public Double evaluate(String expression);

	// Check Expression
	// Returns true if 'op2' has higher or same precedence as 'op1',
	// otherwise returns false.
	public boolean hasPrecedence(char op1, char op2);

	// A utility method to apply an operator 'op' on operands 'a'
	// and 'b'. Return the result.
	public Double applyOp(char op, Double double1, Double double2);

}
