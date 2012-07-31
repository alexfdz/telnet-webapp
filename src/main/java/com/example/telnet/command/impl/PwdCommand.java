package com.example.telnet.command.impl;

import java.io.IOException;

import org.springframework.integration.Message;

import com.example.telnet.command.Command;

/**
 * @author alexfdz
 *
 */
public class PwdCommand extends Command{

	@Override
	public Message<String> run(String contextPath, String[] args) throws IOException{
		String result = getContextFile(contextPath).getAbsolutePath();
		logger.info("PWD command on " + contextPath);
		return buildMessage(contextPath, result);
	}

}