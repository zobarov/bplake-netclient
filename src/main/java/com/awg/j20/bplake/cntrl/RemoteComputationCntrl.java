package com.awg.j20.bplake.cntrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;
import com.awg.j20.bplake.serv.ComputeService;

@RestController
@RequestMapping("/compute")
@Profile("computator")
public class RemoteComputationCntrl {
	private Logger logger = LoggerFactory.getLogger("RemoteComputationCntrl");
	@Autowired
	private ComputeService computeService;

	@PostMapping(value = "/submit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ComputationResult computeOperation(@RequestBody Computation computation) {

		logger.info("Recieved for remote computation: " + computation);
		ComputationResult computationResult = computeService.compute(computation);
		return computationResult;
	}
}
