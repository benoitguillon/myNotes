package org.bgi.springboot1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/hello")
public class HelloWorldRestController {
	
	@Autowired(required=true)
	private HelloWorldService helloWorldService;
	
	@RequestMapping(value="/", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	public HelloWorldMessage sayHello() {
		return this.helloWorldService.computeHelloWorldMessage();
	}

}
