package org.bgi.springboot1.api.tokens;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserTokenServiceImpl implements UserTokenService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserTokenServiceImpl.class);
	
	private static final String MAC_ALGORITHM = "HmacSHA256";
	
	private static final char TOKEN_INTERNAL_SEPARATOR = '.';
	
	private byte[] secretKey = new byte[0];
	
	@PostConstruct
	public void initializeSecretKey() throws Exception{
		LOG.info("Initializing UserTokenFactoryImpl");
		this.secretKey = generateSecretKey();
	}
	
	public String createUserToken(UserTokenInfo userInfo) throws Exception {
		final StringBuilder builder = new StringBuilder();
		byte[] content = toJson(userInfo);
		builder.append(toBase64(content));
		builder.append(TOKEN_INTERNAL_SEPARATOR);
		builder.append(toBase64(computeHash(content)));
		return builder.toString();
	}
	
	public UserTokenInfo extractUserToken(String token) throws Exception {
		int separatorIndex = token.indexOf(TOKEN_INTERNAL_SEPARATOR);
		if(separatorIndex == -1){
			throw new IllegalArgumentException("Invalid user token as separator not found");
		}
		String firstPart = token.substring(0, separatorIndex);
		String secondPart = token.substring(separatorIndex + 1);
		byte[] firstPartData = fromBase64(firstPart);
		byte[] secondPartData = fromBase64(secondPart);
		checkHash(firstPartData, secondPartData);
		return fromJson(firstPartData);
	}
	
	protected byte[] toJson(UserTokenInfo userInfo) throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		mapper.writeValue(output, userInfo);
		return output.toByteArray();
	}
	
	protected void checkHash(byte[] referenceData, byte[] hash) throws Exception {
		byte[] referenceHash = computeHash(referenceData);
		if(!Arrays.equals(hash, referenceHash)){
			throw new IllegalArgumentException("Hash cannot be verified");
		}
	}
	
	protected UserTokenInfo fromJson(byte[] data) throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(data, UserTokenInfo.class);
	}
	
	protected String toBase64(byte[] data){
		return Base64Utils.encodeToString(data);
	}
	
	protected byte[] fromBase64(String str){
		return Base64Utils.decode(str.getBytes());
	}
	
	protected byte[] computeHash(byte[] content) throws Exception {
		final SecretKeySpec secretKeySpec = new SecretKeySpec(this.secretKey, MAC_ALGORITHM);
		final Mac mac = Mac.getInstance(MAC_ALGORITHM);
		mac.init(secretKeySpec);
		return mac.doFinal(content);
	}
	
	protected byte[] generateSecretKey() throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance(MAC_ALGORITHM);
		return keyGen.generateKey().getEncoded();
	}

	
}
