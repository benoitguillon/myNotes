package org.bgi.file2db;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestCamelWithSpring {
	
	public static void main(String[] args){
		try{
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("sample1.xml");
			ctx.start();
			Thread.currentThread().join();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
