package com.awg.j20.bplake.cntrl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.awg.j20.bplake.cntrl.ArithmeticCntrlOperatorResolver;
import com.awg.j20.bplake.domain.AlgebraOperator;
import com.awg.j20.bplake.domain.AlgebraOperatorEnum;

@ExtendWith(SpringExtension.class)
public class ArithmeticCntrlOperatorResolverTest {
	
	@Test
	void shouldTolerate_EmptyStringAsVoid() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldTolerate_NullAsVoid() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve(null);
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldResolve_UpperCaseMult() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("MULT");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isTrue();
	}
	
	@Test
	void shouldResolve_MixCaseMult() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("MuLt");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isTrue();
	}
	
	@Test
	void shouldResolve_Mult() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("mult");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_LongMult() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("multiplication");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_MultipleMult() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("multmult");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_Numbers() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("12345");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
	
	@Test
	void shouldDecline_ArithmeticOperators() {
		AlgebraOperator actual = ArithmeticCntrlOperatorResolver.resolve("*");
		
		AlgebraOperatorEnum c = (AlgebraOperatorEnum)actual;
		
		assertThat(actual).isInstanceOf(AlgebraOperatorEnum.class);
		assertThat(AlgebraOperatorEnum.MULT.equals(c)).isFalse();
		assertThat(AlgebraOperatorEnum.VOID.equals(c)).isTrue();
	}
}
