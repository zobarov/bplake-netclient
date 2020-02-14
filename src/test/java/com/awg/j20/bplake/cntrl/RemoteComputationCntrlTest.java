package com.awg.j20.bplake.cntrl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = RemoteComputationCntrl.class)
@ActiveProfiles(profiles = {"computator"})
public class RemoteComputationCntrlTest {
	@Autowired
    private MockMvc mockMvc;
	
	private static MediaType CONTENT_TYPE = new MediaType(MediaType.APPLICATION_JSON.getType(),
			  									  MediaType.APPLICATION_JSON.getSubtype(),
			  									  Charset.forName("UTF-8"));
	@MockBean
	private ComputeService mockComputeService;
	
	@Autowired
	private ObjectMapper objectMapper;

	@DisplayName("Main contract for backend computation endpoint")
    @Test
    void shouldOk_AllValidParams() throws Exception {	
		Computation expectedComputation = new Computation(AlgebraOperatorEnum.MULT, 5, 4);
		ComputationResult expectedCompResult = ComputationResult.forComputation(expectedComputation);
		expectedCompResult.withResult(20);
		expectedCompResult.withComputationType("TestDummyRemote");
				
		Mockito
			.when(mockComputeService.compute(Mockito.any()))
			.thenReturn(expectedCompResult);				
		
		//when:
        mockMvc.perform(post("/compute/submit")
        		.contentType(CONTENT_TYPE)
                .content(objectMapper.writeValueAsString(expectedComputation)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.computation").exists())
        
        .andExpect(jsonPath("$.computation.algebraOperation").exists())
        .andExpect(jsonPath("$.computation.algebraOperation").value("mult"))
        
        .andExpect(jsonPath("$.computation.operandA").exists())
        .andExpect(jsonPath("$.computation.operandA").value("5"))
        
        .andExpect(jsonPath("$.computation.operandB").exists())
        .andExpect(jsonPath("$.computation.operandB").value("4"))
        
        .andExpect(jsonPath("$.result").exists())
        .andExpect(jsonPath("$.result").value("20"))
        
        .andExpect(jsonPath("$.computationType").exists())
        .andExpect(jsonPath("$.computationType").value("TestDummyRemote"));
    }
	

}
