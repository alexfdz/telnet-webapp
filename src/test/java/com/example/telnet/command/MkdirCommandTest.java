package com.example.telnet.command;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.telnet.command.impl.MkdirCommand;

/**
 * @author alexfdz
 *
 */
@ContextConfiguration(locations={"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class MkdirCommandTest{
	
	@Autowired
	private MkdirCommand command;
	
	private String contextPath = getClass().getResource("/").getPath();
	
	@Test
	public void testCommand() throws IOException{
		Assert.assertNotNull(command);
		command.run(contextPath, new String[]{"testfolder"});
		command.run(contextPath, new String[]{"testfolder/test1"});
		command.run(contextPath, new String[]{"com"});
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
		command.run(contextPath, new String[]{"/root/test"});
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException()throws IOException{
		command.run(contextPath, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalArgumentException2()throws IOException{
		command.run(contextPath, new String[]{});
	}
}
