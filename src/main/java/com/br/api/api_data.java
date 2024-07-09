package com.br.api;

import java.io.File;
import java.io.InputStream;
import java.text.BreakIterator;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.tools.picocli.CommandLine.Parameters;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.br.auth.JwtManager;
import com.br.data.DeleteData;
import com.br.data.InsertData;
import com.br.data.SelectData;
import com.br.data.UpdateData;
import com.br.utility.ConvertStringtoObject;
import com.br.utility.FileUtillity;
import com.br.utility.HttpConnection;
import com.br.utility.SendEmail;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.SignatureException;

@Path("/data")
public class api_data {

	protected static final Logger logger = LogManager.getLogger(api_data.class);

	@GET
	@Path("/company")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getCompany(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/company");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");

		try {
			return Response.ok(SelectData.getCompany(), MediaType.APPLICATION_JSON + ";charset=utf8").build();

		} catch (Exception e) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", e);
			logger.error(e.getMessage());
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/staffcode/{username}/{lastname}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getStaffcode(@Context HttpHeaders headers, @PathParam("username") String username,
			@PathParam("lastname") String lastname, String req) throws JSONException {
		logger.info("/staffcode");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");

		try {
			return Response
					.ok(SelectData.getstaffcode(username, lastname), MediaType.APPLICATION_JSON + ";charset=utf8")
					.build();

		} catch (Exception e) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", e);
			logger.error(e.getMessage());
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/bu")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getBU(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/bu");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				logger.debug("getUsername {} getUser {}", getUsername, getAuth);

