package com.awg.j20.bplake.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@JsonTest
public class ComputationResultTest {
	@Autowired
	private JacksonTester<ComputationResult> json;
	
	@Test
	void shouldSerialize() throws IOException {
		Computation computation = new Computation(AlgebraOperatorEnum.MULT, 6, 7);
		ComputationResult actual = ComputationResult.forComputation(computation);
		actual.withResult(42);
		actual.withComputationType("TestTypeSer");
		
		JsonContent<ComputationResult> actualContent = this.json.write(actual);
		
		//Would not work from Gradle:
		//assertThat(this.json.write(actual)).isEqualToJson("computation-result.json");
		
		assertThat(actualContent).hasJsonPathValue("computation");
		assertThat(actualContent).hasJsonPathValue("result");
		assertThat(actualContent).hasJsonPathValue("@.computationType");
        assertThat(actualContent)
        		.extractingJsonPathStringValue("@.computation.algebraOperation")
                .isEqualTo("mult");
        assertThat(actualContent)
				.extractingJsonPathNumberValue("@.computation.operandA")
				.isEqualTo(6);
        assertThat(actualContent)
				.extractingJsonPathNumberValue("@.computation.operandB")
				.isEqualTo(7);
        assertThat(actualContent)
				.extractingJsonPathNumberValue("result")
				.isEqualTo(42);
        assertThat(actualContent)
				.extractingJsonPathStringValue("@.computationType")
				.isEqualTo("TestTypeSer");
	}
	
	@Test
    public void shouldDeserialize() throws Exception {
        String content = "{\r\n" + 
        		"    \"computation\": {\r\n" + 
        		"        \"algebraOperation\": \"mult\",\r\n" + 
        		"        \"operandA\": 25,\r\n" + 
        		"        \"operandB\": 4\r\n" + 
        		"    },\r\n" + 
        		"    \"result\": 100,\r\n" + 
        		"    \"computationType\": \"TestTypeDeser\"\r\n" + 
        		"}";
        
        Computation expectedComp = new Computation(AlgebraOperatorEnum.MULT, 25, 4);
		ComputationResult expectedCompRes = ComputationResult.forComputation(expectedComp);
		expectedCompRes.withResult(100);
		expectedCompRes.withComputationType("TestTypeDeser");
		
		ObjectContent<ComputationResult> actual = this.json.parse(content);

		assertThat(actual).isEqualToComparingOnlyGivenFields(expectedCompRes, "computation.algebraOperation");
		assertThat(actual).isEqualToComparingOnlyGivenFields(expectedCompRes, "computation.operandA");
		assertThat(actual).isEqualToComparingOnlyGivenFields(expectedCompRes, "computation.operandB");
		assertThat(actual).isEqualToComparingOnlyGivenFields(expectedCompRes, "result");
		assertThat(actual).isEqualToComparingOnlyGivenFields(expectedCompRes, "computationType");
	}

}
