package com.awg.j20.bplake.serv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;

/**
 * Computation service that compute locally.
 */
@Service
@Profile("computator")
public class LocalComputeService implements ComputeService {
	private Logger logger = LoggerFactory.getLogger("LocalComputeService");

	@Override
	public ComputationResult compute(Computation computation) {
		logger.info("Compute locally for: " + computation);
		
		ComputationResult compResult = ComputationResult.forComputation(computation);
		Integer evaluatedResult = computation.getAlgebraOperation()
									.evaluate(computation.getOperandA(),
											  computation.getOperandB());
		
		compResult.withResult(evaluatedResult);
		compResult.withComputationType("Local");
		return compResult;
	}

}
