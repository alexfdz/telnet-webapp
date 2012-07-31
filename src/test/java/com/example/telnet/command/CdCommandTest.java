package com.example.telnet.command;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.telnet.command.impl.CdCommand;

/**
 * @author alexfdz
 *
 */
@ContextConfiguration(locations={"/test-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
public class CdCommandTest{

	@Autowired
	private CdCommand command;
	
	@Test
	public void testCommand(){
		Assert.assertNotNull(command);
	}
}
