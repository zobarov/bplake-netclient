package com.awg.j20.bplake.domain;

public enum AlgebraOperatorEnum implements AlgebraOperator {
	VOID {
		@Override
		public Integer evaluate(Integer a, Integer b) {
			return 0;
		}
	},
	MULT {
		@Override
		public Integer evaluate(Integer a, Integer b) {
			return a * b;
		}
	},
	ADD {
		@Override
		public Integer evaluate(Integer a, Integer b) {
			return a + b;
		}
	}

}
