package org.evaluation.expression.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.evaluation.expression.constants.APIConstants;
import org.evaluation.expression.constants.DigitalChangerConstants;
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

/**
 * @author Pawan-pc
 *
 */

@RestController
@RequestMapping(value = APIConstants.REST_DIGITAL_CHANGER)
@Api(tags = { APIConstants.DIGITAL_CHANGER_TAG })
public class DigitalChangerController {

	/**
	 * @param inputFilePath
	 * @return Response code 200 if output file successfully generated
	 * @throws JSONException
	 */

	@SuppressWarnings("resource")
	@ApiOperation(value = APIConstants.CHANGE_DIGITAL_TO_NUMERIC)
	@RequestMapping(method = RequestMethod.POST, produces = APIConstants.REST_JSON_CONTENT_TYPE)
	public ResponseEntity<?> changeDigitalToNumeric(@RequestBody String inputFilePath) throws JSONException {

		JSONObject jsonObject = new JSONObject();

		// split String add in this list
		ArrayList<String> splitSubString = new ArrayList<>();

		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(inputFilePath));
			String sCurrentLine = null;

			try {
				while ((sCurrentLine = br.readLine()) != null) {

					// split a line after three 3rd character
					String subStrings[] = sCurrentLine.split(DigitalChangerConstants.SPLIT);

					for (int i = 0; i < subStrings.length; i++) {

						// add split character substring in list
						splitSubString.add(subStrings[i]);
					}
				}
			} catch (IOException e1) {

				// throw exception when input file not successfully read
				jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.SOMETHING_WENT_WRONG);
				return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
			}
		} catch (FileNotFoundException e1) {

			// throw exception when input file not find
			jsonObject.put(APIConstants.RESPONSE_ERROR, APIConstants.INVALID_INPUT_FILE_PATH);
			return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);
		}

		// store every number in a line
		String num = "";
		String temp;
		ArrayList<String> aList = new ArrayList<>();

		for (int i1 = 0; i1 < splitSubString.size(); i1++) {

			try {
				temp = splitSubString.get(i1) + "\n" + splitSubString.get(i1 + 9) + "\n"
						+ splitSubString.get(i1 + 2 * 9);
				switch (temp) {
				case DigitalChangerConstants.ZERO:
					num = num + 0;
					break;
				case DigitalChangerConstants.ONE:
					num = num + 1;
					break;

				case DigitalChangerConstants.TWO:
					num = num + 2;
					break;
				case DigitalChangerConstants.THREE:
					num = num + 3;
					break;
				case DigitalChangerConstants.FOUR:
					num = num + 4;
					break;
				case DigitalChangerConstants.FIVE:
					num = num + 5;
					break;
				case DigitalChangerConstants.SIX:
					num = num + 6;
					break;
				case DigitalChangerConstants.SEVEN:
					num = num + 7;
					break;
				case DigitalChangerConstants.EIGHT:
					num = num + 8;
					break;
				case DigitalChangerConstants.NINE:
					num = num + 9;
					break;

				}

			} catch (Exception e) {

			}

			if (num.length() == 9) {
				aList.add(num);
				num = "";
			}

		}

		FileWriter writer = null;
		try {
			writer = new FileWriter("C:\\Users\\Pawan-pc\\Desktop\\o2.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedWriter buffer = new BufferedWriter(writer);

		for (String string1 : aList) {
			try {
				buffer.write(string1 + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			buffer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		jsonObject.put(APIConstants.STATUS, APIConstants.OUTPUT_FILE_SUCCESSFULLY_CREATED);
		return new ResponseEntity<>(jsonObject.toString(), HttpStatus.OK);

	}

}
