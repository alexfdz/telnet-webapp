package com.example.telnet.connection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.Message;
import org.springframework.integration.ip.tcp.connection.AbstractTcpConnectionInterceptor;
import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * {@link TcpConnection} wrapper to control session attributes.
 * @see AbstractTcpConnectionInterceptor
 * @author alexfdz
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContextConnectionInterceptor extends AbstractTcpConnectionInterceptor {
	
	/**
	 * Default session context path
	 */
	@Value("${contextPath.default}")
	protected String defaultContextPath;
	
	/**
	 * Session context path key in the {@link Message} header
	 */
	@Value("${contextPath.key}")
	protected String contextPathKey;
	
	/**
	 * Session context path
	 */
	protected String contextPath;
	

	@Override
	public boolean onMessage(Message<?> message) {
		message = MessageBuilder.fromMessage(message).setHeader(contextPathKey, 
				getContextPath()).build();
		return super.onMessage(message);
	}
	

	@Override
	public void send(Message<?> message) throws Exception {
		String newContextPath = (String)message.getHeaders().get(contextPathKey);
		if(newContextPath != null){
			//If is null an exception has been throwed, keep the same value
			setContextPath(newContextPath);
		}
		super.send(message);
	}
	
	/**
	 * @param contextPath the contextPath to set
	 */
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}


	/**
	 * @return the contextPath or the default context path
	 * if it is null
	 */
	public String getContextPath() {
		if(StringUtils.isEmpty(contextPath)){
			contextPath = defaultContextPath;
		}
		return contextPath;
	}
}
