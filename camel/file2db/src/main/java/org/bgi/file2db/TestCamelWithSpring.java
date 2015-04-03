package org.bgi.file2db;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCamelWithSpring {
	
	public static void main(String[] args){
		ClassPathXmlApplicationContext ctx = null;
		try{
			ctx = new ClassPathXmlApplicationContext("data-loading-service-context.xml", "sample1.xml", "datasource-context.xml");
			ctx.start();
			Thread.currentThread().join();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally  {
			if(ctx != null){
				ctx.close();
			}
		}
		
	}

}
