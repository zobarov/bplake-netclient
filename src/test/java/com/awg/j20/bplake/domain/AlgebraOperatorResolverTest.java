package com.awg.j20.bplake.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class AlgebraOperatorResolverTest {
	
	@Test
	void shouldTolerate_EmptyStringAsVoid() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldTolerate_NullAsVoid() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve(null);
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldResolve_UpperCaseMult() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("MULT");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isTrue();
	}
	
	@Test
	void shouldResolve_MixCaseMult() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("MuLt");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isTrue();
	}
	
	
	
	@Test
	void shouldResolve_Mult() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("mult");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_LongMult() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("multiplication");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_MultipleMult() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("multmult");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_Numbers() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("12345");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_ArithmeticOperators() {
		AlgebraOperator actual = AlgebraOperatorResolver.resolve("*");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}

}
