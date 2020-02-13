package com.awg.j20.bplake.serv;

import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;

public interface ComputeService {
	
	ComputationResult compute(Computation computation);
}
