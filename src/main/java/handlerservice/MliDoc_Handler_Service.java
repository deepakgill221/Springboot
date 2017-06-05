package handlerservice;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import common.Commons;
import common.CustomizeDate;
import common.Httpurl_Connection;

@Component
public class MliDoc_Handler_Service 
{
		private static Logger logger = LogManager.getLogger(MliDoc_Handler_Service.class);
	public static ResourceBundle res = ResourceBundle.getBundle("errorMessages");
	public Map<String, String> getMliDocService(String policyNo, String cashlastPremPmtDt) 
	{
		logger.info("Came Inside" + "getMliDocService :: Method :: STARTS");
	        String finaldate="";
		try{
		CustomizeDate custDate = new CustomizeDate();
		finaldate=custDate.DateFormat(cashlastPremPmtDt);
		}catch(Exception e){logger.info(e);}
		String mliDoc = "MLIDOC";
		HashMap<String, String> returnMap = new HashMap();
		Httpurl_Connection connection = new Httpurl_Connection();
		String result = connection.httpConnection_response(policyNo, mliDoc, finaldate);
		logger.info("Result Get From HttpUrlConnection :- "+ result.toString());
		try
		{
			Map resultData = Commons.getGsonData(result);
			String soaStatusCode = ((Map) ((Map) resultData.get("response")).get("responseData"))
					.get("soaStatusCode").toString();
			if (soaStatusCode != null && !"".equalsIgnoreCase(soaStatusCode) && soaStatusCode.equalsIgnoreCase("200"))
			{
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
