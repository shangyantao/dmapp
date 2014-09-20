package com.sap.data.app.ws;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sap.data.app.datasource.CompanyContextHolder;
import com.sap.data.core.mapper.JsonMapper;
import com.sap.data.core.rest.RsResponse;
import com.sap.data.db.dao.BapiFUPARAREFDao;
import com.sap.data.db.dao.HibernateUtil;
import com.sap.data.db.dao.StructureDao;
import com.sap.data.db.pojo.BapiFUPARAREFPojo;
import com.sap.data.db.util.NotFoundException;
import com.sap.data.ws.WsConstants;

@Component
@Path("/data")
public class DataResourceService {

	@GET
	@Path("/search")
	@Produces({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_XML + WsConstants.CHARSET })
	public String searchData(@QueryParam("companyId") String companyId,
			@QueryParam("userId") String userId,
			@QueryParam("eventType") String eventType,
			@QueryParam("tableName") String tableName,
			@QueryParam("firstResult") int firstResult,
			@QueryParam("maxResults") int maxResults) {
		/** change dataSource begin **/
		if (StringUtils.isNotBlank(companyId)) {
			CompanyContextHolder.setCompanyType(companyId);
		} else {
			String message = "company id isn't null";
			throw RsResponse.buildException(Status.BAD_REQUEST, message);
		}
		/** change dataSource end **/
		try {
			HashMap<String, Object> dataMap = new HashMap<>();
			JsonMapper jsonMapper = new JsonMapper();
			if ("E0000".equalsIgnoreCase(eventType)) {
				
				String message = "E0000 is unavailble now";
				throw RsResponse.buildException(Status.BAD_REQUEST, message);
				
			}else if ("E0015".equalsIgnoreCase(eventType)) {
				
				if(StringUtils.isBlank(tableName)){
					String message = "tableName can't be null";
					throw RsResponse.buildException(Status.BAD_REQUEST, message);
				}
				
				try {
					HibernateUtil.incarnate(eventType.toUpperCase()
							+ "_" + tableName,"NO_FIRST_LOAD");
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				StructureDao sDao = new StructureDao(eventType.toUpperCase()
						+ "_" + tableName);
				dataMap.put(tableName,
						sDao.selectE0015(firstResult, maxResults));
				
				
			} else {
				
				List<BapiFUPARAREFPojo> bplist = new BapiFUPARAREFDao()
				.selectParameterStructuresByFuncName(eventType);
				System.out.println(bplist.size());
				for (BapiFUPARAREFPojo bapiParameterPojo : bplist) {
					try {
						HibernateUtil.incarnate(eventType.toUpperCase()
								+ "_" + bapiParameterPojo.getId().getPARAMETER(),"NO_FIRST_LOAD");
					} catch (NotFoundException e) {
						e.printStackTrace();
					}
					
					StructureDao sDao = new StructureDao(
							eventType.toUpperCase() + "_"
									+ bapiParameterPojo.getId().getPARAMETER());
					if (!bapiParameterPojo.getId().getPARAMETER()
							.contains("RETURN")) {
						dataMap.put(bapiParameterPojo.getId().getPARAMETER(),
								sDao.selectByJCO_USER(userId, firstResult,
										maxResults));
					}
				}
				
			}

			
			return jsonMapper.toJson(dataMap);
		} catch (RuntimeException e) {
			throw RsResponse.buildDefaultException(e);
		} catch (NotFoundException e) {
			throw RsResponse.buildException(Status.INTERNAL_SERVER_ERROR, e.getMessage());
		}
	}

}
