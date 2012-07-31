package com.example.telnet;

import java.io.File;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
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
	
	private String contextPath = getClass().getResource("/").getPath();
	
	@Test
	public void testContext(){
		Assert.assertNotNull(gateway);
	}
	
	@Test
	public void testNotAllowedCommand(){
		String result = gateway.send("del");
		Assert.assertEquals("Command not allowed", result);
		
		result = gateway.send("delee * *");
		Assert.assertEquals("Command not allowed", result);
		
		result = gateway.send("");
		Assert.assertEquals("Incorrect command", result);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testNullCommand(){
		gateway.send(null);
	}
	
	@Test
	public void testPwd() {
		String result = gateway.send("pwd");
		Assert.assertTrue(StringUtils.isNotEmpty(result));
		result = gateway.send("pwd");
	}
	
	@Test
	public void testLs() {
		String result = gateway.send("ls");
		File path = new File(contextPath);
		Assert.assertEquals(path.exists(), result != null);
		Assert.assertEquals(path.list().length > 0, StringUtils.isNotEmpty(result));
	}
	
	@Test
	public void testCd() {
		String result = gateway.send("cd dummy/folder");
		Assert.assertTrue(StringUtils.isNotEmpty(result));
	}
}
