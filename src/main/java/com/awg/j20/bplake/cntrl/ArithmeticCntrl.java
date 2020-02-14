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

@RestController
@RequestMapping("/math")
public class ArithmeticCntrl {
	private Logger logger = LoggerFactory.getLogger("ArithmeticCntrl");
	@Autowired
	private ComputeService computeService;

	@GetMapping(value="/algebra/{operation}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ComputationResult> algebra(@PathVariable("operation") String operation,
						  @RequestParam("operandA") String operandA,
						  @RequestParam("operandB") String operandB) {
		
		logger.info("Got request: " + operation + "A=" + operandA + " * B=" + operandB);
		
		//TODO: validate and use direct:
		Integer a = Integer.valueOf(operandA);
		Integer b = Integer.valueOf(operandB);
		
		Computation computation = new Computation(ArithmeticCntrlOperatorResolver.resolve(operation), a, b);
		
		ComputationResult computationResult = computeService.compute(computation);
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
		return new ResponseEntity<ComputationResult>(computationResult, headers, HttpStatus.OK);
	}

}
