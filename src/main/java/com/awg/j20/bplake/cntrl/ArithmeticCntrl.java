package com.awg.j20.bplake.cntrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.awg.j20.bplake.domain.AlgebraOperatorResolver;
import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;
import com.awg.j20.bplake.serv.ComputeService;

@RestController
@RequestMapping("/math")
public class ArithmeticCntrl {
	private Logger logger = LoggerFactory.getLogger("ArithmeticCntrl");
	@Autowired
	private ComputeService computeService;

	@GetMapping("/algebra/{operation}")
	public ComputationResult algebra(@PathVariable("operation") String operation,
						  @RequestParam("operandA") String operandA,
						  @RequestParam("operandB") String operandB) {
		
		logger.info("Got request: " + operation + "A=" + operandA + " * B=" + operandB);
		
		//TODO: validate and use direct:
		Integer a = Integer.valueOf(operandA);
		Integer b = Integer.valueOf(operandB);
		
		Computation computation = new Computation(AlgebraOperatorResolver.resolve(operation), a, b);
		
		ComputationResult computationResult = computeService.compute(computation);
		return computationResult;
	}

}
