package org.evaluation.expression.controller;

import org.evaluation.expression.constants.APIConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = APIConstants.REST_DIGITAL_CHANGER)
@Api(tags = { APIConstants.DIGITAL_CHANGER_TAG })
public class DigitalChangerController {

	@ApiOperation(value = APIConstants.ADD_TODOS)
	@RequestMapping(method = RequestMethod.POST, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> addTodos(@RequestBody String digitalDigit) throws JSONException {
		JSONObject jsonObject = new JSONObject();
		String getDigitalPattern = digitalDigit;
		System.out.println("****************************************************");
		System.out.println("*****************     INPUT       ******************");
		System.out.println();
		System.out.println(digitalDigit);
		String num = "";

		System.out.println(getDigitalPattern);

		final String ZERO = " _\n| |\n|_|";

		final String ONE = "\n  |\n  |";

		final String TWO = " _\n _|\n|_";

		final String THREE = " _\n _|\n _|";

		final String FOUR = "\n|_|\n  |";

		final String FIVE = " _\n|_\n _|";

		final String SIX = " _\n|_\n|_|";

		final String SEVEN = " _\n  |\n  |";

		final String EIGHT = " _\n|_|\n|_|";

		final String NINE = " _\n|_|\n _|";

		switch (getDigitalPattern) {

		case ZERO:
			System.out.println(0);
			num = "0";
			break;
		case ONE:
			System.out.println(1);
			num = "1";
			break;

		case TWO:
			System.out.println(2);
			num = "2";
			break;
		case THREE:
			System.out.println(3);
			num = "3";
			break;
		case FOUR:
			System.out.println(4);
			num = "4";
			break;
		case FIVE:
			System.out.println(5);
			num = "5";
			break;
		case SIX:
			System.out.println(6);
			num = "6";
			break;
		case SEVEN:
			System.out.println(7);
			num = "7";
			break;
		case EIGHT:
			System.out.println(8);
			num = "8";
			break;
		case NINE:
			System.out.println(9);
			num = "9";
			break;

		}

		jsonObject.put("Number", num);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

}
