package com.example.telnet.command;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.telnet.command.impl.CdCommand;
import com.example.telnet.command.impl.LsCommand;
import com.example.telnet.command.impl.MkdirCommand;
import com.example.telnet.command.impl.PwdCommand;

/**
 * @author alexfdz
 *
 */
@ContextConfiguration(locations={"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class CommandsTest{

	@Value("${contextPath.key}")
	private String contextKey;
	
	@Autowired
	private CdCommand cdCommand;
	@Autowired
	private LsCommand lsCommand;
	@Autowired
	private MkdirCommand mkdirCommand;
	@Autowired
	private PwdCommand pwdCommand;
	
	private String contextPath = getClass().getResource("/").getPath();
	
	@Test
	public void testCommands() throws IOException{
		Message<String> message;
		message = pwdCommand.run(contextPath);
		Assert.assertTrue(StringUtils.isNotEmpty(message.getPayload()));
		message = mkdirCommand.run(contextPath, new String[]{"test/folder"});
		message = cdCommand.run(contextPath, new String[]{"test/folder/../folder/.."});
		Assert.assertTrue(((String)message.getHeaders().get(contextKey)).endsWith("test"));
		message = lsCommand.run(contextPath + "/test");
		Assert.assertTrue(message.getPayload().endsWith("folder"));
	}
	
}
