package org.bgi.springboot1.api;

import org.bgi.springboot1.api.tokens.UserTokenService;
import org.bgi.springboot1.api.tokens.UserTokenInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {

	@Value(value="${helloWorldValue}")
	private String helloWorldString;
	
	@Autowired
	private UserTokenService userTokenFactory;
	
	public HelloWorldMessage computeHelloWorldMessage() throws Exception {
		HelloWorldMessage message = new HelloWorldMessage();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		message.setUserToken(userTokenFactory.createUserToken(new UserTokenInfo(auth.getName(), 35L)));
		message.setUserName(auth.getName());
		message.setTimestamp(System.currentTimeMillis());
		message.setUserMessage(this.helloWorldString);
		return message;
	}
	
	public HelloWorldMessage computeHelloWorldMessageAdminSpace(){
		HelloWorldMessage message = new HelloWorldMessage();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		message.setUserName(auth.getName());
		message.setTimestamp(System.currentTimeMillis());
		message.setUserMessage("You are in the admin space, yeah !!");
		return message;
	}
	
}
