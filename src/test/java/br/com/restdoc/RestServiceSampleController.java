package br.com.restdoc;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Resource;

@Resource
public class RestServiceSampleController {

	public RestServiceSampleController() {
	}

	/**
	 * Find service orders
	 * 
	 * @param soId
	 *            service order ID
	 * @param businessUnit
	 * @param subscription
	 * @param sigla
	 * @param protocol
	 * @param status
	 * @return service order list on json format
	 * @returnExample {result: [{ "idos": 1 , "businessUnit": 1 ,
	 *                "subscription": 1 , "sigla": 1 , "protocol": 1 , "status":
	 *                1 , "description": 1 , "serviceTypeDesc": 1 ,
	 *                "osInformation": 1 }] }
	 * @throws DataException
	 */

	@Get("/serviceOrders")
	public List<String> find(String soId, String businessUnit,
			String subscription, String sigla, String protocol, String status)
			throws Exception {

		List<String> list = new ArrayList<String>();
		list.add(businessUnit);

		return list;
	}
}
