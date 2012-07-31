package com.example.telnet;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.telnet.client.ClientGateway;

/**
 * @author alexfdz
 *
 */
@ContextConfiguration(locations={"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class TelnetClientTest {

	@Autowired
	ClientGateway gateway;
	
	@Test
	public void testContext(){
		Assert.assertNotNull(gateway);
	}
	
	@Test
	public void testEcho() {
		String result = gateway.send("hello");
		Assert.assertEquals("echo: hello", result);
	}
	
}
