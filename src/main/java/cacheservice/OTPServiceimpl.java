package cacheservice;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import commons.Httpurl_Connection;



@Component
public class OTPServiceimpl implements OTPService
{
	private static Logger logger = LogManager.getLogger(OTPServiceimpl.class);
	@Cacheable(value="chatbot", key="#sessionId")
	public String OTPCallCashing(String policyNo,String sessionId, String identifier) throws SQLException , Exception
	{
		logger.info("CameInside Method :: OTPCallCashing ");
		String finalresult="";
		Httpurl_Connection connection = new Httpurl_Connection();
		return  finalresult = connection.httpConnection_response(policyNo, identifier);
	}
}
