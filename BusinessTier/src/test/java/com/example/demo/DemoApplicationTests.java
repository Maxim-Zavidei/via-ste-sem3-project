package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import Tier3.ServerCommunicator;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ServerCommunicator sCommunicator;

	@Test
	void getEvents() throws Exception
	{
		//assertEquals("[]", sCommunicator.fetchUserEventFromDatabase(2));
	}
	@Test
	void contextLoads() {
	}

}
