package config;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import commons.Commons;
import handlerservice.MliDoc_Handler_Service;
import handlerservice.OTP_Handler_Service;
import handlerservice.PolicyDetail_Handler_Service;
import handlerservice.PolicyInfo_Handler_Service;




@Controller
@RequestMapping("/webhook")
public class ChatBotController {

	private static Logger logger = LogManager.getLogger(ChatBotController.class);
	public static ResourceBundle resProp = ResourceBundle.getBundle("errorMessages");
	public static Map<String, String> serviceResp = new HashMap<String,String>();
	private static final String POL_DATA = "PolData";
	@Autowired
	OTP_Handler_Service oTP_Handler_Service; 
	@Autowired
	PolicyDetail_Handler_Service policyDetail_Handler_Service; 
	@Autowired
	PolicyInfo_Handler_Service policyInfo_Handler_Service; 
	@Autowired
	MliDoc_Handler_Service mliDoc_Handler_Service;

	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody WebhookResponse webhook(@RequestBody String obj, Model model, HttpSession httpSession) {
		String speech = null;
		try {
			System.out.println(obj);
			JSONObject object = new JSONObject(obj.toString());
			String sessionId = object.get("sessionId")+"";
			logger.info("Request Session Id :- "+ sessionId);
			String action = object.getJSONObject("result").get("action")+"";
			logger.info("Request Action :- "+ action);
			String policy_Number = object.getJSONObject("result").getJSONObject("parameters").getJSONObject("PolicyNumber").get("Given-PolicyNumber")+"";
			logger.info("Request PolicyNo :- "+policy_Number );

			if ("PolicyNumberValidation".equals(action))
			{
				logger.info("CameInsdie Action :: PolicyNumberValidation");
				if (serviceResp.get("PolicyNo") != null && (serviceResp.get("ValidOTP") != null && serviceResp.get("ValidOTP") != "" ))
				{
					logger.info("Inside :: PolicyNo & OTP Validation From ehcache");
					String policyNumberInSession = serviceResp.get("PolicyNo") ;
					System.out.println("Policy number in session :- " + policyNumberInSession);
					System.out.println("Current PolicyNo :-  " + policy_Number);

					if (policyNumberInSession != null) 
					{
						logger.info("Inside :: PolicyNumberInSession Check !=null");
						if (!(policy_Number.equals(policyNumberInSession)))
						{
							System.out.println("A new policy number entered by the customer " + policy_Number);
							logger.info("A new policy number entered by the customer " + policy_Number);
						}
					}
				}
				if (serviceResp.get("PolicyNo") != null && (serviceResp.get("ValidOTP") != null && serviceResp.get("ValidOTP") != "" ))
				{
					logger.info("Inside 2 Check :: PolicyNo & OTP Validation From ehcache");
					speech = "OTP Verification is completed for Policy Number " + serviceResp.get("PolicyNo")
					+ ", please tell what you want to know about policy";
					logger.info("Message Sent to Api :-"+ speech);
				}
				else if (serviceResp.get("PolicyNo") != null) 
				{
					String otp_session = "";
					String cache_otp = serviceResp.get("policyotp");
					if (cache_otp != null)
					{
						otp_session = serviceResp.get("policyotp");
					}
					if (otp_session != null)
					{
						if (otp_session.equals(policy_Number)) {
							speech = "Hi " + Commons.toCamelCase(serviceResp.get("proposerName").toString())
							+ resProp.getString("welcomeUser");
							Map data = policyInfo_Handler_Service.getPolicyInfo(serviceResp.get("PolicyNo").toString());
							System.out.println("data----------" + data.toString());
							serviceResp.put("ValidOTP", serviceResp.get("PolicyNo"));
							serviceResp.remove("policyotp");
							
						} else {
							speech = resProp.getString("OTPnotmatch");
						}
					} else {
						speech = resProp.getString("GenerateOTP");
					}
				}
				else
				{
					logger.info("START :: Request For Generate PolicyOTP");
					serviceResp = oTP_Handler_Service.getPolicyOtp(policy_Number, sessionId , 0);
					logger.info("END :: Request For Generate PolicyOTP :- "+serviceResp);
					speech = serviceResp.get("Message");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(speech);
		WebhookResponse responseObj = new WebhookResponse(speech, speech);
		return responseObj;
	}

}


