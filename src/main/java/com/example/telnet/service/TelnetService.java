package com.example.telnet.service;

import java.io.IOException;

import org.springframework.integration.annotation.Payload;

/**
 * @author alexfdz
 *
 */
public class TelnetService {
	
	public String command(@Payload String input) throws IOException{
		
		return "echo: " + input;
	}
}
