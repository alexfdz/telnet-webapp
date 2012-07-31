package com.example.telnet.connection;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.ip.tcp.connection.TcpConnection;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptor;
import org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory;

/**
 * TCP connection interceptor factory implementation to wrap
 * the {@link TcpConnection} to add session management controls
 * @see TcpConnectionInterceptorFactory
 * @author alexfdz
 *
 */
public class ContextConnectionInterceptorFactory implements TcpConnectionInterceptorFactory {

	@Autowired 
	private BeanFactory beanFactory;
	
	/* (non-Javadoc)
	 * @see org.springframework.integration.ip.tcp.connection.TcpConnectionInterceptorFactory#getInterceptor()
	 */
	@Override
	public TcpConnectionInterceptor getInterceptor() {
		return beanFactory.getBean(ContextConnectionInterceptor.class);
	}
}
