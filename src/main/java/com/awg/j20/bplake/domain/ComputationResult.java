package com.awg.j20.bplake.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("ComputationResult")
public class ComputationResult {
	@JsonProperty("computation")
	private Computation computation;
	@JsonProperty("result")
	private Integer result;
	@JsonProperty("computationType")
	private String computationType;
	
	/**
	 * Private to avoid immediate result assigning.
	 */
	private ComputationResult() {		
	}
	
	public static ComputationResult byComputation(Computation computation) {
		ComputationResult compResult = new ComputationResult();
		compResult.computation = computation;
		
		return compResult;
	}
	
	public void assignResult(Integer result) {
		this.result = result;
	}
	
	public void specifyComputationType(String type) {
		this.computationType = type;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{ComputationResult: ");
		 sb.append("for: ").append(this.computation)
		   .append(", result:").append(result)
		   .append(", type:").append(computationType)
		   .append("}");
		return sb.toString();
	}
	

}
