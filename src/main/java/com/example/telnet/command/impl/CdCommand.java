package com.example.telnet.command.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.integration.Message;

import com.example.telnet.command.Command;

/**
 * @author alexfdz
 *
 */
public class CdCommand extends Command{

	@Override
	public Message<String>  run(String contextPath, String[] args) throws IOException{
		if(args == null || args.length != 1){
			throw new IllegalArgumentException("Invalid arguments, should be only one argument, the destination.");
		}
		File contextFile = getContextFile(contextPath,  args[0]);
		logger.info("CD command on " + contextFile.getAbsolutePath());
		return buildMessage(contextFile.getPath());
	}
}
