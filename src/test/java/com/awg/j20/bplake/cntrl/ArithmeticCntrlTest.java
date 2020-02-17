package com.awg.j20.bplake.cntrl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.awg.j20.bplake.domain.AlgebraOperatorEnum;
import com.awg.j20.bplake.domain.Computation;
import com.awg.j20.bplake.domain.ComputationResult;
import com.awg.j20.bplake.serv.ComputeService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ArithmeticCntrl.class)
@ActiveProfiles(profiles = {"comp-local", "netclient"})
public class ArithmeticCntrlTest {
	@Autowired
    private MockMvc mockMvc;
	
	private static MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
			  											  MediaType.APPLICATION_JSON.getSubtype(),
			  											  Charset.forName("UTF-8"));
	@MockBean
	private ComputeService mockComputeService;

	@DisplayName("Main contract for endpoint")
    @Test
    void shouldOk_AllValidParams() throws Exception {	
		Computation expectedComputation = new Computation(AlgebraOperatorEnum.MULT, 2, 3);
		ComputationResult expectedCompResult = ComputationResult.forComputation(expectedComputation);
		expectedCompResult.withResult(6);
		expectedCompResult.withComputationType("TestDummy");
				
		Mockito
			.when(mockComputeService.compute(Mockito.any()))
			.thenReturn(expectedCompResult);
		
		//when:
        mockMvc.perform(get("/math/algebra/mult")
        			.accept(CONTENT_TYPE)
        			.param("operandA", "2")
        			.param("operandB", "3"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.computation").exists())
        
        .andExpect(jsonPath("$.computation.algebraOperation").exists())
        .andExpect(jsonPath("$.computation.algebraOperation").value("mult"))
        
        .andExpect(jsonPath("$.computation.operandA").exists())
        .andExpect(jsonPath("$.computation.operandA").value("2"))
        
        .andExpect(jsonPath("$.computation.operandB").exists())
        .andExpect(jsonPath("$.computation.operandB").value("3"))
        
        .andExpect(jsonPath("$.result").exists())
        .andExpect(jsonPath("$.result").value("6"))
        
        .andExpect(jsonPath("$.computationType").exists())
        .andExpect(jsonPath("$.computationType").value("TestDummy"));
    }
	

}
