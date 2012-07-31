package com.example.telnet.command.impl;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.integration.Message;

import com.example.telnet.command.Command;

/**
 * @author alexfdz
 *
 */
public class MkdirCommand extends Command{

	@Override
	public Message<String> run(String contextPath, String[] args) throws IOException{
		
		if(args == null || args.length != 1){
			throw new IllegalArgumentException("Invalid arguments, should be only one argument, the destination.");
		}
		File contextFile = getFile(contextPath, args[0]);
		logger.info("MKDIR command on " + contextFile.getAbsolutePath());
		FileUtils.forceMkdir(contextFile);
		
		return buildMessage(contextPath);
	}

	/**
	 * Resolve the server {@link File} entity for a given context path and the 
	 * relative path requested
	 * @param contextPath
	 * @param path
	 * @return
	 * @throws IOException
	 */
	protected File getFile(String contextPath, String path) throws IOException{
		if(StringUtils.isNotEmpty(path) && new File(path).isAbsolute()){
			return new File(path);
		}
		return new File(contextPath + "/" + path);
	}
}
