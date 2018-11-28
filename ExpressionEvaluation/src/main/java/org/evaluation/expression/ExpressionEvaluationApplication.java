package org.evaluation.expression;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fathzer.soft.javaluator.DoubleEvaluator;

@SpringBootApplication
public class ExpressionEvaluationApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpressionEvaluationApplication.class, args);

		String expression = "22+-7";
		Double result = null;
		try {
			result = new DoubleEvaluator().evaluate(expression);
		} catch (IllegalArgumentException e) {
			System.out.println("syntax error");
		}

		System.out.println(result);

	}
}
