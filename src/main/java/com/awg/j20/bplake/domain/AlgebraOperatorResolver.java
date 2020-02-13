package com.awg.j20.bplake.domain;

public class AlgebraOperatorResolver {
	
	public static AlgebraOperator resolve(String operation) {
		if(operation == null || operation.length() == 0) {
			return AlgebraOperatorEnum.VOID;
		}
		String lowerOp = operation.toLowerCase();
		//TODO: build:
		if(lowerOp.equals("mult")) {
			return AlgebraOperatorEnum.MULT;
		}
		if(lowerOp.equals("add")) {
			return AlgebraOperatorEnum.ADD;
		}
		
		return AlgebraOperatorEnum.VOID;
	}
}
