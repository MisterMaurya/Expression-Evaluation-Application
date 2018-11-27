package org.evaluation.expression.controller;

import org.evaluation.expression.constants.APIConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

@RestController
@RequestMapping(value = APIConstants.REST_BASE_URL + APIConstants.REST_EVALUATE_URL)
@Api(tags = { APIConstants.EXPRESSION_EVALUATION_TAG })
public class ExpressionController {

}
