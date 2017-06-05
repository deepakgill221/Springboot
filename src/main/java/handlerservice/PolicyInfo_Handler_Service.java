package handlerservice;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import common.Commons;
import common.Httpurl_Connection;


@Component
public class PolicyInfo_Handler_Service 
{
	public static ResourceBundle res = ResourceBundle.getBundle("errorMessages");
	private static Logger logger = LogManager.getLogger(PolicyInfo_Handler_Service.class);
	public Map getPolicyInfo(String policyNo)
	{
		logger.info("CameInside Method :: getPolicyInfo :: START ");
		Map<String, String> map = new HashMap();
		Map<String, Map> returnMap = new HashMap<String, Map>();
		try
		{
			Httpurl_Connection connection = new Httpurl_Connection();
			String PolicyInfo = "PolicyInfo";
                  	String finaldate="";
			String result = connection.httpConnection_response(policyNo, PolicyInfo, finaldate);
			logger.info("PolicyInfo API Response From Backend ::  "+ result.toString());
			
			Map resultData = Commons.getGsonData(result);
			String soaStatusCode = ((Map) ((Map) resultData.get("response")).get("responseData"))
					.get("soaStatusCode").toString();
			if (soaStatusCode != null && !"".equalsIgnoreCase(soaStatusCode) && soaStatusCode.equalsIgnoreCase("200"))
			{
				String policyBasePlanIdDesc = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("policyBasePlanIdDesc").toString();
				String ctpAmt = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("ctpAmt").toString();
				String polDueDate = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("polDueDate").toString();

				String polStatusCode = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("policyStatusCd").toString();

				String polmodprem = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("PolicyMeasures")).get("polModPrem").toString();

				String polStatusDesc = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("policyStatusDesc").toString();
 				String lastPremPmtDt = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("lastPremPmtDt").toString();

				Map<String, String> myPolicyData = new HashMap();

				myPolicyData.put("polStatusCode", polStatusCode);
				myPolicyData.put("polStatusDesc",polStatusDesc);
				myPolicyData.put("policyBasePlanIdDesc", policyBasePlanIdDesc);
				myPolicyData.put("lastPremPmtDt", lastPremPmtDt);

				returnMap.put("PolicyData", myPolicyData);

				polDueDate=Commons.convertDateFormat(polDueDate);
				map.put("policyBasePlanIdDesc", policyBasePlanIdDesc);
				map.put("ctpAmt", ctpAmt);
				map.put("polDueDate", polDueDate);

				if("".equalsIgnoreCase(ctpAmt) || "".equalsIgnoreCase(polDueDate)) {
					map.put("Message", res.getString("CTP_CON6_1") + " " + policyNo + " " + res.getString("CTP_CON6_2") + " " + polStatusDesc + " " + res.getString("CTP_CON6_3") );
					returnMap.put("ErrorMessage", map);
				}
				String policyInsuranceTypeCd = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("BasicDetails")).get("policyInsuranceTypeCd").toString();

				if ("N".equals(policyInsuranceTypeCd) || "F".equals(policyInsuranceTypeCd)
						|| "D".equals(policyInsuranceTypeCd) || "C".equals(policyInsuranceTypeCd)) {
					Map<String, String> fvMap = new HashMap();
					fvMap.put("fundValAsonDate",
							((Map) ((Map) ((Map) resultData.get("response")).get("responseData")).get("PolicyMeasures"))
							.get("fundValAsonDate").toString());
					fvMap.put("discontinuanceFund",
							((Map) ((Map) ((Map) resultData.get("response")).get("responseData")).get("BasicDetails"))
							.get("discontinuanceFund").toString());

					if (!("1".equals(polStatusCode) || "2".equals(polStatusCode) || "3".equals(polStatusCode) || "4".equals(polStatusCode) || "5".equals(polStatusCode) || "1A".equals(polStatusCode))) 
						fvMap.put("FVErrorMessage", res.getString("FV_CON1_1") + " " + policyNo + " " + res.getString("FV_CON1_2") + " " + polStatusDesc + " " + res.getString("FV_CON1_3"));
					else
						fvMap.put("Message", res.getString("InquiringFVTrue"));

					returnMap.put("FV", fvMap);
				} else if ("8".equals(policyInsuranceTypeCd) || "1".equals(policyInsuranceTypeCd)) {
					Map<String, String> fvMap = new HashMap<String, String>();
					fvMap.put("Message", res.getString("InquiringFVfalse"));
					returnMap.put("FV", fvMap);
				} else {
					Map<String, String> fvMap = new HashMap<String, String>();
					fvMap.put("Message", res.getString("InquiringFVfalse"));
					returnMap.put("FV", fvMap);
				}

				if ("8".equals(policyInsuranceTypeCd) || "1".equals(policyInsuranceTypeCd)) {
					System.out.println("CSV not Applicable");
						Map<String, String> csv = new HashMap<String, String>();
					csv.put("Message", res.getString("cashSurrenderNotApplicable"));
					returnMap.put("CSV", csv);
				}
				try {
					if (Double.parseDouble(ctpAmt) == 0) {
						if(Commons.dateDiff(polDueDate)<0){
							Map<String, String> fvMap = new HashMap<String, String>();
							fvMap.put("Message", res.getString("CTP_CON1_1")+" "+
									polmodprem   +" "+ res.getString("CTP_CON1_2")+" "+
									polDueDate +" "+ res.getString("CTP_CON1_3")+"\n"+res.getString("CTP_CON1_4"));
							returnMap.put("CTP", fvMap);
						} else {
							Map<String, String> fvMap = new HashMap<String, String>();
							fvMap.put("Message", res.getString("CTP_CON6_1") + " " + policyNo + " " + res.getString("CTP_CON6_2") + " " + polStatusDesc + " " + res.getString("CTP_CON6_3") );
							returnMap.put("CTP", fvMap);
						}
					}else if(Commons.dateDiff(polDueDate)<=30){
						Map<String, String> fvMap = new HashMap<String, String>();
						fvMap.put("Message", res.getString("CTP_CON2_1")+" "+polDueDate+" "+res.getString("CTP_CON2_2")+" "
								+ ctpAmt +" "+res.getString("CTP_CON2_3"));
						returnMap.put("CTP", fvMap);
					}else if(Commons.dateDiff(polDueDate)>30 && Commons.dateDiff(polDueDate)<=180){
						Map<String, String> fvMap = new HashMap<String, String>();
						fvMap.put("Message", res.getString("CTP_CON3_1")+" "+ctpAmt+" "+res.getString("CTP_CON3_2")+" "
								+ polDueDate + res.getString("CTP_CON3_3"));
						returnMap.put("CTP", fvMap);
					}else if(Commons.dateDiff(polDueDate)>180 && Commons.dateDiff(polDueDate)<1095){
						Map<String, String> fvMap = new HashMap<String, String>();
						fvMap.put("Message", res.getString("CTP_CON4_1")+" "+ctpAmt+" "+res.getString("CTP_CON4_2")+" "
								+ polDueDate + res.getString("CTP_CON4_3"));
						returnMap.put("CTP", fvMap);
					}else if(Commons.dateDiff(polDueDate)>1095){
						Map<String, String> fvMap = new HashMap<String, String>();
						fvMap.put("Message", res.getString("CTP_CON5_1"));
						returnMap.put("CTP", fvMap);
					}
					else {
						Map<String, String> fvMap = new HashMap<String, String>();

						fvMap.put("Message", res.getString("dueAmountPolicy1") + " " + policyNo + " "
								+ res.getString("dueAmountPolicy2") + " " + ctpAmt + " "
								+ res.getString("dueAmountPolicy3") + " " + polDueDate);
						fvMap.put("ctpAmt", ctpAmt);
						fvMap.put("polDueDate", polDueDate);
						returnMap.put("CTP", fvMap);
					}

				} catch (Exception ec) {
					logger.info(ec);
				}
			} else {

				Map<String, String> fvMap = new HashMap<String, String>();
				fvMap.put("Message", "Getting error : ! 200  while calling backend service");
				
				returnMap.put("ErrorMessage", fvMap);
			}
		}
		catch(Exception e) 
		{
			logger.info("We are in exception while calling API : "+e);
		}
		return returnMap;
	}
}
