package com.qc.api.service;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import com.qc.common.Commons;
import com.qc.common.Httpurl_Connection;

@Component
public class PolicyPack_Handler_Service 
{
	private static Logger logger = LogManager.getLogger(MliDoc_Handler_Service.class);
	public static ResourceBundle res = ResourceBundle.getBundle("errorMessages");
	public Map<String, String> getPolicyPackService (String policyNo)
	{
		logger.info("Came Inside" + "getPolicyPackService :: Method :: STARTS");
		String finaldate="";
		String mliDoc = "PolicyPack";
		HashMap<String, String> returnMap = new HashMap();
		Httpurl_Connection connection = new Httpurl_Connection();
		String result = connection.httpConnection_response(policyNo, mliDoc, finaldate);
		System.out.println("Result Get From HttpUrlConnection :- "+ result.toString());
		try
		{
			Map resultData = Commons.getGsonData(result);
			String soaStatusCode = ((Map) ((Map) resultData.get("response")).get("responseData"))
					.get("statusCode").toString();
			if (soaStatusCode != null && !"".equalsIgnoreCase(soaStatusCode) && soaStatusCode.equalsIgnoreCase("200"))
			{
				logger.info("Result Data :-"+ resultData );
				String statusMessage = ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("statusMessage").toString();
				if (statusMessage.startsWith("Failure")) {
					returnMap.put("Message", res.getString("PolicyPackServiceFailure"));
				} else {
					returnMap.put("Message", res.getString("PolicyPackServiceSuccess"));
				}
			} 
			else
			{
				returnMap.put("Message", res.getString("PolicyPackServiceFailure"));
			}
			logger.info("Came OutSide" + "getPolicyPackService :: Method :: END");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}
}


