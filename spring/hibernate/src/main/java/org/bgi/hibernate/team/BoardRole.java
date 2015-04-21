package org.bgi.hibernate.team;

public enum BoardRole {

	PRESIDENT('P'),
	VICE_PRESIDENT('V'),
	SPORTING_DIRECTOR('S');
	
	private char code;
	
	private BoardRole(char code){
		this.code = code;
	}
	
	public char getCode(){
		return this.code;
	}
	
}
