package com.example.telnet.service;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Headers;
import org.springframework.integration.annotation.Payload;

import com.example.telnet.command.Command;

/**
 * Service to handle the command requests from the outbound gateway
 * @author alexfdz
 */
public class TelnetService {
	
	protected static final Logger logger = Logger.getLogger(TelnetService.class);

	/**
	 * Map of allowed {@link Command} implementations to
	 * be executed
	 */
	private Map<String, Command> commands;
	
	/**
	 * Session context path key in the {@link Message} header
	 */
	@Value("${contextPath.key}")
	protected String contextPathKey;
	
	/**
	 * Message command execution
	 * @param headers {@link Message} headers
	 * @param input {@link Message} playload
	 * @return
	 * @throws IOException
	 */
	public Message<String> command(@Headers Map<String, Object> headers, @Payload String input) throws IOException{
		
		if(headers == null || !headers.containsKey(contextPathKey)){
			throw new RuntimeException("Invalid message");
		}
		
		if(StringUtils.isEmpty(input)){
			throw new RuntimeException("Incorrect command");
		}
		
		//Resolve command and arguments
		String[] inputs = input.split(" "); 
		String command = inputs[0];
		
		if(!commands.containsKey(command)){
			throw new RuntimeException("Command not allowed");
		}
		
		if(inputs.length > 1){
			inputs = (String[])ArrayUtils.remove(inputs, 0);
		}else{
			inputs = null;
		}
		
		String contextPath = (String)headers.get(contextPathKey);
		
		return commands.get(command).run(contextPath, inputs);
	}
	
	/**
	 * @param commands the commands to set
	 */
	public void setCommands(Map<String, Command> commands) {
		this.commands = commands;
	}
	
}
