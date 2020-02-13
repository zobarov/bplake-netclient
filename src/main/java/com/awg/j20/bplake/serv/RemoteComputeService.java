package com.awg.j20.bplake.serv;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;

@Service
@Profile("comp-remote")
public class RemoteComputeService implements ComputeService {

	@Override
	public ComputationResult compute(Computation computation) {
		// TODO Auto-generated method stub
		return null;
	}

}
