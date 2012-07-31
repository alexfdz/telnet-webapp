package com.example.telnet.command;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.telnet.command.impl.LsCommand;

/**
 * @author alexfdz
 *
 */
@ContextConfiguration(locations={"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class LsCommandTest{

	@Autowired
	private LsCommand command;
	
	private String contextPath = getClass().getResource("/").getPath();
	
	@Test
	public void testCommand() throws IOException{
		Assert.assertNotNull(command);
		Message<String> message = command.run(contextPath);
		Assert.assertNotNull(message);
		Assert.assertTrue(StringUtils.isNotEmpty(message.getPayload()));
	}
	
	@Test(expected=IOException.class)
	public void testIOExceptionRoot()throws IOException{
		File rootFolder = new File("/root");
		if(!rootFolder.exists()){
			throw new IOException("File doesn't exist");
		}
		if(rootFolder.canRead()){
			throw new IOException("You're running tests as root!!!");
		}
		command.run(rootFolder.getPath());
	}
	
	@Test(expected=IOException.class)
	public void testIOException()throws IOException{
		command.run("/dummy/folder");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException()throws IOException{
		command.run(null);
	}
}
