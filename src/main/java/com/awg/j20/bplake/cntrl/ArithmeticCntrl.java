package com.awg.j20.bplake.cntrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;
import com.awg.j20.bplake.serv.ComputeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * REST controller to receive user's input for math operations.
 */
@RestController
@RequestMapping("/math")
@Api(value="RemoteAlgebra")
public class ArithmeticCntrl {
	private Logger logger = LoggerFactory.getLogger("ArithmeticCntrl");
	@Autowired
	private ComputeService computeService;

	@GetMapping(value="/algebra/{operation}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Send operands for remote computations",response = ComputationResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully calculated"),
            @ApiResponse(code = 500, message = "Remote computation in error")
    	}
    )
	public ResponseEntity<ComputationResult> algebra(@PathVariable("operation") String operation,
						  @RequestParam("operandA") Integer operandA,
						  @RequestParam("operandB") Integer operandB) {
		
		logger.info("Got request: " + operation + "A=" + operandA + " * B=" + operandB);
		
		Computation computation = new Computation(ArithmeticCntrlOperatorResolver.resolve(operation),
												  operandA, operandB);
		
		ComputationResult computationResult = computeService.compute(computation);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<ComputationResult>(computationResult, headers, HttpStatus.OK);
	}
}
