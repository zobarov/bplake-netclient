package com.awg.j20.bplake.cntrl;

import com.awg.j20.bplake.domain.AlgebraOperator;
import com.awg.j20.bplake.domain.AlgebraOperatorEnum;

/**
 * Resolves appropriate {@code AlgebraOperator} from income 
 * controller mapping commands.
 */
public class ArithmeticCntrlOperatorResolver {
	
	public static AlgebraOperator resolve(String ctrlCommand) {
		if(ctrlCommand == null || ctrlCommand.length() == 0) {
			return AlgebraOperatorEnum.VOID;
		}
		String operator = ctrlCommand.toLowerCase();

		if(operator.equals("mult")) {
			return AlgebraOperatorEnum.MULT;
		}
		if(operator.equals("add")) {
			return AlgebraOperatorEnum.ADD;
		}
		
		return AlgebraOperatorEnum.VOID;
	}
}
