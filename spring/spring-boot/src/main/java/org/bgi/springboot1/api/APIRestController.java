package org.bgi.springboot1.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api")
public class APIRestController {
	
	@Autowired(required=true)
	private HelloWorldService helloWorldService;
	
	@RequestMapping(value="/hello", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public HelloWorldMessage sayHello() throws Exception {
		return this.helloWorldService.computeHelloWorldMessage();
	}
	
	@RequestMapping(value="/admin", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public HelloWorldMessage adminSpaceHello() {
		return this.helloWorldService.computeHelloWorldMessageAdminSpace();
	}

}
