package handlerservice;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import commons.Commons;
import commons.Httpurl_Connection;



@Component
public class MliDoc_Handler_Service 
{
	private static Logger logger = LogManager.getLogger(MliDoc_Handler_Service.class);
	public static ResourceBundle res = ResourceBundle.getBundle("errorMessages");
	public Map<String, String> getMliDocService(String policyNo) 
	{
		logger.info("Came Inside" + "getMliDocService :: Method :: STARTS");
		String mliDoc = "MLIDOC";
		HashMap<String, String> returnMap = new HashMap();
		Httpurl_Connection connection = new Httpurl_Connection();
		String result = connection.httpConnection_response(policyNo, mliDoc);
		logger.info("Result Get From HttpUrlConnection :- "+ result.toString());
		try
		{
			if(!result.equalsIgnoreCase("InvalidResponse"))
			{
				Map resultData = Commons.getGsonData(result);
				logger.info("Result Data :-"+ resultData );
				String responseDescription = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("dispatchDocumentsResponse")).get("responseDescription").toString();
				if (responseDescription.startsWith("Failure")) {
					returnMap.put("Message", res.getString("mliDocServiceFailure"));
				} else {
					returnMap.put("Message", res.getString("mliDocServiceSuccess"));
				}
			} 
			else
			{
				returnMap.put("Message", res.getString("mliDocServiceFailure"));
			}
			logger.info("Came OutSide" + "getMliDocService :: Method :: END");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnMap;
	}

}
