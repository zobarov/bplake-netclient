package com.awg.j20.bplake.cntrl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.awg.j20.bplake.serv.ComputeService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ArithmeticCntrl.class)
@ActiveProfiles(profiles = {"comp-local"})
public class ArithmeticCntrlTest {
	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private ComputeService mockComputeService;

	@DisplayName("Main contract for endpoint")
    @Test
    void getMathWithAlgebra() throws Exception {
        mockMvc.perform(get("/math/algebra/mult")
        				.param("operandA", "2")
        				.param("operandB", "3"))
        .andExpect(status().isOk());
    }
	

}
