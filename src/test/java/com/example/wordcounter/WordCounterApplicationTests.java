package com.example.wordcounter;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WordCounterApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test_main() {
		WordCounterApplication.main(new String[] {});
	}

}
