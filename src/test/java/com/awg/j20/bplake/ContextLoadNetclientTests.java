package com.awg.j20.bplake;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(profiles = {"comp-remote", "netclient"})
class ContextLoadNetclientTests {

	@Test
	void contextLoads() {
	}

}
