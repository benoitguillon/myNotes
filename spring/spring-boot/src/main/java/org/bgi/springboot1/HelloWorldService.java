package org.bgi.springboot1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

	@Value(value="${helloWorldValue}")
	private String helloWorldString;
	
	
	public HelloWorldMessage computeHelloWorldMessage(){
		HelloWorldMessage message = new HelloWorldMessage();
		message.setTimestamp(System.currentTimeMillis());
		message.setUserMessage(this.helloWorldString);
		return message;
	}
	
}
