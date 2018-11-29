package org.evaluation.expression.service.impl;

import java.util.ArrayList;
import java.util.Stack;

import org.evaluation.expression.constants.ExpressionConstants;
import org.evaluation.expression.service.EvaluationService;
import org.springframework.stereotype.Service;

@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Override
	public String[] findAllExpression(String expression) {

		return expression.substring(5, expression.length() - 1).split("[,]");
	}

	@Override
	public String getActualExpression(String expression) {
		System.out.println("\nUser Input : " + expression);
		return expression.substring(5, expression.length() - 1);
	}

	@Override
	public ArrayList<String> storeEachExpression(String actualExpression) {
		String pattern = ExpressionConstants.PATTERN;
		String singleExpression[] = actualExpression.split(ExpressionConstants.EXPRESSION_SPLIT);
		ArrayList<String> expressionList = new ArrayList<>();
		System.out.println("\n\n------------ Expressions ----------------");
		for (String expression : singleExpression) {
			if (expression.matches(pattern)) {
				expressionList.add(expression);
				System.out.println(expression);
			} else {
				System.out.println(ExpressionConstants.PATTERN_ERROR + expression);
				return null;
			}
		}
		return expressionList;
	}

	@Override
	public Double evaluate(String singleExpression) {
		char[] tokens = singleExpression.toCharArray();

		// Stack for numbers: 'values'
		Stack<Double> values = new Stack<Double>();

		// Stack for Operators: 'ops'
		Stack<Character> ops = new Stack<Character>();

		for (int i = 0; i < tokens.length; i++) {

			// Current token is a whitespace, skip it
			if (tokens[i] == ' ')
				continue;

			// Current token is a number, push it to stack for numbers
			if (tokens[i] >= '0' && tokens[i] <= '9') {
				StringBuffer sbuf = new StringBuffer();

				// There may be more than one digits in number
				while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
					sbuf.append(tokens[i++]);
				i--;

				pushValue(values, Double.parseDouble(sbuf.toString()));
			}

			// Current token is an opening brace, push it to 'ops'
			else if (tokens[i] == '(') {

				pushOperator(ops, tokens[i]);
				// ops.push(tokens[i]);

			}

			// Closing brace encountered, solve entire brace
			else if (tokens[i] == ')') {
				System.out.println("solve bracket");
				while (ops.peek() != '(')

					pushValue(values, applyOp(popOperator(ops), popValue(values), popValue(values)));
				// values.push(applyOp(ops.pop(), values.pop(), values.pop()));

				// ops.pop();
				popOperator(ops);

			}

			// Current token is an operator.
			else if (tokens[i] == '+' || tokens[i] == '-' || tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {
				// While top of 'ops' has same or greater precedence to current
				// token, which is an operator. Apply operator on top of 'ops'
				// to top two elements in values stack
				while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))

					// values.push(applyOp(ops.pop(), values.pop(), values.pop()));
					pushValue(values, applyOp(popOperator(ops), popValue(values), popValue(values)));

				// Push current token to 'ops'.

				pushOperator(ops, tokens[i]);
				// ops.push(tokens[i]);

			}
		}

		// Entire expression has been parsed at this point, apply remaining
		// ops to remaining values
		while (!ops.empty())
			pushValue(values, applyOp(popOperator(ops), popValue(values), popValue(values)));

		// Top of 'values' contains result, return it
		return popValue(values);
	}

	@Override
	public boolean hasPrecedence(char op1, char op2) {
		if (op2 == '(' || op2 == ')')
			return false;
		if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
			return false;
		if ((op1 == '^' && (op2 == '*' || op2 == '-' || op2 == '+' || op2 == '/'))) {
			return false;
		}
		if ((op1 == '(' || op1 == '(') && op2 == '^') {
			return false;
		} else
			return true;
	}

	@Override
	public Double applyOp(char op, Double double1, Double double2) {

		switch (op) {
		case '^':
			return Math.pow(double2, double1);
		case '+':
			return double2 + double1;
		case '-':
			return double2 - double1;
		case '*':
			return double2 * double1;
		case '/':
			if (double1 == 0)
				throw new UnsupportedOperationException("Cannot divide by zero");
			return double2 / double1;
		}
		return 0.0;
	}

	private Double popValue(Stack<Double> values) {
		Double val = values.pop();
		System.out.println("Value Pop In Value Stack     " + val.toString());
		return val;
	}

	private Character popOperator(Stack<Character> values) {
		Character val = values.pop();
		System.out.println("Value Pop In Operator Stack  " + val.toString());
		return val;
	}

	private Double pushValue(Stack<Double> values, Double value) {
		Double val = values.push(value);
		System.out.println("Value Push In Value Stack    " + val.toString());
		return val;
	}

	private Character pushOperator(Stack<Character> values, Character value) {
		Character val = values.push(value);
		System.out.println("Value Push In Opearator Stack" + val.toString());
		return val;
	}

}
