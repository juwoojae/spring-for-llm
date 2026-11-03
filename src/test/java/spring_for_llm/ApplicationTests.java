package spring_for_llm;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class ApplicationTests {

	@MockitoBean
	private AiRunner aiRunner;

	@Test
	void contextLoads() {
	}

}
