package com.awg.j20.bplake.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum AlgebraOperatorEnum implements AlgebraOperator {
	@JsonProperty("void")
	VOID {
		@Override
		public Integer evaluate(Integer a, Integer b) {
			return 0;
		}
	},
	@JsonProperty("mult")
	MULT {
		@Override
		public Integer evaluate(Integer a, Integer b) {
			return a * b;
		}
	},
	@JsonProperty("add")
	ADD {
		@Override
		public Integer evaluate(Integer a, Integer b) {
			return a + b;
		}
	}
}
