package com.awg.j20.bplake.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Enumerates all supported operations.
 */
public enum AlgebraOperatorEnum implements AlgebraOperator {
	@JsonProperty("void")
	VOID {
		@Override
		public Integer evaluate(Integer a, Integer b) {
			return null;
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
