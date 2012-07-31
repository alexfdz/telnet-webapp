package com.example.telnet.command.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.integration.Message;

import com.example.telnet.command.Command;

/**
 * @author alexfdz
 *
 */
public class LsCommand extends Command{

	@Override
	public Message<String>  run(String contextPath, String[] args) throws IOException{
		File contextFile = getContextFile(contextPath);
		logger.info("LS command on " + contextFile.getAbsolutePath());

		String[] list = contextFile.list();
		String result =  StringUtils.join(list, "\r\n\t");
		
		return buildMessage(contextPath, result);
	}

}
