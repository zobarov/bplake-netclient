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
	
	public static ComputationResult forComputation(Computation computation) {
		ComputationResult compResult = new ComputationResult();
		compResult.computation = computation;
		
		return compResult;
	}
	
	public ComputationResult withResult(Integer result) {
		this.result = result;
		return this;
	}
	
	public ComputationResult withComputationType(String type) {
		this.computationType = type;
		return this;
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