				try {
					return Response.ok(SelectData.getBU(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/costcenterbu/{bu}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getCostcenterByBU(@Context HttpHeaders headers, @PathParam("bu") String bu, String req)
			throws JSONException {
		logger.info("/costcenterbu/{bu}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(SelectData.getCostCenterByBU(getCono, getDivi, bu),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/warehouse")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getWarehouse(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/warehouse");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response
							.ok(SelectData.getWarehouse(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/accountant")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getAccountant(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/accountant");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(SelectData.getAccountant(getCono, getDivi),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/approve")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getApprove(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/approve");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(SelectData.getApprove(), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/deptandcost")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getDeparmentAndCostcenter(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/deptandcost");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(SelectData.getDepartmentAndCostcenter(getCono, getDivi),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/getoperationdata")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getoperationdata(@Context HttpServletRequest httpServletRequest,@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/getoperationdata");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));
			
			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");
				
				String getLocation = getSubject[3];

				try {

					return Response.ok(SelectData.getoperationdata(httpServletRequest,getCono, getDivi,getLocation),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	
	
	
	@GET
	@Path("/getoperationfilterdata/{fromdate}/{todate}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getoperationfilterdata(@Context HttpServletRequest httpServletRequest, @PathParam("fromdate") String fromdate, @PathParam("todate") String todate,@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/getoperationdata");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));
			
			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");
				
				String getLocation = getSubject[3];

				try {

					return Response.ok(SelectData.getoperationfilterdata(httpServletRequest,getCono, getDivi,getLocation,fromdate,todate),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/monitoringreceipt")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMonitoringReceipt(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/deptandcost");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(SelectData.getMonitoringReceipt(getCono, getDivi),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/marnumber/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMARNumber(@Context HttpHeaders headers, @PathParam("fromstatus") String fromstatus,
			@PathParam("tostatus") String tostatus, String req) throws JSONException {
		logger.info("/marnumber/{fromstatus}/{tostatus}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(SelectData.getMARNumber(getCono, getDivi, fromstatus, tostatus, getUsername),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/item/{whs}/{type}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getItem(@Context HttpHeaders headers, @PathParam("whs") String whs, @PathParam("type") String type,
			String req) throws JSONException {
		logger.info("getItem");
		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(SelectData.getItem(getCono, getDivi, whs, type),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/itemdetail/{whs}/{item}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getItemDetail(@Context HttpHeaders headers, @PathParam("whs") String whs,
			@PathParam("item") String item, String req) throws JSONException {
		logger.info("getItemDetail");
		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(SelectData.getItemDetailV2(getCono, getDivi, whs, item),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/marhead/{marno}/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMARHead(@Context HttpHeaders headers, @PathParam("marno") String marno,
			@PathParam("fromstatus") String fromstatus, @PathParam("tostatus") String tostatus, String req)
			throws JSONException {
		logger.info("/marhead/{marno}/{fromstatus}/{tostatus}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(SelectData.getMARHead(getCono, getDivi, marno, fromstatus, tostatus),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/mardetail/{marno}/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMARDetail(@Context HttpHeaders headers, @PathParam("marno") String marno,
			@PathParam("fromstatus") String fromstatus, @PathParam("tostatus") String tostatus, String req)
			throws JSONException {
		logger.info("/marhead/{marno}/{fromstatus}/{tostatus}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(SelectData.getMARDetail(getCono, getDivi, marno, fromstatus, tostatus),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/marfile/{marno}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMARFile(@Context HttpHeaders headers, @PathParam("marno") String marno, String req)
			throws JSONException {
		logger.info("/marfile/{marno}");
		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(SelectData.getMARFile(getCono, getDivi, marno),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	// @GET
	// @Path("/file/{filename}/{token}")
	// @Produces("image/png, application/pdf, application/vnd.ms-excel,
	// application/msword")
	// public Response getFile(@Context HttpHeaders headers, @PathParam("filename")
	// String fileName,
	// @PathParam("token") String token, @Context HttpServletRequest
	// httpServletRequest) {
	// JSONObject mJsonObj = new JSONObject();
	// String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	// System.out.println("headers: " + headers);
	// System.out.println("getToken: " + getToken);
	//
	// return FileUtillity.getFileV3(httpServletRequest, fileName);
	//
	// }

	@GET
	@Path("/file/{filename}/{token}")
	@Produces("image/jpeg, image/png, application/pdf, application/vnd.ms-excel, application/msword")
	public Response getFile(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@PathParam("filename") String fileName, @PathParam("token") String token, String req) throws JSONException {
		logger.info("/file/{filename}/{token}");
		JSONObject mJsonObj = new JSONObject();
		String getToken = token;
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					// return Response.ok(SelectData.getMARFile(getCono, getDivi, marno),
					// MediaType.APPLICATION_JSON + ";charset=utf8").build();

					return FileUtillity.getFileV3(httpServletRequest, fileName);

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).build();

	}

	@POST
	@Path("/marhead")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addMARHead(@Context HttpHeaders headers, @FormDataParam("vMARNumber") String vMARNumber,
			@FormDataParam("vDate") String vDate, @FormDataParam("vPostDate") String vPostDate,
			@FormDataParam("vMonth") String vMonth, @FormDataParam("vType") String vType,
			@FormDataParam("vPrefix") String vPrefix, @FormDataParam("vBU") String vBU,
			@FormDataParam("vCostcenter") String vCostcenter, @FormDataParam("vAccountant") String vAccountant,
			@FormDataParam("vRequestor") String vRequestor, @FormDataParam("vPurpose") String vPurpose,
			@FormDataParam("vRemark") String vRemark, @FormDataParam("vPurpose") String purpose,
			@FormDataParam("vApprove1") String vApprove1, @FormDataParam("vApprove2") String vApprove2,
			@FormDataParam("vApprove3") String vApprove3, @FormDataParam("vApprove4") String vApprove4,
			@FormDataParam("vApprove5") String vApprove5, @FormDataParam("vAppICT") String vAppICT,
			@FormDataParam("vAppCIO") String vAppCIO, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vSubmit") String vSubmit) throws JSONException {
		logger.info("/marhead");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(InsertData.addMARHead(getCono, getDivi, vMARNumber, vDate, vPostDate, vMonth,
							vType, vPrefix, vBU, vCostcenter, vAccountant, vRequestor, vRemark, purpose, vApprove1,
							vApprove2, vApprove3, vApprove4, vApprove5, vAppICT, vAppCIO, vStatus, vSubmit,
							getUsername), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/mardetail")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addMARDetail(@Context HttpHeaders headers, @FormDataParam("vMARNumber") String vMARNumber,
			@FormDataParam("vPrefix") String vPrefix, @FormDataParam("vRefNumber") String vRefNumber,
			@FormDataParam("vTypeAdjust") String vTypeAdjust, @FormDataParam("vItemLine") String vItemLine,
			@FormDataParam("vItem") String vItem, @FormDataParam("vItemDesc1") String vItemDesc1,
			@FormDataParam("vFacility") String vFacility, @FormDataParam("vWarehouse") String vWarehouse,
			@FormDataParam("vLocation") String vLocation, @FormDataParam("vLotNo") String vLotNo,
			@FormDataParam("vDate") String vDate, @FormDataParam("vUnit") String vUnit,
			@FormDataParam("vQty") String vQty, @FormDataParam("vUnitPrice") String vUnitPrice,
			@FormDataParam("vAmount") String vAmount, @FormDataParam("vRemark1") String vRemark1,
			@FormDataParam("vRemark2") String vRemark2, @FormDataParam("vStatus") String vStatus) throws JSONException {
		logger.info("/mardetail");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(
							InsertData.addMARDetail(getCono, getDivi, vMARNumber, vPrefix, vRefNumber, vTypeAdjust,
									vItemLine, vItem, vItemDesc1, vFacility, vWarehouse, vLocation, vLotNo, vDate,
									vUnit, vQty, vUnitPrice, vAmount, vRemark1, vRemark2, vStatus, getUsername),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/addmonitoringreceipt")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addMonitoringReceipt(@Context HttpHeaders headers, @FormDataParam("rcno") String rcno)
			throws JSONException {
		logger.info("/addmonitoringreceipt");
		System.out.println("addmonitoringreceipt\n");
		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					// FileUtillity.writeToFileServerV2(httpServletRequest, vFile, vFileName,
					// vFilePath);
					// FileUtillity.deleteFileV4(httpServletRequest, vFilePath);
					return Response
							.ok(InsertData.addMonitoringReceipt(rcno), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/marfile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addMARFile(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vMARNumber") String vMARNumber, @FormDataParam("vPrefix") String vPrefix,
			@FormDataParam("vFile") InputStream vFile, @FormDataParam("vFileName") String vFileName,
			@FormDataParam("vFileType") String vFileType, @FormDataParam("vFileLine") String vFileLine,
			@FormDataParam("vRemark1") String vRemark1, @FormDataParam("vRemark2") String vRemark2)
			throws JSONException {
		logger.info("/marfile");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					// FileUtillity.writeToFileServerV2(httpServletRequest, vFile, vFileName,
					// vFilePath);
					// FileUtillity.deleteFileV4(httpServletRequest, vFilePath);
					return Response.ok(
							InsertData.addMARFile(httpServletRequest, getCono, getDivi, vPrefix, vMARNumber, vFile,
									vFileName, vFileType, vRemark1, vRemark2, getUsername),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

//////////////////////////////

	@PUT
	@Path("/updateroomcard")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateroomcard(@Context HttpHeaders headers, @FormDataParam("vID") String vID,
			@FormDataParam("vRoom") String vRoom, @FormDataParam("vCard") String vCard

	) throws JSONException {
		logger.info("/updateroomcard");
		System.out.print("updateroomcard");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");
				String getLocation = getSubject[3];


				try {

					return Response.ok(UpdateData.updateroomcard(vID, vRoom, vCard ,getLocation),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	
	@GET
	@Path("/getimage/{vID}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getimage(@Context HttpHeaders headers,@Context HttpServletRequest httpServletRequest,
		 @PathParam("vID") String vID,
			String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();

		try {

			try {

				return Response.ok(SelectData.getimage(httpServletRequest,vID),
						MediaType.APPLICATION_JSON + ";charset=utf8").build();

			} catch (Exception e) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", e);
				e.printStackTrace();
			}

		} catch (Exception e) {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", e);
			e.printStackTrace();
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	
	
	@PUT
	@Path("/updateemp")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateemp(@Context HttpHeaders headers, @FormDataParam("vID") String vID,@FormDataParam("vEmail") String vEmail,
			@FormDataParam("vEmployeedialog") String vEmployeedialog,	@FormDataParam("vRemark") String vRemark

	) throws JSONException {
		logger.info("/updateroomcard");
		System.out.print("updateroomcard");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");
				String getLocation = getSubject[3];


				try {

					return Response.ok(UpdateData.updateemp(vID,vEmployeedialog,vEmail,getLocation,vRemark),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	@PUT
	@Path("/updatefollower")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
//@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updatefollower(@Context HttpHeaders headers, @FormDataParam("vID") String vID,
			@FormDataParam("H_SURNAME") String H_SURNAME, @FormDataParam("oldH_SURNAME") String oldH_SURNAME,
			@FormDataParam("vCONO") String vCONO,
			@FormDataParam("vDIVI") String vDIVI,
			@FormDataParam("vLocaton") String vLocaton

	) throws JSONException {
		logger.info("/visitorheader");
		System.out.print("visitorheaderrrrrrrrrrrrrrrrrrr");

		JSONObject mJsonObj = new JSONObject();
	

			
			
				try {

					return Response.ok(UpdateData.updatefollower(vID, H_SURNAME, oldH_SURNAME , vCONO,vDIVI,vLocaton),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/checkout")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response checkout(@Context HttpHeaders headers, @FormDataParam("vID") String vID
			,@FormDataParam("vStatuscheck") String vStatuscheck

	) throws JSONException {
		logger.info("/visitorheader");
		System.out.print("visitorheaderrrrrrrrrrrrrrrrrrr00");
		String sts = "00";
		switch(vStatuscheck) {
		case "CHECKOUT":
			sts ="50";
			break;
		
		case "SUBMIT":
		sts ="10";
		break;
		case "APPROVE":
			sts ="30";
			break;
		case "CANCEL":
			sts ="99";
			break;
			
		case "REJECT":
			sts ="80";
			break;
			
		case "CHECKIN":
			sts ="10";
			break;
			
			default:
			sts ="00";
			break;
			
	}
			

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");
				String getLocation = getSubject[3];

				try {

					return Response.ok(UpdateData.checkout(vID,sts,getLocation), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	

	@PUT
	@Path("/checkout1")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response checkout(@Context HttpHeaders headers, @FormDataParam("vID") String vID
			,@FormDataParam("vStatuscheck") String vStatuscheck
			,@FormDataParam("vROOM") String room
			,@FormDataParam("vLocation") String location)
	throws JSONException {
		logger.info("/visitorheader");
		System.out.print("checkout111111111111111111111");
		String sts = "00";
		switch(vStatuscheck) {
		case "CHECKOUT":
			sts ="50";
			break;
		
		case "SUBMIT":
		sts ="10";
		break;
		case "APPROVE":
			sts ="30";
			break;
		case "CANCEL":
			sts ="99";
			break;
			
		case "REJECT":
			sts ="80";
			break;
			
		case "CHECKIN":
			sts ="10";
			break;
			
			default:
			sts ="00";
			break;
			
	}
			

		JSONObject mJsonObj = new JSONObject();

			
				try {

					return Response.ok(UpdateData.checkout1(vID,sts,location,room), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

		
	return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();
}





	@PUT
	@Path("/addvisitorheader")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addvisitorheader(@Context HttpHeaders headers, @FormDataParam("vID") String vID,
			@FormDataParam("vIMG") String vIMG, @FormDataParam("vTimein") String vTimein,
			@FormDataParam("vTimeout") String vTimeout, @FormDataParam("vLicense") String vLicense,
			@FormDataParam("vName") String vName, @FormDataParam("vSurname") String vSurname,
			@FormDataParam("vTel") String vTel, @FormDataParam("vReason") String vReason,
			@FormDataParam("vEmployee") String vEmployee, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vCono") String vCono, @FormDataParam("vCompany") String vCompany,
			@FormDataParam("vMeetdate") String vMeetdate, @FormDataParam("vMeettime") String vMeettime,
			@FormDataParam("vMail") String vMail,@FormDataParam("imagefile") InputStream imagefile,
			@FormDataParam("imagename") String imagename,@FormDataParam("vCONO") String vCONO,
			@FormDataParam("vDIVI") String vDIVI,@FormDataParam("vLocation") String vLocation
			
			

	) throws JSONException {
		logger.info("/visitorheader");
		System.out.print("visitorheaderrrrrrrrrrrrrrrrrrr");

	
		JSONObject mJsonObj = new JSONObject();

			


				try {

					return Response.ok(
							UpdateData.addvisitorheader(vID, vIMG, vTimein, vTimeout, vLicense, vName, vSurname, vTel,
									vReason, vEmployee, vStatus, vCono, vCompany, vMeetdate, vMeettime, vMail,imagefile,imagename, vCONO, vDIVI, vLocation ),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

		

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	
	@GET
	@Path("/showvisitor/{vID}/{location}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response showvisitor(@Context HttpHeaders headers, @PathParam("vID") String vID,@PathParam("location") String location,
			@Context HttpServletRequest httpServletRequest) throws JSONException {
		logger.info("/getemployee");

		JSONObject mJsonObj = new JSONObject();
		
			

				try {

					return Response.ok(
							SelectData.showvisitor(vID,location ),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

		

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}



///////////////////////////////	

	@PUT
	@Path("/updateswrfile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateMARHead(@Context HttpHeaders headers, @FormDataParam("oldSFORNO") String oldSFORNO,
			@FormDataParam("oldSFCONO") String oldSFCONO, @FormDataParam("oldSFDIVI") String oldSFDIVI,

			@FormDataParam("newSFORNO") String newSFORNO, @FormDataParam("newSFPREF") String newSFPREF,
			@FormDataParam("newSFLINE") String newSFLINE, @FormDataParam("newSFNAME") String newSFNAME,
			@FormDataParam("newSFTYPE") String newSFTYPE, @FormDataParam("newSFREM1") String newSFREM1,
			@FormDataParam("newSFREM2") String newSFREM2) throws JSONException {
		logger.info("/swrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
		System.out.print("swrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(
							UpdateData.updateSWRFile(oldSFORNO, oldSFCONO, oldSFDIVI, newSFORNO, newSFPREF, newSFLINE,
									newSFNAME, newSFTYPE, newSFREM1, newSFREM2, getUsername),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/marhead")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateMARHead(@Context HttpHeaders headers, @FormDataParam("vMARNumber") String vMARNumber,
			@FormDataParam("vDate") String vDate, @FormDataParam("vPostDate") String vPostDate,
			@FormDataParam("vMonth") String vMonth, @FormDataParam("vType") String vType,
			@FormDataParam("vPrefix") String vPrefix, @FormDataParam("vBU") String vBU,
			@FormDataParam("vCostcenter") String vCostcenter, @FormDataParam("vAccountant") String vAccountant,
			@FormDataParam("vRequestor") String vRequestor, @FormDataParam("vPurpose") String vPurpose,
			@FormDataParam("vRemark") String vRemark, @FormDataParam("vPurpose") String purpose,
			@FormDataParam("vApprove1") String vApprove1, @FormDataParam("vApprove2") String vApprove2,
			@FormDataParam("vApprove3") String vApprove3, @FormDataParam("vApprove4") String vApprove4,
			@FormDataParam("vApprove5") String vApprove5, @FormDataParam("vAppICT") String vAppICT,
			@FormDataParam("vAppCIO") String vAppCIO, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vSubmit") String vSubmit) throws JSONException {
		logger.info("/marhead");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(UpdateData.updateMARHead(getCono, getDivi, vMARNumber, vDate, vPostDate, vMonth,
							vType, vPrefix, vBU, vCostcenter, vAccountant, vRequestor, vRemark, purpose, vApprove1,
							vApprove2, vApprove3, vApprove4, vApprove5, vAppICT, vAppCIO, vStatus, vSubmit,
							getUsername), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/marhead/{marno}/{status}/{submit}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateStatusMARHead(@Context HttpHeaders headers, @PathParam("marno") String marno,
			@PathParam("status") String status, @PathParam("submit") String submit, String req) throws JSONException {
		logger.info("/marhead");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response
							.ok(UpdateData.updateStatusMARHead(getCono, getDivi, marno, status, submit, getUsername),
									MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/mardetail")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateMARDetail(@Context HttpHeaders headers, @FormDataParam("vMARNumber") String vMARNumber,
			@FormDataParam("vPrefix") String vPrefix, @FormDataParam("vRefNumber") String vRefNumber,
			@FormDataParam("vTypeAdjust") String vTypeAdjust, @FormDataParam("vItemLine") String vItemLine,
			@FormDataParam("vItem") String vItem, @FormDataParam("vItemDesc1") String vItemDesc1,
			@FormDataParam("vFacility") String vFacility, @FormDataParam("vWarehouse") String vWarehouse,
			@FormDataParam("vLocation") String vLocation, @FormDataParam("vLotNo") String vLotNo,
			@FormDataParam("vDate") String vDate, @FormDataParam("vUnit") String vUnit,
			@FormDataParam("vQty") String vQty, @FormDataParam("vUnitPrice") String vUnitPrice,
			@FormDataParam("vAmount") String vAmount, @FormDataParam("vRemark1") String vRemark1,
			@FormDataParam("vRemark2") String vRemark2, @FormDataParam("vStatus") String vStatus) throws JSONException {
		logger.info("/mardetail");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(
							UpdateData.updateMARDetail(getCono, getDivi, vMARNumber, vPrefix, vRefNumber, vTypeAdjust,
									vItemLine, vItem, vItemDesc1, vFacility, vWarehouse, vLocation, vLotNo, vDate,
									vUnit, vQty, vUnitPrice, vAmount, vRemark1, vRemark2, vStatus, getUsername),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@DELETE
	@Path("/deleteswrfile/{SFORNO}")

	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response deleteSWRFile(@Context HttpHeaders headers, @PathParam("SFORNO") String SFORNO)
			throws JSONException {
		logger.info("/mardetail/{marno}/{line}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(DeleteData.deleteSWRFile(getCono, getDivi, SFORNO),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/swrnumber")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getSWRNumber(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("swrnumber");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String[] getSubject = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response
							.ok(SelectData.getSWRNumber(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@DELETE
	@Path("/mardetail/{marno}/{line}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response deleteMARDetail(@Context HttpHeaders headers, @PathParam("marno") String marno,
			@PathParam("line") String line, String req) throws JSONException {
		logger.info("/mardetail/{marno}/{line}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);

			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(DeleteData.deleteMARDetail(getCono, getDivi, marno, line),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/marfile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response deleteMARFile(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vMARNumber") String vMARNumber, @FormDataParam("vPrefix") String vPrefix,
			@FormDataParam("vFile") InputStream vFile, @FormDataParam("vFileName") String vFileName,
			@FormDataParam("vFileType") String vFileType, @FormDataParam("vFileLine") String vFileLine,
			@FormDataParam("vRemark1") String vRemark1, @FormDataParam("vRemark2") String vRemark2)
			throws JSONException {
		logger.info("/marfile");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response.ok(DeleteData.deleteMARFile(httpServletRequest, getCono, getDivi, vMARNumber,
							vFileLine, vFileName), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	@GET
	@Path("/month")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMonth(@Context HttpHeaders headers, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {

					return Response
							.ok(SelectData.getMonth(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/year")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getYear(@Context HttpHeaders headers, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {

					return Response
							.ok(SelectData.getYear(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/credit/{credit}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getCredit(@Context HttpHeaders headers, @PathParam("credit") String credit, String req)
			throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {

					return Response.ok(SelectData.getCredit(getCono, getDivi, credit),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/round")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getRound(@Context HttpHeaders headers, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {

					return Response
							.ok(SelectData.getRound(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getStatus(@Context HttpHeaders headers, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {

					return Response
							.ok(SelectData.getStatus(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/customer")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getCustomer(@Context HttpHeaders headers, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {
					return Response.ok(SelectData.getCustomerV2(getCono, getDivi, userID),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/adrnumber/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getADRNumber(@Context HttpHeaders headers, @PathParam("fromstatus") String fromstatus,
			@PathParam("tostatus") String tostatus, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(SelectData.getADRNumber(getCono, getDivi, username, fromstatus, tostatus),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/adrnumberaccountant/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getADRNumberAccountant(@Context HttpHeaders headers, @PathParam("fromstatus") String fromstatus,
			@PathParam("tostatus") String tostatus, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response
							.ok(SelectData.getADRNumberAccountant(getCono, getDivi, username, fromstatus, tostatus),
									MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/adrhead/{adrno}/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getADRHead(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@PathParam("adrno") String adrno, @PathParam("fromstatus") String fromstatus,
			@PathParam("tostatus") String tostatus, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							SelectData.getADRHead(httpServletRequest, getCono, getDivi, adrno, fromstatus, tostatus),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/adrheadmonitoring/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getADRHeadMonitoring(@Context HttpHeaders headers, @PathParam("fromstatus") String fromstatus,
			@PathParam("tostatus") String tostatus, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(SelectData.getADRHeadMonitoring(getCono, getDivi, fromstatus, tostatus),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/adrdetail/{adrno}/{fromstatus}/{tostatus}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getADRDetail(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@PathParam("adrno") String adrno, @PathParam("fromstatus") String fromstatus,
			@PathParam("tostatus") String tostatus, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							SelectData.getADRDetail(httpServletRequest, getCono, getDivi, adrno, fromstatus, tostatus),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/monitoringhead/{orderno}/{date}/{month}/{year}/{status}/{credit}/{round}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMonitorHead(@Context HttpHeaders headers, @PathParam("orderno") String orderno,
			@PathParam("date") String date, @PathParam("month") String month, @PathParam("year") String year,
			@PathParam("status") String status, @PathParam("credit") String credit, @PathParam("round") String round,
			String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {
					return Response.ok(
							SelectData.getMonitoringHead(getCono, getDivi,
									ConvertStringtoObject.convertNullToObject(orderno),
									ConvertStringtoObject.convertNullToObject(date),
									ConvertStringtoObject.convertNullToObject(month),
									ConvertStringtoObject.convertNullToObject(year),
									ConvertStringtoObject.convertNullToObject(status),
									ConvertStringtoObject.convertNullToObject(credit),
									ConvertStringtoObject.convertNullToObject(round), userID, group, auth),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/image/{filename}")
	@Produces({ "image/png", "image/jpg", "image/gif" })
	public Response getImage(@Context HttpHeaders headers, @PathParam("filename") String filename,
			@Context HttpServletRequest httpServletRequest) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(SelectData.getBU(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/image")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addImage(@FormDataParam("vImageFile") InputStream fileInputStream,
			@FormDataParam("vImageFile") FormDataContentDisposition fileFormDataContentDisposition,
			@FormDataParam("vImageName") String vImageName, @Context HttpHeaders headers,
			@Context HttpServletRequest httpServletRequest) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				String uploadFilePath = null;
				String filePath = httpServletRequest.getRealPath("/") + "WEB-INF\\image\\";

				try {
					// System.out.println("getFileName: " +
					// fileFormDataContentDisposition.getFileName());
					// fileName = fileFormDataContentDisposition.getFileName();
					// fileName = "jaonaay";
					System.out.println("filePath: " + filePath + vImageName);

					uploadFilePath = FileUtillity.writeToFileServer(fileInputStream, vImageName, filePath);
					return Response.status(Response.Status.OK).build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/file")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addFile(@FormDataParam("vImageFile") InputStream fileInputStream,
			@FormDataParam("vImageFile") FormDataContentDisposition fileFormDataContentDisposition,
			@FormDataParam("vImageName") String vImageName, @Context HttpHeaders headers,
			@Context HttpServletRequest httpServletRequest) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		// Jws<Claims> validate = null;
		//
		// if (getToken != null) {
		//
		// try {
		//
		// // jwt verify token
		// validate = JwtManager.parseToken(getToken);
		// // System.out.println("validate: " + validate);
		// String username = validate.getBody().get("aud", String.class);
		// String company = validate.getBody().get("sub", String.class);
		// String getCompany[] = company.split(" : ");
		// String getCono = getCompany[0];
		// String getDivi = getCompany[1];
		// String getCompanyName = getCompany[2];
		// // String getCono = "10";
		// // String getDivi = "101";

		String uploadFilePath = null;
		String filePath = httpServletRequest.getRealPath("/") + "WEB-INF\\image\\";

		try {
			// System.out.println("getFileName: " +
			// fileFormDataContentDisposition.getFileName());
			// fileName = fileFormDataContentDisposition.getFileName();
			// fileName = "jaonaay";
			System.out.println("filePath: " + filePath + vImageName);

			uploadFilePath = FileUtillity.writeToFileServer(fileInputStream, vImageName, filePath);
			return Response.status(Response.Status.OK).build();

		} catch (Exception e) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", e);

		}

		// } catch (SignatureException e) {
		// mJsonObj.put("auth", "false");
		// mJsonObj.put("message", e.getMessage());
		//
		// }
		//
		// } else {
		// mJsonObj.put("auth", "false");
		// mJsonObj.put("message", "No token provided");
		// }

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	// @Path("/files")
	// public class FileDownloadService {
	private static final String FILE_PATH = "D:\\files\\api_project\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\mar_api\\WEB-INF\\image\\TEST.pdf";

	// @GET
	// @Path("/file/{filename}/{token}")
	// @Produces("image/png, application/pdf, application/vnd.ms-excel,
	// application/msword")
	// public Response getFile(@Context HttpHeaders headers, @PathParam("filename")
	// String fileName,
	// @PathParam("token") String token, @Context HttpServletRequest
	// httpServletRequest) {
	// JSONObject mJsonObj = new JSONObject();
	// String getToken = headers.getRequestHeaders().getFirst("x-access-token");
	// System.out.println("headers: " + headers);
	// System.out.println("getToken: " + getToken);
	//
	// return FileUtillity.getFileV3(httpServletRequest, fileName);
	//
	// }

	@DELETE
	@Path("/file/{filename}/{token}")
	@Produces("image/png, application/pdf, application/vnd.ms-excel, application/msword")
	public Response deleteFile(@Context HttpHeaders headers, @PathParam("filename") String fileName,
			@PathParam("token") String token, @Context HttpServletRequest httpServletRequest) {
		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("headers: " + headers);
		System.out.println("getToken: " + getToken);

		return FileUtillity.deleteFileV3(httpServletRequest, fileName);

	}

	@GET
	@Path("/filev2/{name}/{token}")
	@Produces("image/png, application/pdf, application/vnd.ms-excel, application/msword")
	public Response getFileV2(@Context HttpHeaders headers) {
		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("headers: " + headers);
		System.out.println("getToken: " + getToken);

		File file = new File(FILE_PATH);
		ResponseBuilder response = Response.ok((Object) file);
		response.header("Content-Disposition", "attachment; filename=\"TEST.pdf\"");
		return response.build();
		// return null;

		// return Response.ok(SelectData.getADRDetail(httpServletRequest, getCono,
		// getDivi, adrno, fromstatus, tostatus),
		// MediaType.APPLICATION_JSON + ";charset=utf8").build();

	}

	// vMARNumber: "",
	// vDate: moment(new Date()).format("YYYY-MM-DD"),
	// vPostDate: moment(new Date()).format("YYYY-MM-DD"),
	// vMonth: moment(new Date()).format("YYYYMM"),
	// vType: "",
	// vPrefix: "",
	// vBU: "",
	// vCostcenter: "",
	// vAccountant: "",
	// vRequestor: loginActions.getTokenUsername(),
	// vPurpose: "",
	// vRemark: "",
	// vApprove1: "",
	// vApprove2: "",
	// vApprove3: "",
	// vApprove4: "",
	// vApprove5: "",
	// vStatus: "",

	@POST
	@Path("/adrhead")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addADRHead(@Context HttpHeaders headers, @FormDataParam("vADRNumber") String vADRNumber,
			@FormDataParam("vDate") String vDate, @FormDataParam("vMonth") String vMonth,
			@FormDataParam("vType") String vType, @FormDataParam("vPrefix") String vPrefix,
			@FormDataParam("vBOI") String vBOI, @FormDataParam("vBU") String vBU,
			@FormDataParam("vCostcenter") String vCostcenter, @FormDataParam("vVat") String vVat,
			@FormDataParam("vAccountant") String vAccountant, @FormDataParam("vRequestor") String vRequestor,
			@FormDataParam("vRemark") String vRemark, @FormDataParam("vApprove1") String vApprove1,
			@FormDataParam("vApprove2") String vApprove2, @FormDataParam("vApprove3") String vApprove3,
			@FormDataParam("vApprove4") String vApprove4, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vSubmit") String vSubmit) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							InsertData.addADRHead(getCono, getDivi, vADRNumber, vDate, vMonth, vType, vPrefix, vBOI,
									vBU, vCostcenter, vVat, vAccountant, vRequestor, vRemark, vApprove1, vApprove2,
									vApprove3, vApprove4, vStatus, vSubmit, username),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/orderhead")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addOrderHead(@Context HttpHeaders headers, @FormDataParam("vOrderNumber") String vOrderNumber,
			@FormDataParam("vCustomerNo") String vCustomerNo, @FormDataParam("vOrderDate") String vOrderDate,
			@FormDataParam("vDeliveryDate") String vDeliveryDate, @FormDataParam("vRound") String vRound,
			@FormDataParam("vPricelist") String vPricelist, @FormDataParam("vOrderType") String vOrderType,
			@FormDataParam("vWarehouse") String vWarehouse, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vType") String vType, @FormDataParam("vRemark") String vRemark,
			@FormDataParam("vPONumber") String vPONumber, @FormDataParam("vDay") String vDay) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				String username = validate.getBody().get("aud", String.class);

				String getUser[] = validate.getBody().get("role", String.class).split(" ; ");
				String userID = getUser[0];
				String userRef = getUser[1];
				String group = getUser[2];
				String auth = getUser[3];

				String getSubject[] = validate.getBody().get("sub", String.class).split(" ; ");
				String company = getSubject[0];
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {

					return Response.ok(InsertData.addOrderHeadV2(getCono, getDivi, vCustomerNo, vOrderDate,
							vDeliveryDate, vRound, vPricelist, vOrderType, vWarehouse, vStatus, vType, vRemark,
							vPONumber, vDay, userID), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/adrdetail")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response addADRDetail(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vADRNumber") String vADRNumber, @FormDataParam("vPrefix") String vPrefix,
			@FormDataParam("vItemLine") String vItemLine, @FormDataParam("vItemNo") String vItemNo,
			@FormDataParam("vSBNO") String vSBNO, @FormDataParam("vItemCostcenter") String vItemCostcenter,
			@FormDataParam("vItemDate") String vItemDate, @FormDataParam("vAssetCost") String vAssetCost,
			@FormDataParam("vNetValue") String vNetValue, @FormDataParam("vItemQty") String vItemQty,
			@FormDataParam("vItemPrice") String vItemPrice, @FormDataParam("vItemRemark") String vItemRemark,
			@FormDataParam("vImageFile") InputStream fileInputStream,
			@FormDataParam("vImageFile") FormDataContentDisposition fileFormDataContentDisposition,
			@FormDataParam("vImageName") String vImageName, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vSubmit") String vSubmit) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							InsertData.addADRDetail(httpServletRequest, getCono, getDivi, vADRNumber, vPrefix,
									vItemLine, vItemNo, vSBNO, vItemCostcenter, vItemDate, vAssetCost, vNetValue,
									vItemQty, vItemPrice, vItemRemark, fileInputStream,
									ConvertStringtoObject.convertNullToObject(vImageName), vStatus, vSubmit, username),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/adrdetail")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateADRDetail(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vADRNumber") String vADRNumber, @FormDataParam("vPrefix") String vPrefix,
			@FormDataParam("vItemLine") String vItemLine, @FormDataParam("vItemNo") String vItemNo,
			@FormDataParam("vSBNO") String vSBNO, @FormDataParam("vItemCostcenter") String vItemCostcenter,
			@FormDataParam("vItemDate") String vItemDate, @FormDataParam("vAssetCost") String vAssetCost,
			@FormDataParam("vNetValue") String vNetValue, @FormDataParam("vItemQty") String vItemQty,
			@FormDataParam("vItemPrice") String vItemPrice, @FormDataParam("vItemRemark") String vItemRemark,
			@FormDataParam("vImageFile") InputStream fileInputStream,
			@FormDataParam("vImageFile") FormDataContentDisposition fileFormDataContentDisposition,
			@FormDataParam("vImageName") String vImageName, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vSubmit") String vSubmit) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							UpdateData.updateADRDetail(httpServletRequest, getCono, getDivi, vADRNumber, vPrefix,
									vItemLine, vItemNo, vSBNO, vItemCostcenter, vItemDate, vAssetCost, vNetValue,
									vItemQty, vItemPrice, vItemRemark, fileInputStream,
									ConvertStringtoObject.convertNullToObject(vImageName), vStatus, vSubmit, username),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/adrimage")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateADRImage(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vADRNumber") String vADRNumber, @FormDataParam("vImageFile") InputStream fileInputStream,
			@FormDataParam("vImageFile") FormDataContentDisposition fileFormDataContentDisposition,
			@FormDataParam("vImageName") String vImageName) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							UpdateData.updateADRImage(httpServletRequest, getCono, getDivi, vADRNumber, fileInputStream,
									ConvertStringtoObject.convertNullToObject(vImageName), username),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/adrhead")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateADRHead(@Context HttpHeaders headers, @FormDataParam("vADRNumber") String vADRNumber,
			@FormDataParam("vDate") String vDate, @FormDataParam("vMonth") String vMonth,
			@FormDataParam("vType") String vType, @FormDataParam("vPrefix") String vPrefix,
			@FormDataParam("vBOI") String vBOI, @FormDataParam("vBU") String vBU,
			@FormDataParam("vCostcenter") String vCostcenter, @FormDataParam("vVat") String vVat,
			@FormDataParam("vAccountant") String vAccountant, @FormDataParam("vRequestor") String vRequestor,
			@FormDataParam("vRemark") String vRemark, @FormDataParam("vApprove1") String vApprove1,
			@FormDataParam("vApprove2") String vApprove2, @FormDataParam("vApprove3") String vApprove3,
			@FormDataParam("vApprove4") String vApprove4, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vSubmit") String vSubmit) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							UpdateData.updateADRHead(getCono, getDivi, vADRNumber, vDate, vMonth, vType, vPrefix, vBOI,
									vBU, vCostcenter, vVat, vAccountant, vRequestor, vRemark, vApprove1, vApprove2,
									vApprove3, vApprove4, vStatus, vSubmit, username),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/statusadrhead/{adrno}/{status}/{submit}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateStatusADRHead(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@PathParam("adrno") String adrno, @PathParam("status") String status, @PathParam("submit") String submit,
			String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {
					return Response.ok(UpdateData.updateStatusADRHead(httpServletRequest, getCono, getDivi, adrno,
							status, submit, username), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/rejectadrhead")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8") ADACRE1, ADAPRE1,
	// ADAPRE2, ADAPRE3, ADAPRE4
	public Response updateRejectADRHead(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vADRNumber") String vADRNumber, @FormDataParam("vRemark") String vRemark,
			@FormDataParam("vAccRemark") String vAccRemark, @FormDataParam("vAppRemark1") String vAppRemark1,
			@FormDataParam("vAppRemark2") String vAppRemark2, @FormDataParam("vAppRemark3") String vAppRemark3,
			@FormDataParam("vAppRemark4") String vAppRemark4, @FormDataParam("vStatus") String vStatus,
			@FormDataParam("vSubmit") String vSubmit) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];
				// String getCono = "10";
				// String getDivi = "101";

				try {

					return Response.ok(
							UpdateData.updateRejectADRHead(httpServletRequest, getCono, getDivi, vADRNumber, vRemark,
									vAccRemark, ConvertStringtoObject.convertNullToObject(vAppRemark1),
									ConvertStringtoObject.convertNullToObject(vAppRemark2),
									ConvertStringtoObject.convertNullToObject(vAppRemark3),
									ConvertStringtoObject.convertNullToObject(vAppRemark4), vStatus, vSubmit, username),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@DELETE
	@Path("/adrdetail/{adrno}/{line}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response deleteADRDetail(@Context HttpHeaders headers, @PathParam("adrno") String adrno,
			@PathParam("line") String line) throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		Jws<Claims> validate = null;

		if (getToken != null) {

			try {

				// jwt verify token
				validate = JwtManager.parseToken(getToken);
				// System.out.println("validate: " + validate);
				String username = validate.getBody().get("aud", String.class);
				String company = validate.getBody().get("sub", String.class);
				String getCompany[] = company.split(" : ");
				String getCono = getCompany[0];
				String getDivi = getCompany[1];
				String getCompanyName = getCompany[2];

				try {

					return Response.ok(DeleteData.deleteADRDetail(getCono, getDivi, adrno, line),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e);

				}

			} catch (SignatureException e) {
				mJsonObj.put("auth", "false");
				mJsonObj.put("message", e.getMessage());

			}

		} else {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", "No token provided");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	/// WITH OUT TOKEN

	@GET
	@Path("/adrheadwotoken/{cono}/{divi}/{adrno}/{fromstatus}/{tostatus}/{approve}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getADRHeadWOToken(@Context HttpHeaders headers, @PathParam("cono") String cono,
			@PathParam("divi") String divi, @PathParam("adrno") String adrno,
			@PathParam("fromstatus") String fromstatus, @PathParam("tostatus") String tostatus,
			@PathParam("approve") String approve, String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();

		try {

			try {

				return Response.ok(SelectData.getADRHeadWOToken(cono, divi, adrno, fromstatus, tostatus, approve),
						MediaType.APPLICATION_JSON + ";charset=utf8").build();

			} catch (Exception e) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", e);

			}

		} catch (SignatureException e) {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", e.getMessage());
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/adrdetailwotoken/{cono}/{divi}/{adrno}/{status}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getADRDetailWOToken(@Context HttpHeaders headers, @PathParam("cono") String cono,
			@PathParam("divi") String divi, @PathParam("adrno") String adrno, @PathParam("status") String status,
			String req) throws JSONException {

		JSONObject mJsonObj = new JSONObject();

		try {

			try {

				return Response.ok(SelectData.getADRDetailWOToken(cono, divi, adrno, status),
						MediaType.APPLICATION_JSON + ";charset=utf8").build();

			} catch (Exception e) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", e);

			}

		} catch (SignatureException e) {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", e.getMessage());
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/statusadrheadwotoken/{cono}/{divi}/{adrno}/{status}/{submit}/{username}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateStatusADRHeadWOToken(@Context HttpHeaders headers,
			@Context HttpServletRequest httpServletRequest, @PathParam("cono") String cono,
			@PathParam("divi") String divi, @PathParam("adrno") String adrno, @PathParam("status") String status,
			@PathParam("submit") String submit, @PathParam("username") String username, String req)
			throws JSONException {

		JSONObject mJsonObj = new JSONObject();
		try {

			System.out.println(cono + " " + divi + " " + adrno + " " + status + " " + submit + " " + username);

			try {
				return Response.ok(
						UpdateData.updateStatusADRHead(httpServletRequest, cono, divi, adrno, status, submit, username),
						MediaType.APPLICATION_JSON + ";charset=utf8").build();

			} catch (Exception e) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", e);

			}

		} catch (SignatureException e) {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", e.getMessage());
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@PUT
	@Path("/rejectadrheadwotoken")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_JSON + ";charset=utf-8") ADACRE1, ADAPRE1,
	// ADAPRE2, ADAPRE3, ADAPRE4
	public Response updateRejectADRHeadWOToken(@Context HttpHeaders headers,
			@Context HttpServletRequest httpServletRequest, @FormDataParam("vCono") String vCono,
			@FormDataParam("vDivi") String vDivi, @FormDataParam("vADRNumber") String vADRNumber,
			@FormDataParam("vRemark") String vRemark, @FormDataParam("vAccRemark") String vAccRemark,
			@FormDataParam("vAppRemark1") String vAppRemark1, @FormDataParam("vAppRemark2") String vAppRemark2,
			@FormDataParam("vAppRemark3") String vAppRemark3, @FormDataParam("vAppRemark4") String vAppRemark4,
			@FormDataParam("vStatus") String vStatus, @FormDataParam("vSubmit") String vSubmit,
			@FormDataParam("vUsername") String vUsername) throws JSONException {

		JSONObject mJsonObj = new JSONObject();

		try {

			try {

				return Response.ok(
						UpdateData.updateRejectADRHead(httpServletRequest, vCono, vDivi, vADRNumber, vRemark,
								vAccRemark, ConvertStringtoObject.convertNullToObject(vAppRemark1),
								ConvertStringtoObject.convertNullToObject(vAppRemark2),
								ConvertStringtoObject.convertNullToObject(vAppRemark3),
								ConvertStringtoObject.convertNullToObject(vAppRemark4), vStatus, vSubmit, vUsername),
						MediaType.APPLICATION_JSON + ";charset=utf8").build();

			} catch (Exception e) {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", e);

			}

		} catch (SignatureException e) {
			mJsonObj.put("auth", "false");
			mJsonObj.put("message", e.getMessage());

		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/addreceipt")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addreceipt(@FormDataParam("rcno") String rcno, @FormDataParam("voucher") String voucher,
			@FormDataParam("fixno") String fixno) throws JSONException {
		logger.info("/staffcode");
		JSONObject mJsonObj = new JSONObject();

		try {
			return Response
					.ok(InsertData.addreceipt(rcno, voucher, fixno), MediaType.APPLICATION_JSON + ";charset=utf8")
					.build();

		} catch (Exception e) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", e);
			logger.error(e.getMessage());
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	///////////////////// VSITORHEADER //////////////

//todo
	
	

	@POST
	@Path("insertheadervisitor")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	
	public Response addheadervisitor(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest, @FormDataParam("vCONO") String vCONO,@FormDataParam("vDIVI") String vDIVI,@FormDataParam("vLocation") String vLocation)
			throws JSONException {
		logger.info("/addheadervisitor");

		JSONObject mJsonObj = new JSONObject();

		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");

		

		
	

				try {

					return Response.ok(InsertData.addvisitorheader(vCONO,vDIVI,vLocation), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			

		

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	
	
	
	//////////// SENDEMAIL///////////////
	
	
	
	

	@POST
	@Path("/sendemailpp/{prefix}/{ordno}/{status}/{submit}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response sendemailpp(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@PathParam("prefix") String prefix, @PathParam("ordno") String ordno, @PathParam("status") String status,
			@PathParam("submit") String submit,
			String req) throws JSONException {
		logger.info("/sendemailpp/{prefix}/{ordno}/{status}/{submit}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		 System.out.println("mailllllllllllllllllllllllllllllllllllllllllllllllllll");

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionCheckToken(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String[] getSubject = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");
				String getLocation = getSubject[3];

				try {

					return Response.ok(
							SendEmail.prepareResendEmail(httpServletRequest, getCono, getDivi, prefix, ordno, status,
									status, submit, getUsername, getToken , getLocation),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	
	
	
	
	
	
	@POST
	@Path("/resendemail/{prefix}/{marno}/{status}/{submit}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response resendEmail(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@PathParam("prefix") String prefix, @PathParam("marno") String marno, @PathParam("status") String status,
			@PathParam("submit") String submit,@PathParam("getLocation") String getLocation,
			String req) throws JSONException {
		logger.info("/resendemail/{prefix}/{marno}/{status}/{submit}");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionCheckToken(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String[] getSubject = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(
							SendEmail.prepareResendEmail(httpServletRequest, getCono, getDivi, prefix, marno, status,
									status, submit, getUsername, getToken , getLocation),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//////////////////////////////
	
	
	
	
	

	@GET
	@Path("/location/{CONO}/{DIVI}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response location(@Context HttpHeaders headers,
			 @PathParam("CONO") String CONO,
			 @PathParam("DIVI") String DIVI,
			@Context HttpServletRequest httpServletRequest) throws JSONException {
		logger.info("/getfollower");

		JSONObject mJsonObj = new JSONObject();

		
			

				try {

					return Response.ok(SelectData.location(CONO,DIVI), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

		
		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}
	
	
	
	

	@GET
	@Path("/getfollower/{vID}/{CONO}/{DIVI}/{LOCATION}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getfollower(@Context HttpHeaders headers, @PathParam("vID") String vID,
			 @PathParam("CONO") String CONO,
			 @PathParam("DIVI") String DIVI,
			 @PathParam("LOCATION") String LOCATION,
			@Context HttpServletRequest httpServletRequest) throws JSONException {
		logger.info("/getfollower");

		JSONObject mJsonObj = new JSONObject();

		
			

				try {

					return Response.ok(SelectData.getfollower(vID), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

		
		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/getemployee")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getemployee(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest)
			throws JSONException {
		logger.info("/getemployee");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(SelectData.getemployee(getCono), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

//////// SWR HEADER　/////////////

	@GET
	@Path("/getID")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getID(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest)
			throws JSONException {
		logger.info("/getID");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(SelectData.getID(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/getnewID")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getnewID(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest)
			throws JSONException {
		logger.info("/getID");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response
							.ok(SelectData.getnewID(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/getDeptHead")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getDeptHead(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest)
			throws JSONException {
		logger.info("/getDeptHead");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response
							.ok(SelectData.getDeptHead(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/getDev")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getDev(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest)
			throws JSONException {
		logger.info("/getDeptHead");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response
							.ok(SelectData.getDev(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

//////////////////////////////////////////

	@POST
	@Path("/addfollower")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addfollower(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("H_SURNAME") String H_SURNAME, @FormDataParam("vID") String vID
			, @FormDataParam("vCONO") String vCONO
			, @FormDataParam("vDIVI") String vDIVI
			, @FormDataParam("vLocation") String vLocation) throws JSONException {
		logger.info("/addfollower");

		JSONObject mJsonObj = new JSONObject();
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");

	
				try {

					return Response.ok(InsertData.addfollower( vCONO, vDIVI,vLocation, H_SURNAME, vID),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/addheaeder")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addHeader(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vSwrtype") String vSwrtype, @FormDataParam("vSwrname") String vSwrname,
			@FormDataParam("vVersion") String vVersion, @FormDataParam("vReqdate") String vReqdate,
			@FormDataParam("vFinishdate") String vFinishdate, @FormDataParam("vRemark") String vRemark,
			@FormDataParam("vRequester") String vRequester, @FormDataParam("vDepthead") String vDepthead,
			@FormDataParam("vDev") String vDev, @FormDataParam("vAppdevmanager") String vAppdevmanager,
			@FormDataParam("vGM") String vGM, @FormDataParam("vCIO") String vCIO,
			@FormDataParam("vStatus") String vStatus) throws JSONException {
		logger.info("/addheaeder");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(InsertData.addSwrHeader(getCono, getDivi, vSwrtype, vSwrname, vVersion, vReqdate,
							vFinishdate, vRemark, vRequester, vDepthead, vDev, vAppdevmanager, vGM, vCIO, vStatus),
							MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@POST
	@Path("/swrfile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addSWRFile(@Context HttpHeaders headers, @Context HttpServletRequest httpServletRequest,
			@FormDataParam("vReference") String vReference, @FormDataParam("vOrderno") String vOrderno,
			@FormDataParam("vName") String vName, @FormDataParam("vLine") String vLine,
			@FormDataParam("vType") String vType, @FormDataParam("vRemark1") String vRemark1,
			@FormDataParam("vRemark2") String vRemark2) throws JSONException {
		logger.info("/marfile");

		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {

					return Response.ok(InsertData.addSWRFile(getCono, getDivi, vOrderno, vReference, vName, vLine,
							vType, vRemark1, vRemark2), MediaType.APPLICATION_JSON + ";charset=utf8").build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	@GET
	@Path("/getswrfile")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getSWRFile(@Context HttpHeaders headers, String req) throws JSONException {
		logger.info("/swrfile");
		JSONObject mJsonObj = new JSONObject();
		String getToken = headers.getRequestHeaders().getFirst("x-access-token");
		// System.out.println("getToken: " + getToken);

		if (getToken != null && !getToken.isEmpty()) {
			String getTokenData = HttpConnection.httpConnectionV2(getToken);
			// System.out.println("getTokenData: " + getTokenData);

			JSONObject dataObject = new JSONObject(getTokenData);
			boolean checkToken = Boolean.parseBoolean(dataObject.getString("message"));

			if (checkToken) {
				JSONObject getDataObject = dataObject.getJSONObject("body");
				String getSubject[] = getDataObject.getString("sub").split(" : ");
				String getCono = getSubject[0];
				String getDivi = getSubject[1];
				String getCompanyName = getSubject[2];
				String getUsername = getDataObject.getString("aud");
				String getAuth = getDataObject.getString("role");

				try {
					return Response
							.ok(SelectData.getSWRile(getCono, getDivi), MediaType.APPLICATION_JSON + ";charset=utf8")
							.build();

				} catch (Exception e) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", e.getMessage());
					logger.error(e.getMessage());
				}

			} else {
				mJsonObj.put("result", "nok");
				mJsonObj.put("message", "Token expired.");
				logger.error("Token expired.");
			}

		} else {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", "No token provided.");
			logger.error("No token provided.");
		}

		return Response.status(Response.Status.NOT_FOUND).entity(mJsonObj).build();

	}

	/////////////

	////////////////

}
