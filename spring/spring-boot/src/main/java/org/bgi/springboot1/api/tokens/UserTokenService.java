package org.bgi.springboot1.api.tokens;


public interface UserTokenService {
	
	public String createUserToken(final UserTokenInfo userInfo) throws Exception ;
	
	public UserTokenInfo extractUserToken(final String token) throws Exception;

}
