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

import common.Commons;
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

			switch(action)
			{

			case "OTP.NotAvailable":
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					String cachePolicyNo=responsecache_onsessionId.get(sessionId).get("PolicyNo")+"";
					String cacheOTP=responsecache_onsessionId.get(sessionId).get("policyotp")+"";
					String proposerName=responsecache_onsessionId.get(sessionId).get("proposerName")+"";
					Map<String, Object> serviceResp = responsecache_onsessionId.get(sessionId);
					serviceResp.remove("cachevalidOTP");
					responsecache_onsessionId.put(sessionId, serviceResp);
					Map<String, String> orignalData = oTP_Handler_Service.getPolicyOtp(policy_Number, sessionId , 0);
					speech = orignalData.get("Message");
					if (orignalData.get("policyotp") != null) {
						orignalData.put(cacheOTP, orignalData.get("policyotp"));
						orignalData.put(proposerName, orignalData.get("proposerName"));
						orignalData.put(cachePolicyNo, cachePolicyNo);
						responsecache_onsessionId.put(sessionId, orignalData);
						System.out.println("OTP is **** " + serviceResp.get("policyotp"));
					}
				}
				else{
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
			}
			break;
			case "PolicyNumberValidation":
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					String cachePolicyNo=responsecache_onsessionId.get(sessionId).get("PolicyNo")+"";
					String cachevalidOTP=responsecache_onsessionId.get(sessionId).get("ValidOTP")+"";
					String cacheOTP=responsecache_onsessionId.get(sessionId).get("policyotp")+"";
					String proposerName=responsecache_onsessionId.get(sessionId).get("proposerName")+"";

					logger.info("CameInsdie Action :: PolicyNumberValidation");
					if (cachePolicyNo!= null && (cachevalidOTP != null && !cachevalidOTP.equalsIgnoreCase("")))
					{
						logger.info("Inside :: PolicyNumberInSession Check !=null");
						if (!(policy_Number.equals(cachePolicyNo)))
						{
							System.out.println("A new policy number entered by the customer " + policy_Number);
							logger.info("A new policy number entered by the customer " + policy_Number);
						}
					}
					if (cachePolicyNo!= null && (cachevalidOTP != null && !cachevalidOTP.equalsIgnoreCase("")))
					{
						logger.info("Inside 2 Check :: PolicyNo & OTP Validation From ehcache");
						speech = "OTP Verification is completed for Policy Number " + cachePolicyNo
								+ ", please tell what you want to know about policy";
						logger.info("Message Sent to Api :-"+ speech);
					}
					else if (cachePolicyNo!= null) 
					{
						String otp_session="";
						if (!"".equalsIgnoreCase(cacheOTP) && cacheOTP != null)
						{
							otp_session = cacheOTP;
						}
						if (!"".equalsIgnoreCase(otp_session) &&  otp_session != null)
						{
							policy_Number="123456";
							if (otp_session.equals(policy_Number)) {
								speech = "Hi " + Commons.toCamelCase(proposerName)
								+ resProp.getString("welcomeUser");
								Map data = policyInfo_Handler_Service.getPolicyInfo(cachePolicyNo);
								System.out.println("data----------" + data.toString());
								Map<String, Object> serviceResp = responsecache_onsessionId.get(sessionId);
								serviceResp.put("ValidOTP", cachePolicyNo);
								serviceResp.put("PolData", data);
								serviceResp.remove("policyotp");
								responsecache_onsessionId.put(sessionId, serviceResp);
							} else {
								speech = resProp.getString("OTPnotmatch");
							}
						} 
						else
						{
							speech = resProp.getString("GenerateOTP");
						}
					}
				}
				else
				{
					logger.info("START :: Request For Generate PolicyOTP");
					Map<String, String> orignalData = oTP_Handler_Service.getPolicyOtp(policy_Number, sessionId , 0);
					responsecache_onsessionId.put(sessionId, orignalData);
					logger.info("END :: Request For Generate PolicyOTP :- "+serviceResp);
					speech = orignalData.get("Message");
				}
			}
			break;
			case "OTPValidation":
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					String cachePolicyNo=responsecache_onsessionId.get(sessionId).get("PolicyNo")+"";
					String cachevalidOTP=responsecache_onsessionId.get(sessionId).get("ValidOTP")+"";
					String cacheOTP=responsecache_onsessionId.get(sessionId).get("policyotp")+"";
					if ("".equalsIgnoreCase(cachePolicyNo)||cachePolicyNo == null)
					{
						speech = resProp.getString("validPolicyMessage");
					}
					else if (!"".equalsIgnoreCase(cachevalidOTP) && cachevalidOTP!= null) 
					{
						speech = "OTP Verification is completed for Policy Number "+ cachePolicyNo
								+ ", please tell what you want to know about policy  OR write reset to start new session";
					} 
					else
					{
						String otp_session = "";
						String OTP_request=object.getJSONObject("result").getJSONObject("parameters").getJSONObject("OTP").get("Provided-OTP")+"";
						//						String requiredadata = object.getJSONObject("result").getJSONArray("contexts").getJSONObject(0)
						//								.getJSONObject("parameters").getJSONObject("PolicyNumber").get("Given-PolicyNumber.original") + "";
						otp_session = cacheOTP;
						if (!"".equalsIgnoreCase(otp_session) && otp_session != null) {
							if (otp_session.equals(OTP_request)) 
							{
								speech = "Mr. Arun. What information you want to know about your policy";
								Map data = policyInfo_Handler_Service.getPolicyInfo(cachePolicyNo);
								System.out.println("data----------" + data.toString());

								Map<String, String> serviceResp = responsecache_onsessionId.get(sessionId);
								serviceResp.put("cachevalidOTP", OTP_request);
								serviceResp.remove("policyotp");
								responsecache_onsessionId.put(sessionId, serviceResp);
							} 
							else {
								speech = "OTP did not match.Please provide correct OTP.";
							}
						} else
						{
							speech = "You have not generated OTP.Please provide valid policy to generate OTP";
						}
					}
				} 
				else{
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
			}
			break;
			case "input.CTP":
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					String cachePolicyNo=responsecache_onsessionId.get(sessionId).get("PolicyNo")+"";
					String cachevalidOTP=responsecache_onsessionId.get(sessionId).get("ValidOTP")+"";
					Map data =(Map)responsecache_onsessionId.get(sessionId).get("PolData");
					if ("".equalsIgnoreCase(cachePolicyNo) || cachePolicyNo==null )
					{
						speech = resProp.getString("validPolicyMessage");
					} else if ("".equalsIgnoreCase(cachevalidOTP) || cachevalidOTP== null) 
					{
						speech = resProp.getString("validateOTP").concat(cachePolicyNo);

					} else {
						System.out.println("i am in ctp action 1");
						if (data.get("CTP") == null) 
						{
							speech = ((Map) data.get("ErrorMessage")).get("Message").toString();
						} else 
						{
							System.out.println("i am in ctp action 3");
							Map ctp = (Map) data.get("CTP");
							speech = ctp.get("Message").toString();
						}
					}
				}
				else{
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
			}
			break;
			case "input.Surrender": 
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					String cachePolicyNo=responsecache_onsessionId.get(sessionId).get("PolicyNo")+"";
					String cachevalidOTP=responsecache_onsessionId.get(sessionId).get("ValidOTP")+"";
					Map data =(Map)responsecache_onsessionId.get(sessionId).get("PolData");
					if ("".equalsIgnoreCase(cachePolicyNo)||cachePolicyNo == null)
					{
						speech = resProp.getString("validPolicyMessage");
					} 
					else if ("".equalsIgnoreCase(cachevalidOTP)||cachevalidOTP== null)
					{
						speech = resProp.getString("validateOTP").concat(cachePolicyNo);
					} 
					else 
					{
						System.out.println("I am in Surrender Value");
						if (data.get("CSV") == null) {
							System.out.println("CSV is null is policy data");
							speech = policyDetail_Handler_Service.getPolicyDetails(data, cachePolicyNo).get("Message");
							System.out.println("Speech is " + speech);
						}
						else{
							speech = ((Map)data.get("CSV")).get("Message").toString();
						}
					}
				}
				else{
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
			}
			break;
			case "input.Receipt":
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					String cachePolicyNo=responsecache_onsessionId.get(sessionId).get("PolicyNo")+"";
					String cachevalidOTP=responsecache_onsessionId.get(sessionId).get("ValidOTP")+"";
					Map data =(Map)responsecache_onsessionId.get(sessionId).get("PolData");

					if ("".equalsIgnoreCase(cachePolicyNo)|| cachePolicyNo == null) {
						speech = resProp.getString("validPolicyMessage");
					} else if ("".equalsIgnoreCase(cachevalidOTP) || cachevalidOTP == null) {
						speech = resProp.getString("validateOTP").concat(cachePolicyNo);
					} else {
						speech = mliDoc_Handler_Service.getMliDocService(cachePolicyNo).get("Message");
					}
				}
				else{
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
			}
			break;
			case "input.Fund": 
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					String cachePolicyNo=responsecache_onsessionId.get(sessionId).get("PolicyNo")+"";
					String cachevalidOTP=responsecache_onsessionId.get(sessionId).get("ValidOTP")+"";
					Map data =(Map)responsecache_onsessionId.get(sessionId).get("PolData");
					if ("".equalsIgnoreCase(cachePolicyNo) || cachePolicyNo == null) {
						speech = resProp.getString("validPolicyMessage");
					} else if ("".equalsIgnoreCase(cachevalidOTP) || cachevalidOTP == null) {
						speech = resProp.getString("validateOTP").concat(cachePolicyNo);
					} else {
						if (data.get("FV") == null) {
							speech = ((Map) data.get("ErrorMessage")).get("Message").toString();
						} else {
							Map fv = (Map) data.get("FV");
							if (fv.get("fundValAsonDate") != null) {

								if (fv.get("FVErrorMessage") != null)
									speech = fv.get("FVErrorMessage").toString();
								else {
									String fvdata = fv.get("fundValAsonDate").toString();
									speech = fv.get("Message").toString() + Math.round(Double.parseDouble(fvdata)*100.0)/100.0;
								}
							} else {
								speech = fv.get("Message").toString();
							}
						}
					}
				} 
				else{
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
			}
			break;
			case "close.conversation":
			{
				if(responsecache_onsessionId.containsKey(sessionId))
				{
					responsecache_onsessionId.clear();
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
				else{
					speech = "Thank you for contacting Max Life. Have a great day!";
				}
			}
			default : 
				speech = "Thank you for contacting Max Life. Have a great day!";
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(speech);
		WebhookResponse responseObj = new WebhookResponse(speech, speech);
		return responseObj;
	}

}


