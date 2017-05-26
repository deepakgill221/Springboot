package handlerservice;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Component;

import common.Commons;
import common.Httpurl_Connection;



@Component
public class PolicyDetail_Handler_Service 
{
	ResourceBundle res = ResourceBundle.getBundle("errorMessages");
	public Map<String, String> getPolicyDetails(Map<String, Map> mapData, String policyNumber) {

		Map<String, String> returnMap = new HashMap<String, String>();
		String policyDetail ="PolicyDetail";
		Httpurl_Connection connection = new Httpurl_Connection();
		String result=connection.httpConnection_response(policyNumber, policyDetail);
		String mir_dv_pua_csh_valu = "";
		String mir_dv_eff_dt = "";
		String mir_dv_pol_csv_amt = "";
		try {
			
				Map resultData = Commons.getGsonData(result);
				mir_dv_pua_csh_valu = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("cashSurrenderValue")).get("MIR-DV-PUA-CSH-VALU").toString();
				mir_dv_eff_dt = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("cashSurrenderValue")).get("MIR-DV-EFF-DT").toString();
				mir_dv_pol_csv_amt = ((Map) ((Map) ((Map) resultData.get("response")).get("responseData"))
						.get("cashSurrenderValue")).get("MIR-DV-POL-CSV-AMT").toString();

				if ((mir_dv_pua_csh_valu == null) || "".equals(mir_dv_pua_csh_valu))
					mir_dv_pua_csh_valu = "0.00";

				if ((mir_dv_pol_csv_amt == null) || "".equals(mir_dv_pol_csv_amt))
					mir_dv_pol_csv_amt = "0.00";
				Map m = (Map)mapData.get("PolicyData");

				String polStatusCode = (String)m.get("polStatusCode");
				String polStatusDesc = (String)m.get("polStatusDesc");
				System.out.println("Policy Status " + polStatusCode);
				System.out.println("Policy Desc  " + polStatusDesc);

				if (!("1".equals(polStatusCode) || "2".equals(polStatusCode) || "3".equals(polStatusCode) || "4".equals(polStatusCode) || "5".equals(polStatusCode) || "1A".equals(polStatusCode))) 
					returnMap.put("Message", res.getString("CSV_CON1_1") + " " + policyNumber + " " + res.getString("CSV_CON1_2") + " " + polStatusDesc + " " + res.getString("CSV_CON1_3"));
				else {

					System.out.println("Adding message");
					returnMap.put("Message",
							res.getString("surrendervalue1") + " " + policyNumber + " "
									+ res.getString("surrendervalue2") + " " + Commons.convertDateFormat(mir_dv_eff_dt) + " "
									+ res.getString("surrendervalue3") + Double.parseDouble(mir_dv_pol_csv_amt) + ".");
					returnMap.put("mir_dv_pua_csh_valu", mir_dv_pua_csh_valu);
					System.out.println("Message added");
				}
		} catch (Exception ex) {
			System.out.println("Exception Occured"+ ex);
		}
		return returnMap;
	}
}
