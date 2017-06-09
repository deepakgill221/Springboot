package common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cacheservice.OTPServiceimpl;

public class Httpurl_Connection 
{
	private static Logger logger = LogManager.getLogger(Httpurl_Connection.class);
	public String httpConnection_response(String policyNo, String methodidentifier, String finaldate)
	{
		logger.info("Inside Method:: httpConnection_response ");
		StringBuilder result = new StringBuilder();
		String output = new String();
		ResourceBundle res = ResourceBundle.getBundle("errorMessages");
		String soaCorrelationId = "CorelationId"+System.currentTimeMillis();
		HttpURLConnection conn = null;
		String soaMsgVersion=""; 
		String soaAppID=""; 
		String soaUserID=""; 
		String soaUserPswd="";
		String applicationurl=""; 
		String docID = "PRM23";	
		String policyPackType="03";
		String SendTo = "C"; 
		String docDispatchMode = "E";
		String fromDate1="04/01/2016";
		String toDate1="03/31/2017";

		if("OTP".equalsIgnoreCase(methodidentifier))
		{
			logger.info("Method Identifier :-  " +methodidentifier );
			soaMsgVersion=res.getString("soaMsgVersion");
			soaAppID=res.getString("otpsoaAppID");
			soaUserID=res.getString("otpsoaUserID");
			soaUserPswd=res.getString("otpsoaUserPswd");
			applicationurl=res.getString("Soa_url_OTP_Prod");

		}else if("PolicyInfo".equalsIgnoreCase(methodidentifier))
		{
			logger.info("Method Identifier :-  " +methodidentifier );
			soaMsgVersion=res.getString("soaMsgVersion");
			soaAppID=res.getString("soaAppID");
			soaUserID=res.getString("soaUserIDProd");
			soaUserPswd=res.getString("soaUserPasswordProd");
			applicationurl=res.getString("Soa_url_policy360");
		}else if("PolicyDetail".equalsIgnoreCase(methodidentifier))
		{
			logger.info("Method Identifier :-  " +methodidentifier );
			soaMsgVersion=res.getString("soaMsgVersion");
			soaAppID=res.getString("soaAppID");
			soaUserID=res.getString("soaUserIDProd");
			soaUserPswd=res.getString("soaUserPasswordProd");
			applicationurl=res.getString("Soa_url_cushsurrender");
		}
		else if("Maturity".equalsIgnoreCase(methodidentifier))
		{
			logger.info("Method Identifier :-  " +methodidentifier );
			soaMsgVersion=res.getString("soaMsgVersion");
			soaAppID=res.getString("soaAppID");
			soaUserID=res.getString("soaUserID");
			soaUserPswd=res.getString("soaUserPswd");
			applicationurl=res.getString("Soa_url_policymaturity");
		}
		else if("PolicyPack".equalsIgnoreCase(methodidentifier))
		{
			logger.info("Method Identifier :-  " +methodidentifier );
			soaMsgVersion=res.getString("soaMsgVersion");
			soaAppID=res.getString("soaAppID");
			soaUserID=res.getString("soaUserIDProd");
			soaUserPswd=res.getString("soaUserPasswordProd");
			applicationurl=res.getString("Soa_url_policypack");
		}
		else
		{
			logger.info("Method Identifier :-  " +methodidentifier );
			soaMsgVersion=res.getString("soaMsgVersion");
			soaAppID=res.getString("soaAppID");
			soaUserID=res.getString("soaUserIDProd");
			soaUserPswd=res.getString("soaUserPasswordProd");
			applicationurl=res.getString("Soa_url_mlidocservice");
			String[] outputdate=finaldate.split("till");
			fromDate1=outputdate[0];
			toDate1=outputdate[1];	
		}
		try {
			XTrustProvider trustProvider = new XTrustProvider();
			trustProvider.install();
			URL url = new URL(applicationurl);
			conn = (HttpURLConnection) url.openConnection();
			HttpsURLConnection.setFollowRedirects(true);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			StringBuilder requestdata = new StringBuilder();
			if(!"MLIDOC".equalsIgnoreCase(methodidentifier) && !"PolicyPack".equalsIgnoreCase(methodidentifier))
			{
				requestdata.append(" 	{	 ");
				requestdata.append(" 	   \"request\": {	 ");
				requestdata.append(" 	      \"header\": {	 ");
				requestdata.append(" 	         \"soaCorrelationId\": \"").append(soaCorrelationId).append("\",	 ");
				requestdata.append(" 	         \"soaMsgVersion\": \"").append(soaMsgVersion).append("\",	 ");
				requestdata.append(" 	         \"soaAppId\": \"").append(soaAppID).append("\",	 ");
				requestdata.append(" 	         \"soaUserId\": \"").append(soaUserID).append("\",	 ");
				requestdata.append(" 	         \"soaPassword\": \"").append(soaUserPswd).append("\"	 ");
				requestdata.append(" 	      },	 ");
				requestdata.append(" 	      \"requestData\": {	 ");
				requestdata.append(" 	         \"policyNumber\": \"").append(policyNo).append("\"	 ");
				requestdata.append(" 	      }	 ");
				requestdata.append(" 	   }	 ");
				requestdata.append(" 	}	 ");
				System.out.println("Request Data For Hitting API : - "+requestdata.toString());
			}
			else if("PolicyPack".equalsIgnoreCase(methodidentifier))
			{
				requestdata.append(" 	{	 ");
				requestdata.append(" 	   \"request\": {	 ");
				requestdata.append(" 	      \"header\": {	 ");
				requestdata.append(" 	         \"soaCorrelationId\": \"").append(soaCorrelationId).append("\",	 ");
				requestdata.append(" 	         \"soaMsgVersion\": \"").append(soaMsgVersion).append("\",	 ");
				requestdata.append(" 	         \"soaAppId\": \"").append(soaAppID).append("\",	 ");
				requestdata.append(" 	         \"soaUserId\": \"").append(soaUserID).append("\",	 ");
				requestdata.append(" 	         \"soaPassword\": \"").append(soaUserPswd).append("\"	 ");
				requestdata.append(" 	      },	 ");
				requestdata.append(" 	      \"requestData\": {	 ");
				requestdata.append(" 	         \"policyId\": \"").append(policyNo).append("\", ");
				requestdata.append(" 	         \"type\": \"").append(policyPackType).append("\"	 ");
				requestdata.append(" 	      }	 ");
				requestdata.append(" 	   }	 ");
				requestdata.append(" 	}	 ");
				System.out.println("Request Data For Hitting API : - "+requestdata.toString());
			}
			else
			{
				requestdata.append("{\"request\":{\"header\":{\"soaCorrelationId\":\"");
				requestdata.append(soaCorrelationId);
				requestdata.append("\",\"soaMsgVersion\":\"1.0\",\"soaAppId\":\"");
				requestdata.append(soaAppID);
				requestdata.append("\",\"soaUserId\":\"");
				requestdata.append(soaUserID);
				requestdata.append("\",\"soaPassword\":\"");
				requestdata.append(soaUserPswd);
				requestdata.append("\"},\"requestData\":{\"dispatchDocuments\":{\"policyNumber\":\"");
				requestdata.append(policyNo);
				requestdata.append("\",\"docId\":\"");
				requestdata.append(docID);
				requestdata.append("\",\"sendTo\":\"");
				requestdata.append(SendTo);
				requestdata.append("\",\"emailIdC\":\"\",\"emailIdA\":\"\",\"docDispatchMode\":\"");
				requestdata.append(docDispatchMode);
				requestdata.append("\",\"fromDate\":\"");
				requestdata.append(fromDate1);
				requestdata.append("\",\"toDate\":\"");
				requestdata.append(toDate1);
				requestdata.append(
						"\",\"fromYear\":\"\",\"toYear\":\"\",\"source\":\"\",\"machineIP\":\"\",\"uniqueTransId\":\"\",\"userId\":\"\",\"requestedBy\":\"\"}}}}");
				System.out.println("Request Data For Hitting API : - "+requestdata.toString());

			}
			System.out.println("External API Call : START");
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(requestdata.toString());
			writer.flush();
			try {
				writer.close();
			} catch (Exception e1) {
			}

			int apiResponseCode = conn.getResponseCode();
			logger.info("API Response Code : - " + apiResponseCode);
			if (apiResponseCode == 200) 
			{
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
				while ((output = br.readLine()) != null) {
					result.append(output);
				}
				conn.disconnect();
				br.close();
				System.out.println("External API Call : END");
			}
			else
			{
				BufferedReader br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
				while ((output = br.readLine()) != null) {
					result.append(output);
				}
				conn.disconnect();
				br.close();
				Map<String, Object> resultData = Commons.getGsonData(result.toString());
				String soaStatusCode = ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("soaStatusCode").toString();
				if (soaStatusCode != null && !soaStatusCode.equalsIgnoreCase("") && soaStatusCode.equalsIgnoreCase("999")) 
				{
					return result.toString();
				}
				else
				{
					return  res.getString("GenericBackendErrorMessage");
				}
			}
		}
		catch(Exception e)
		{
			logger.info("Exception Occoured While Calling API's " + e);
		}
		logger.info("OutSide Method:: httpConnection_response ");
		return result.toString();
	}
}
