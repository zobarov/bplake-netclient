package com.awg.j20.bplake.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Contains operands and operator for processing.
 */
public class Computation {
	@JsonDeserialize(as = AlgebraOperatorEnum.class)
	private AlgebraOperator algebraOperation;
	private Integer operandA;
	private Integer operandB;
	
	public Computation() {
		// for serialization
	}
	
	public Computation(AlgebraOperator algOperation, Integer opA, Integer opB) {
		this.algebraOperation = algOperation;
		this.operandA = opA;
		this.operandB = opB;
	}

	public AlgebraOperator getAlgebraOperation() {
		return algebraOperation;
	}

	public Integer getOperandA() {
		return operandA;
	}

	public Integer getOperandB() {
		return operandB;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{Computation: ");
		 sb.append("operator:").append(algebraOperation)
		   .append(", A:").append(operandA)
		   .append(", B:").append(operandB)
		   .append("}");
		return sb.toString();
	}
}
