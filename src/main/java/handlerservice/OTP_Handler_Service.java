package handlerservice;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cacheservice.OTPService;
import common.Commons;


@Component
public class OTP_Handler_Service 
{
	private static Logger logger = LogManager.getLogger(OTP_Handler_Service.class);
	public static ResourceBundle resProp = ResourceBundle.getBundle("errorMessages");

	@Autowired
	OTPService otpService;

	public Map<String, String> getPolicyOtp(String policyNo, String sessionId, int counter) 
	{
		logger.info("CameInside getPolicyOtp :: Method :: START");
		String result="";
		String identity="OTP";
		try
		{
			logger.info("START :: Calling OTPCallCashing Method");
			result = otpService.OTPCallCashing(policyNo, sessionId, identity);
			logger.info("END :: OTPCallCashing Method :: Response :: "+ result);
		}catch(Exception e)
		{
			logger.info("Error Occoured while calling External webservice"+e);
		}
		Map<String, String> otpDescMap = new HashMap<String, String>();
		String policyOtp = "";
		String proposerName = "";
		try
		{
			if(!result.equalsIgnoreCase("InvalidResponse"))
			{
				logger.info("CameInside :: ! InvalidResponse");
				Map resultData = Commons.getGsonData(result);
				logger.info("ResultData :- "+resultData.toString());
				String soaStatusCode = ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("soaStatusCode").toString();
				if (soaStatusCode != null && !soaStatusCode.equalsIgnoreCase("") && soaStatusCode.equalsIgnoreCase("200")) 
				{
					try {
						policyOtp = ((Map) ((Map) resultData.get("response")).get("responseData")).get("otp")
								.toString();
						proposerName = ((Map) ((Map) resultData.get("response")).get("responseData"))
								.get("proposerName").toString();
						System.out.println("proposerName :" + proposerName);
					} catch (Exception ec) {
						logger.info("unable to get required data" + ec.getMessage());
					}
					otpDescMap.put("policyotp", policyOtp);
					otpDescMap.put("proposerName", proposerName);
					if (counter == 0)
					{
						otpDescMap.put("Message", resProp.getString("getOtpSuccessfully"));
						otpDescMap.put("PolicyNo", policyNo);
						otpDescMap.put("ValidOTP", "");
					}
					else
					{
						otpDescMap.put("Message", resProp.getString("getOtpRegenSuccessfully"));
					}
				}
				else if (soaStatusCode != null && !soaStatusCode.equalsIgnoreCase("") && soaStatusCode.equalsIgnoreCase("999")) 
				{
					String soaMessage = ((Map) ((Map) resultData.get("response")).get("responseData")).get("soaMessage")
							.toString();
					if ("Unable to fetch client Id from Policy Info backend service.".equals(soaMessage)) {
						otpDescMap.put("Message",
								resProp.getString("PolicyNumberNotFound") + policyNo + " " + resProp.getString("PolicyNumberNotFound1"));
					} else if ("Unable to fetch Mobile number from Client Info backend service.".equals(soaMessage)) {
						otpDescMap.put("Message", resProp.getString("MobileNumberRegardingPolicy"));
					}
					// Set message if required
					System.out.println("soaStatusCode is : " + soaStatusCode);
				} 
				else 
				{
					System.out.println("soaStatusCode is : " + soaStatusCode);
				}
			}

			else {
				otpDescMap.put("Message", "Unable to fetch the data form Soa Services response code ! = 200");
			}
		} catch (Exception e) {
			System.out.println("We are in exception while calling API : " + e);
		}
		logger.info("OutSide getPolicyOtp :: Method :: End :"+ otpDescMap);
		return otpDescMap;
	}

}
