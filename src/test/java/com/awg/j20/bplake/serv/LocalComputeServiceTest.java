package com.awg.j20.bplake.serv;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.awg.j20.bplake.domain.AlgebraOperatorEnum;
import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;

@ExtendWith(SpringExtension.class)
@ActiveProfiles(profiles = {"comp-local", "computator"})
@SpringBootTest(classes = {LocalComputeService.class})
public class LocalComputeServiceTest {
	@Autowired
	private LocalComputeService computeSrvUnderTest;
	
	@Test
	public void shouldReturn_BasicMult() {
		Computation comp = new Computation(AlgebraOperatorEnum.MULT, 3, 8);
		
		ComputationResult actual = computeSrvUnderTest.compute(comp);

		assertThat(actual).isNotNull();
		assertThat(actual).extracting("result").isEqualTo(24);
	}
	
	/** I don't want to go to standard Java operator testing here.
	 * Main point for me to make sure the service setting it to the correct field 
	 * for further bypassing.
	 *  **/
}
