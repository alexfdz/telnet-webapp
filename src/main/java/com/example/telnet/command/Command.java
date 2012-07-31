package com.example.telnet.command;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.Message;
import org.springframework.integration.support.MessageBuilder;

import com.example.telnet.connection.ContextConnectionInterceptor;


/**
 * Abstract command entity to implement real telnet commands.
 * The execution of this command needs the connection context path.
 * 
 * @author alexfdz
 */
public abstract class Command {
	
	protected static final Logger logger = Logger.getLogger(Command.class);
	
	@Value("${contextPath.key}")
	private String contextPathkey;
	
	/**
	 * Command execution
	 * @param contextPath Session context path
	 * @return {@link Message} with the result in the playload
	 * @throws IOException
	 */
	public Message<String> run(String contextPath) throws IOException{
		return run(contextPath, null);
	}
	
	/**
	 * Command execution
	 * @param contextPath Session context path
	 * @param args Command arguments
	 * @return {@link Message} with the result in the playload
	 * @throws IOException
	 */
	public abstract Message<String> run(String contextPath, String[] args) throws IOException;
	
	
	/**
	 * Resolve the server {@link File} entity for a given context path
	 * @param contextPath
	 * @return
	 * @throws IOException
	 */
	protected File getContextFile(String contextPath) throws IOException{
		if(StringUtils.isEmpty(contextPath)){
			throw new IllegalArgumentException("Context path must not be null");
		}
		File contextFile = new File(contextPath);
		
		if(!contextFile.exists()){
			throw new IOException("File doesn't exist");
		}
		
		if(!contextFile.canRead()){
			throw new IOException("Insufficient permissions");
		}
		return contextFile;
	}
	
	/**
	 * Resolve the server {@link File} entity for a given context path and the 
	 * relative path requested
	 * @param contextPath
	 * @param path
	 * @return
	 * @throws IOException
	 */
	protected File getContextFile(String contextPath, String path) throws IOException{
		if(StringUtils.isNotEmpty(path) && new File(path).isAbsolute()){
			return getContextFile(path);
		}
		return getContextFile(contextPath + "/" + path);
	}
	
	/**
	 * Resolve the response {@link Message} with an empty playload and the new
	 * context path as a header, to be intercepted by the {@link ContextConnectionInterceptor}
	 * @param contextPath
	 * @return
	 */
	protected Message<String> buildMessage(String contextPath){
		return buildMessage(contextPath, null);
	}
	
	/**
	 * Resolve the response {@link Message} with the playload result and the new
	 * context path as a header, to be intercepted by the {@link ContextConnectionInterceptor}
	 * @param contextPath
	 * @param result
	 * @return
	 */
	protected Message<String> buildMessage(String contextPath, String result){
		if(result == null){
			result = StringUtils.EMPTY;
		}else{
			result = "\t" + result;
		}
		contextPath = FilenameUtils.normalize(contextPath);
		return MessageBuilder.withPayload(result).setHeader(contextPathkey, contextPath).build();
	}
}
