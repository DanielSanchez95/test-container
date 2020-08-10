/**
 * 
 */
package com.assertsolutions.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.assertsolutions.dto.Request;

/**
 * @author Assert Solutions S.A.S.
 *
 */
@Component
public class CourseProcessor implements Processor {

	private List<Request> listUser = new ArrayList<Request>();

	
	@Override
	public void process(Exchange exchange) throws Exception {
		List<Request> list = new ArrayList<Request>();
		UUID uuid = UUID.randomUUID();

		
		
		switch (String.valueOf(exchange.getProperty("serviceRest"))) {
		case "create":
			list = listUser;
			Request req = (Request) exchange.getIn().getBody();
			req.setId(uuid.toString());
			list.add(req);
			listUser = list;
			list = new ArrayList<Request>();
			break;
		case "update":
			String documentUP = String.valueOf(exchange.getIn().getHeader("document"));

			List<Request> listUpdate = new ArrayList<>(listUser);
			int iu = 0;
			for (Request request : listUser) {
				if (request.getId().equals(documentUP)) {
					request = (Request) exchange.getIn().getBody();
					request.setId(documentUP);
					listUpdate.set(iu, request);
				}
				iu++;
			}
			list = listUpdate;
			listUser = list;
			list = new ArrayList<Request>();
			break;
		case "delete":
			String documentDE = String.valueOf(exchange.getIn().getHeader("document"));
			List<Request> listDelete = new ArrayList<>(listUser);
			int i = 0;
			for (Request request : listUser) {
				if (request.getId().equals(documentDE)) {
					listDelete.remove(i);
				}
				i++;
			}
			list = listDelete;
			listUser = list;
			list = new ArrayList<Request>();
			break;
		case "list":
			String documentLI = String.valueOf(exchange.getIn().getHeader("document"));
			for (Request request : listUser) {
				if (request.getId().equals(documentLI)) {
					list.add(request);
					break;
				}
			}
			break;
		case "listall":
			list = listUser;
			break;
		default:
			list = listUser;
			break;
		}
		exchange.getIn().setBody(list);
		exchange.getOut().setBody(list);

	}

}
