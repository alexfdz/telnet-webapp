package com.example.telnet.command.impl;

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
		return null;
	}
}
