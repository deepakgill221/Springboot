package handlerservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import common.Commons;
import common.CustomizeDate;
import common.Httpurl_Connection;

@Component
public class MaturityDate 
{
	public static ResourceBundle res = ResourceBundle.getBundle("errorMessages");
	private static Logger logger = LogManager.getLogger(MaturityDate.class);
	public Map getMaturityDate(String policyNo)
	{
		logger.info("CameInside Method :: getPolicyInfo :: START ");
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> returnMap = new HashMap<String, String>();
		try
		{
			Httpurl_Connection connection = new Httpurl_Connection();
			String PolicyInfo = "Maturity";
			String finaldate ="";
			String result = connection.httpConnection_response(policyNo, PolicyInfo, finaldate);
			logger.info("PolicyInfo API Response From Backend ::  "+ result.toString());

			Map resultData = Commons.getGsonData(result);
			String soaStatusCode = ((Map) ((Map) resultData.get("response")).get("responseData"))
					.get("soaStatusCode").toString();
			if (soaStatusCode != null && !"".equalsIgnoreCase(soaStatusCode) && soaStatusCode.equalsIgnoreCase("200"))
			{
				String cvgIssEffDt="";
				String cvgMatXpryDt="";
				CustomizeDate cusDate = new CustomizeDate();
				String policyStatusCd = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("policyStatusCd").toString();
				String policyStatusDesc = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("policyStatusDesc").toString();
				String policyTerm = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("policyTerm").toString();
				List cvgDetails = (List)((Map)((Map) resultData.get("response")).get("responseData")).get("coverageDetailsArray");
				if(cvgDetails!=null && !cvgDetails.isEmpty())
				{
					for(int i=0; i<cvgDetails.size(); i++)
					{
						String cvgNum =((Map) cvgDetails.get(i)).get("cvgNum")+"";
						if("01".equals(cvgNum))
						{
							cvgIssEffDt =((Map) cvgDetails.get(i)).get("cvgIssEffDt")+"";
							cvgMatXpryDt =((Map) cvgDetails.get(i)).get("cvgMatXpryDt")+"";
						}
					}
				}
				if("1".equalsIgnoreCase(policyStatusCd) || "2".equalsIgnoreCase(policyStatusCd) || "3".equalsIgnoreCase(policyStatusCd)
						|| "4".equalsIgnoreCase(policyStatusCd) || "1A".equalsIgnoreCase(policyStatusCd))
				{
					if("0".equalsIgnoreCase(policyTerm))
					{
						String message=res.getString("maturity4 ")+" "+policyNo+" "+
								res.getString("maturity5")+" "+policyTerm+" "+res.getString("maturity6");
						returnMap.put("maturityMessage", message);
					}
					else
					{
						CustomizeDate cd = new CustomizeDate();
						int year=cd.getYear(cvgIssEffDt, cvgMatXpryDt);
						String finalYear=String.valueOf(year);
						String message=res.getString("maturity7")+" "+policyNo+" "
								+res.getString("maturity8")+" "+Commons.convertDateFormat(cvgMatXpryDt)+" "+res.getString("maturity11") 
								+" "+finalYear+" "+res.getString("maturity9");
						returnMap.put("maturityMessage", message);
					}
				}else 
				{
					String message1=res.getString("maturity1 ")+" "+policyNo+" "+res.getString(" maturity2")
					+" "+policyStatusDesc+" "+res.getString("maturity3");
					returnMap.put("maturityMessage", message1);
				}
			}else 
			{
				returnMap.put("Message", "Getting error : ! 200  while calling backend service");
			}
			returnMap.put("MaturityCashData", result);
		}
		catch(Exception e) 
		{
			logger.info("We are in exception while calling API : "+e);
		}
		return returnMap;
	}

}
