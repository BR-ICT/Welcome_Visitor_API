package com.br.data;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.br.connection.ConnectDB2;
import com.br.utility.Constant;
import com.br.utility.ConvertString;
import com.br.utility.FileUtillity;

import groovy.ui.Console;

public class InsertData {

	private static final Logger logger = LogManager.getLogger(InsertData.class);

	protected static String DBNAME = Constant.DBNAME;
	protected static String DBM3NAME = Constant.DBM3NAME;

	static DecimalFormat df0 = new DecimalFormat("0");
	static DecimalFormat df2 = new DecimalFormat("#.##");
	static DecimalFormat df3 = new DecimalFormat("#.###");
	static DecimalFormat df4 = new DecimalFormat("#.####");
	
	
	
	
	
	public static String addvisitorheader(String vCONO, String  vDIVI, String vLocation )
			throws Exception {
		logger.info("addvisitorheader");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			
			
	


			String query = "　INSERT  INTO  BRLDTABK01.VISITOR_HEAD \r\n"
					+ "　(H_ID,H_TIMEIN,H_CONO,H_DIVI,H_LOCATION,H_ROOMNO,H_CARDNO)\r\n"
					+ "　VALUES ((SELECT COALESCE(MAX(H_ID)+1,\r\n"
					+ "	 SUBSTRING(REPLACE(CHAR(current date, ISO),'-',''),3,2) || '000001' ) AS DATE\r\n"
					+ "	 FROM BRLDTABK01.VISITOR_HEAD WHERE H_CONO = '"+vCONO.trim()+"' AND  H_DIVI = '"+vDIVI.trim()+"' AND　H_LOCATION = '"+vLocation.trim()+"'),(SELECT VARCHAR_FORMAT(CURRENT TIMESTAMP, 'YYYY-MM-DD HH:mi:ss')\r\n"
					+ "FROM SYSIBM.SYSDUMMY1),'"+vCONO.trim()+"','"+vDIVI.trim()+"','"+vLocation.trim()+"','-','-')";
			 System.out.println("addvisitorheader\n" + query);
			logger.debug(query);
			stmt.execute(query);

			//todo 
			
			String getORDER = SelectData.getID1();

			
			mJsonObj.put("result", "ok");
			mJsonObj.put("message", query);
			mJsonObj.put("ID", getORDER);

			return mJsonObj.toString();
			//return getOrderID.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}
	
	
	
	
	public static String addfollower(String cono, String divi, String  location ,String	H_SURNAME,String vID)
			throws Exception {
		logger.info("addfollower");
		
	  System.out.print("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();


			String query = " INSERT INTO BRLDTABK01.FOLLOWER_HEAD \r\n"
					+ "(H_CONO,H_DIVI,H_LOCATION,H_NAME ,H_SURNAME , F_ID , H_REMARK1, H_STATUS)\r\n"
					+ "VALUES \r\n"
					+ "('"+cono.trim()+"','"+divi.trim()+"','"+location.trim()+"', 'Follower','"+H_SURNAME.trim()+"','"+vID.trim()+"', (SELECT  COALESCE(MAX(H_REMARK1)+1,'1' ) AS FOLLOWID  \r\n"
							+ "	 FROM BRLDTABK01.FOLLOWER_HEAD \r\n"
							+ "	 WHERE  F_ID  = '"+vID.trim()+"'),NULL)";
			 System.out.println("addfollower\n" + query);
			logger.debug(query);
			stmt.execute(query);

			
			mJsonObj.put("result", "ok");
			mJsonObj.put("message", query);

			return mJsonObj.toString();
			//return getOrderID.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}
	
	
	
	

	public static String addSwrHeader(String cono, String divi, String	vSwrtype,
			String vSwrname,String vVersion,String vReqdate,String vFinishdate,
			String vRemark,String vRequester,String vDepthead, String vDev,
			String vAppdevmanager,String vGM,String vCIO,String vStatus
)
			throws Exception {
		logger.info("addSwrHeader");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			/*
			String getYear4 = month.substring(0, 4);
			String getYear2 = month.substring(2, 4);
			remark = ConvertString.convertApostrophe(remark);
			*/ 
			
			//String getOrderID = SelectData.getnewID(cono, divi);
			

			//logger.debug("getOrderID {}", getOrderID);
			
	


			String query = "\r\n"
					+ "INSERT INTO BRLDTABK01.M3_SWRHEAD\r\n"
					+ "(SHORNO,SHCONO,SHDIVI,\r\n"
					+ "SHTYPE,SHREDA,SHFIDA,SHREM1,SHREQU\r\n"
					+ ",SHAPPV,SHSTAT,SHMOD,SHBU,SHCOCE,SHAPPC,SHENDA,SHENTI)\r\n"
					+ "VALUES \r\n"
					+ "((SELECT  COALESCE(MAX(SHORNO)+1,SUBSTRING(REPLACE(CHAR(current date, ISO),'-',''),3,2) || '000001' ) AS ORDERNO   FROM BRLDTABK01.M3_SWRHEAD WHERE   SHCONO = '10' AND  SHDIVI = '101'),\r\n"
					+ "'10','101',\r\n"
					+ "'','2024-04-12','2024-04-12',\r\n"
					+ "'qqqq','M3SRVICT','BSRI','00','-','-','-','-','2008-10-29','14:56:59')\r\n"
					+ "";
			 System.out.println("addSWRHead\n" + query);
			logger.debug(query);
			stmt.execute(query);

			
			mJsonObj.put("result", "ok");
			mJsonObj.put("message", query);

			return mJsonObj.toString();
			//return getOrderID.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}
	
	
	
	
	public static String addImageVisitor(@Context HttpServletRequest httpServletRequest, String cono, String divi,
			String id,
			 InputStream imagefile,
			String imagename) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();


				
				String setImageName = "visitor" + "_" + id + ".png";

				String query = "";
				stmt.execute(query);

				if (imagename != null) {
					String uploadFilePath = null;
					// String filePath = httpServletRequest.getRealPath("/") + "WEB-INF\\image\\";
					String filePath = "D:\\files\\api_project\\adr_images\\";
					// System.out.println("filePath: " + filePath + setImageName);
					uploadFilePath = FileUtillity.writeToFileServer(imagefile, setImageName, filePath);

				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", "complete");
				return mJsonObj.toString();

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}
	
	
	
	
	public static String addMARHead(String cono, String divi, String marno, String date, String postdate,
			String month, String type, String prefix, String bu, String costcenter, String accountant, String requestor,
			String remark, String purpose, String approve1, String approve2, String approve3, String approve4,
			String approve5, String acknoict, String acknocio, String status, String submit, String username)
			throws Exception {
		logger.info("addMARHead");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			
			String getYear4 = month.substring(0, 4);
			String getYear2 = month.substring(2, 4);
			remark = ConvertString.convertApostrophe(remark);
		

			String query = "INSERT INTO BRLDTA0100.M3_MARHEAD \n"
					+ "(MHCONO, MHDIVI, MHPREF, MHORNO, MHREDA, MHPODA, MHMONT, MHTYPE, MHBU, MHCOCE, MHACCT, MHREQU, MHREM1, MHREM2, MHAPP1, MHAPDA1, MHAPP2, MHAPDA2, MHAPP3, MHAPDA3, MHAPP4, MHAPDA4, MHAPP5, MHAPDA5, MHAPICT, MHICTDA, MHAPCIO, MHCIODA, MHACCRE, MHAPRE1, MHAPRE2, MHAPRE3, MHAPRE4, MHAPRE5, MHICTRE, MHCIORE, MHENDA, MHENTI, MHSTAT, MHENUS) \n"
					+ "VALUES('" + cono + "' \n"
					+ ", '" + divi + "' \n"
					+ ", '" + prefix + "' \n"
					+ ", (SELECT CASE WHEN CAST(MAX(MHORNO) AS DECIMAL(10,0)) > 0 THEN CAST(MAX(MHORNO) AS DECIMAL(10,0)) + 1 \n"
					+ "ELSE CAST((SUBSTRING('" + date + "',3,2) || '000001') AS DECIMAL(10,0)) END AS MHORNO \n"
					+ "FROM BRLDTA0100.M3_MARHEAD \n"
					+ "WHERE MHCONO = '" + cono + "' \n"
					+ "AND MHDIVI = '" + divi + "' \n"
					+ "AND MHPREF = '" + prefix + "' \n"
					+ "AND SUBSTRING(CHAR(MHREDA,ISO),0,5) = SUBSTRING('" + date + "',0,5)) \n"
					+ ", '" + date + "' \n"
					+ ", '" + postdate + "' \n"
					+ ", '" + month + "' \n"
					+ ", '" + type + "' \n"
					+ ", '" + bu + "' \n"
					+ ", '" + costcenter + "' \n"
					+ ", '" + accountant + "' \n"
					+ ", '" + requestor + "' \n"
					+ ", '" + remark + "' \n"
					+ ", '" + purpose + "' \n"
					+ ", '" + approve1 + "' \n"
					+ ", NULL \n"
					+ ", '" + approve2 + "' \n"
					+ ", NULL \n"
					+ ", '" + approve3 + "' \n"
					+ ", NULL \n"
					+ ", '" + approve4 + "' \n"
					+ ", NULL \n"
					+ ", '" + approve5 + "' \n"
					+ ", NULL \n"
					+ ", '" + acknoict + "' \n"
					+ ", NULL \n"
					+ ", '" + acknocio + "' \n"
					+ ", NULL \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", CURRENT DATE \n"
					+ ", CURRENT TIME \n"
					+ ", '" + status + "' \n"
					+ ", '" + username + "')";
			// System.out.println("addADRHead\n" + query);
			logger.debug(query);
			stmt.execute(query);

			String getMARNumber = SelectData.getMaxMARNumber(cono, divi, prefix, getYear4, requestor);
			logger.debug("getMARNumber {}", getMARNumber);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", getMARNumber);

			return mJsonObj.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}
	
	
	
	public static String addSWRFile(String cono, String divi,String orderno, String refer, String name, String line, String type, String remark1, String remark2)
			throws Exception {
		logger.info("addSWRFile");
 
		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();
 
			remark1 = ConvertString.convertApostrophe(remark1);
			remark2 = ConvertString.convertApostrophe(remark2);
			
		
 
			String query = "INSERT INTO BRLDTABK01.M3_SWRFILE \n"
					+ "(SFCONO, SFDIVI, SFPREF, SFORNO, SFLINE, SFNAME, SFTYPE, SFREM1, SFREM2, SFENDA, SFENTI, SFENUS) \n"
					+ "VALUES('10' \n"
					+ ", '101' \n"
					+ ", '" + refer + "' \n"
					+ ", '" + orderno + "' \n"
					+ ", '" + line + "' \n"
					+ ", '" + name + "' \n"
					+ ", '" + type + "' \n"
					+ ", '" + remark1 + "' \n"
					+ ", '" + remark2 + "' \n"
					+ ", CURRENT_DATE \n"
					+ ", CURRENT_TIME \n"
					+ ", '')";
			// System.out.println("addMARFile\n" + query);
			logger.debug(query);
			stmt.execute(query);
 
			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Insert file complete.");
			logger.info("Insert complete.");
			return mJsonObj.toString();
 
		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
 
		}
 
		return null;
 
	}
	
	
	
	
	public static String addreceipt(String rcno, String voucher, String fixno)
			throws Exception {
		logger.info("addreceipt");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();


			String query = "    INSERT INTO M3FDBTST.HR_RECEIPT  \r\n"
					+ "                     (HR_CONO,HR_DIVI,HC_RCNO,HC_VCNO,HC_FIXNO)\r\n"
					+ "                     VALUES ('11','111','"+rcno.trim()+"','"+voucher.trim()+"','"+fixno.trim()+"')";
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Insert complete.");
			logger.info("Insert complete.");
			return mJsonObj.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}

	public static String addMARDetail(String cono, String divi, String marno, String prefix, String refno,
			String typeadjust, String line, String item, String name, String fac, String whs, String loc,
			String lotno, String date, String unit, String qty, String price, String amt, String remark1,
			String remark2, String status, String username)
			throws Exception {
		logger.info("addMARDetail");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			String getMARNumber = marno.substring(2, 10);
			remark1 = ConvertString.convertApostrophe(remark1);
			remark2 = ConvertString.convertApostrophe(remark2);

			String query = "INSERT INTO BRLDTA0100.M3_MARDETAIL \n"
					+ "(MLCONO, MLDIVI, MLPREF, MLORNO, MLRENO, MLTYPE, MLFACI, MLWHLO, MLLINE, MLITNO, MLITDE, MLLOCA, MLLOTN, MLDATE, MLUNIT, MLQTY, MLPRIC, MLREM1, MLREM2, MLENDA, MLENTI, MLSTAT, MLENUS) \n"
					+ "VALUES('" + cono + "' \n"
					+ ", '" + divi + "' \n"
					+ ", '" + prefix + "' \n"
					+ ", '" + getMARNumber + "' \n"
					+ ", '" + refno + "' \n"
					+ ", '" + typeadjust + "' \n"
					+ ", '" + fac + "' \n"
					+ ", '" + whs + "' \n"
					+ ", (SELECT COALESCE(MAX(MLLINE),0) + 1 AS MLLINE \n"
					+ "FROM BRLDTA0100.M3_MARDETAIL \n"
					+ "WHERE MLCONO = '" + cono + "' \n"
					+ "AND MLDIVI = '" + divi + "' \n"
					+ "AND MLPREF || '-' || MLORNO = '" + marno + "') \n"
					+ ", '" + item + "' \n"
					+ ", '" + name + "' \n"
					+ ", '" + loc + "' \n"
					+ ", '" + lotno + "' \n"
					+ ", '" + date + "' \n"
					+ ", '" + unit + "' \n"
					+ ", " + qty + " \n"
					+ ", " + price + " \n"
					+ ", '" + remark1 + "' \n"
					+ ", '" + remark2 + "' \n"
					+ ", CURRENT DATE \n"
					+ ", CURRENT TIME \n"
					+ ", '" + status + "' \n"
					+ ", '" + username + "')";
			// System.out.println("addADRHead\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Insert complete.");
			logger.info("Insert complete.");
			return mJsonObj.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}

	public static String addMARFile(HttpServletRequest httpServletRequest, String cono, String divi, String prefix,
			String marno, InputStream file, String name,
			String type, String remark1, String remark2, String username)
			throws Exception {
		logger.info("addMARDetail");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			String getMARNumber = marno.substring(2, 10);
			remark1 = ConvertString.convertApostrophe(remark1);
			remark2 = ConvertString.convertApostrophe(remark2);

			String getMaxLine = SelectData.getMaxMARFileLine(cono, divi, marno);

			String query = "INSERT INTO BRLDTA0100.M3_MARFILE \n"
					+ "(MFCONO, MFDIVI, MFPREF, MFORNO, MFLINE, MFNAME, MFTYPE, MFREM1, MFREM2, MFENDA, MFENTI, MFENUS) \n"
					+ "VALUES('" + cono + "' \n"
					+ ", '" + divi + "' \n"
					+ ", '" + prefix + "' \n"
					+ ", '" + getMARNumber + "' \n"
					+ ", '" + getMaxLine + "' \n"
					+ ", '" + name + "' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", '' \n"
					+ ", CURRENT DATE \n"
					+ ", CURRENT TIME \n"
					+ ", '" + username + "')";
			// System.out.println("addMARFile\n" + query);
			logger.debug(query);
			stmt.execute(query);
			
			String path = marno + "_" + getMaxLine + "_" + name;

			FileUtillity.writeToFileServerV2(httpServletRequest, file, name, path);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Insert file complete.");
			logger.info("Insert complete.");
			return mJsonObj.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}
	
	
	
	
	public static String addMonitoringReceipt(String rcno)
			throws Exception {
		logger.info("addMonitoringReceipt");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			String query = "INSERT INTO M3FDBTST.HR_RECEIPT (HR_CONO,HR_DIVI,HC_RCNO,HC_VCNO,HC_FIXNO)\r\n"
					+ "VALUES ('11','111','"+rcno.trim()+"','44','55'); ";
			 System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Insert file complete.");
			logger.info("Insert complete.");
			return mJsonObj.toString();

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				logger.error(e.getMessage());
			}

		}

		return null;

	}

	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static String addADRHead(String cono, String divi, String adrno, String date, String month, String type,
			String prefix, String boi, String bu, String costcenter, String vat, String accountant, String requestor,
			String remark1, String approve1, String approve2, String approve3, String approve4, String status,
			String submit, String username) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String getYear4 = month.substring(0, 4);
				String getYear2 = month.substring(2, 4);

				remark1 = ConvertString.convertApostrophe(remark1);

				String query = "INSERT INTO BRLDTA0100.M3_ADRHEAD \n"
						+ "(ADCONO, ADDIVI, ADORNO, ADDATE, ADMONT, ADTYPE, ADPREF, ADBOI, ADBU, ADCOCE, ADVAT, ADACCT, ADREQU, ADREM1, ADREM2, ADIMAG, ADAPP1, ADAPDA1, ADAPP2, ADAPDA2, ADAPP3, ADAPDA3, ADAPP4, ADAPDA4 \n"
						+ ", ADACRE1, ADAPRE1, ADAPRE2, ADAPRE3, ADAPRE4, ADENDA, ADENTI, ADSTAT, ADENUS) \n"
						+ "VALUES(" + cono + " \n"
						+ ", " + divi + " \n"
						+ ", (SELECT CASE WHEN CAST(MAX(ADORNO) AS DECIMAL(10,0)) > 0 THEN CAST(MAX(ADORNO) AS DECIMAL(10,0)) + 1 \n"
						+ "ELSE CAST(('" + getYear2 + "' || '000001') AS DECIMAL(10,0)) END AS ADORNO \n"
						+ "FROM BRLDTA0100.M3_ADRHEAD \n"
						+ "WHERE ADCONO = '" + 10 + "' \n"
						+ "AND ADDIVI = '" + divi + "' \n"
						+ "AND ADPREF = '" + prefix + "' \n"
						+ "AND SUBSTRING(CHAR(ADDATE,ISO),0,5) = '" + getYear4 + "') \n"
						+ ", '" + date + "' \n"
						+ ", '" + month + "' \n"
						+ ", '" + type + "' \n"
						+ ", '" + prefix + "' \n"
						+ ", '" + boi + "' \n"
						+ ", '" + bu + "' \n"
						+ ", '" + costcenter + "' \n"
						+ ", '" + vat + "' \n"
						+ ", '" + accountant + "' \n"
						+ ", '" + requestor + "' \n"
						+ ", '" + remark1 + "' \n"
						+ ", '' \n"
						+ ", '' \n"
						+ ", '" + approve1 + "' \n"
						+ ", NULL \n"
						+ ", '" + approve2 + "' \n"
						+ ", NULL \n"
						+ ", '" + approve3 + "' \n"
						+ ", NULL \n"
						+ ", '" + approve4 + "' \n"
						+ ", NULL \n"
						+ ", '' \n"
						+ ", '' \n"
						+ ", '' \n"
						+ ", '' \n"
						+ ", '' \n"
						+ ", CURRENT DATE \n"
						+ ", CURRENT TIME \n"
						+ ", '" + status + "' \n"
						+ ", '" + username + "')";
				// System.out.println("addADRHead\n" + query);
				stmt.execute(query);

				String getADRNumber = SelectData.getMaxADRNumber(cono, divi, prefix, getYear4, requestor);

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", getADRNumber);
				return mJsonObj.toString();

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addADRDetail(@Context HttpServletRequest httpServletRequest, String cono, String divi,
			String adrno, String prefix, String line, String itemno, String sbno, String costcenter, String date,
			String assetcost, String netvalue, String qty, String price, String remark, InputStream imagefile,
			String imagename, String status, String submit, String username) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String getADRNumber = adrno.substring(2, 10);
				remark = ConvertString.convertApostrophe(remark);

				String getMaxADRLine = SelectData.getMaxADRLine(cono, divi, adrno);
				String setImageName = adrno + "_" + getMaxADRLine + ".png";

				String query = "INSERT INTO BRLDTA0100.M3_ADRDETAIL \n"
						+ "(ALCONO, ALDIVI, ALORNO, ALPREF, ALLINE, ALITNO, ALITDE, ALSBNO, ALCOST, ALDATE, ALACOS, ALNETV, ALQTY, ALPRIC, ALREM1, ALREM2, ALIMAG, ALENDA, ALENTI, ALSTAT, ALENUS) \n"
						+ "VALUES('" + cono + "' \n"
						+ ", '" + divi + "' \n"
						+ ", '" + getADRNumber + "' \n"
						+ ", '" + prefix + "' \n"
						// + ", (SELECT COALESCE(MAX(ALLINE),0) + 1 AS ALLINE \n"
						// + "FROM BRLDTA0100.M3_ADRDETAIL \n"
						// + "WHERE ALCONO = '"+cono+"' \n"
						// + "AND ALDIVI = '"+divi+"' \n"
						// + "AND ALPREF || '-' || ALORNO = '"+adrno+"') \n"
						+ ", '" + getMaxADRLine + "' \n"
						+ ", '" + itemno + "' \n"
						+ ", '' \n"
						+ ", '" + sbno + "' \n"
						+ ", '" + costcenter + "' \n"
						+ ", '" + date + "' \n"
						+ ", '" + assetcost + "' \n"
						+ ", '" + netvalue + "' \n"
						+ ", '" + qty + "' \n"
						+ ", '" + price + "' \n"
						+ ", '" + remark + "' \n"
						+ ", '' \n"
						+ ", '" + setImageName + "' \n"
						+ ", CURRENT DATE \n"
						+ ", CURRENT TIME \n"
						+ ", '" + status + "' \n"
						+ ", '" + username + "')";
				// System.out.println("addADRDetail\n" + query);
				stmt.execute(query);

				if (imagename != null) {
					String uploadFilePath = null;
					// String filePath = httpServletRequest.getRealPath("/") + "WEB-INF\\image\\";
					String filePath = "D:\\files\\api_project\\adr_images\\";
					// System.out.println("filePath: " + filePath + setImageName);
					uploadFilePath = FileUtillity.writeToFileServer(imagefile, setImageName, filePath);

				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", "complete");
				return mJsonObj.toString();

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addOrderHead(String cono, String divi, String customerno, String orderdate, String delidate,
			String round, String pricelist, String ordertype, String whs, String status, String type, String remark,
			String day, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String getYear = delidate.substring(2, 4);
				String getOrderNumber = SelectData.getMaxOrderNumber(cono, divi, getYear);
				if (Integer.valueOf(getOrderNumber) == 0) {// Create new ordernumber
					getOrderNumber = getYear + "00000001";

				}

				String saleSupport = SelectData.getSalesuport(cono, divi, userid);
				// System.out.println(getOrderNumber + " : " + getOrderNumber);

				String query = "INSERT INTO " + DBNAME + ".M3_TAKEORDERHEAD \n" +
						"(THCONO, THDIVI, THORNO, THORTY, THWHLO, THCOCE, THORDA, THDEDA, THCUNO, THROUN, THPRIC, THCHAN, THNCHA, THGROU, THAREA, THSANO, THSASU, THREM1, THREM2, THSTAS, THENDA, THENTI, THENUS) \n"
						+
						"VALUES('" + cono + "' \n" +
						", '" + divi + "' \n" +
						", '" + getOrderNumber + "' \n" +
						", '" + ordertype + "' \n" +
						", '" + whs + "' \n" +
						", '' \n" +
						", '" + orderdate + "' \n" +
						", '" + delidate + "' \n" +
						", '" + customerno + "' \n" +
						", '" + round + "' \n" +
						", '" + pricelist + "' \n" +
						", '' \n" +
						", '' \n" +
						", '' \n" +
						", '' \n" +
						", '" + userid + "' \n" +
						", '" + saleSupport + "' \n" +
						", '" + remark + "' \n" +
						", '' \n" +
						", '" + status + "' \n" +
						", CURRENT DATE \n" +
						", CURRENT TIME \n" +
						", '" + userid + "')";
				// System.out.println("addOrderHead\n" + query);
				stmt.execute(query);

				if (type.equals("create")) {
					addItemDetailHistory(cono, divi, getOrderNumber, customerno, day, userid);
				} else {
					addItemDetailCustomer(cono, divi, getOrderNumber, customerno, userid);
				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", getOrderNumber);
				return mJsonObj.toString();

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addOrderHeadV2(String cono, String divi, String customerno, String orderdate, String delidate,
			String round, String pricelist, String ordertype, String whs, String status, String type, String remark,
			String pono, String day, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String getYear = delidate.substring(2, 4);
				String saleSupport = SelectData.getSalesuport(cono, divi, userid);

				// String getOrderNumber = SelectData.getMaxOrderNumber(cono, divi, getYear);
				// if (Integer.valueOf(getOrderNumber) == 0) {// Create new ordernumber
				// getOrderNumber = getYear + "00000001";
				//
				// }

				remark = ConvertString.convertApostrophe(remark);
				pono = ConvertString.convertApostrophe(pono);

				String query = "INSERT INTO " + DBNAME + ".M3_TAKEORDERHEAD \n" +
						"(THCONO, THDIVI, THORNO, THORTY, THWHLO, THCOCE, THORDA, THDEDA, THCUNO, THROUN, THPRIC, THCHAN, THNCHA, THGROU, THAREA, THSANO, THSASU, THREM1, THREM2, THSTAS, THENDA, THENTI, THENUS) \n"
						+
						"VALUES('" + cono + "' \n" +
						", '" + divi + "' \n" +
						", (SELECT CASE WHEN CAST(MAX(THORNO) AS DECIMAL(10,0)) > 0 THEN CAST(MAX(THORNO) AS DECIMAL(10,0)) + 1 ELSE CAST(('"
						+ getYear + "' || '00000001') AS DECIMAL(10,0)) END AS THORNO \n" +
						"FROM " + DBNAME + ".M3_TAKEORDERHEAD \n" +
						"WHERE THCONO = '" + cono + "' \n" +
						"AND THDIVI = '" + divi + "'  \n" +
						"AND SUBSTRING(THORNO,0,3) = '" + getYear + "') \n" +
						", '" + ordertype + "' \n" +
						", '" + whs + "' \n" +
						", '' \n" +
						", '" + orderdate + "' \n" +
						", '" + delidate + "' \n" +
						", '" + customerno + "' \n" +
						", '" + round + "' \n" +
						", '" + pricelist + "' \n" +
						", '' \n" +
						", '' \n" +
						", '' \n" +
						", '' \n" +
						", '" + userid + "' \n" +
						", '" + saleSupport + "' \n" +
						", '" + remark + "' \n" +
						", '" + pono + "' \n" +
						", '" + status + "' \n" +
						", CURRENT DATE \n" +
						", CURRENT TIME \n" +
						", '" + userid + "')";
				// System.out.println("addOrderHeadV2\n" + query);
				stmt.execute(query);

				String getTakeOrderNumber = SelectData.getMaxOrderNumberV3(cono, divi, getYear, userid);
				String getOrderNumber = getTakeOrderNumber.toLowerCase().split(":")[0];

				if (type.equals("create")) {
					addItemDetailHistoryV4(cono, divi, getOrderNumber, customerno, day, userid);
				} else {
					addItemDetailCustomerV4(cono, divi, getOrderNumber, customerno, userid);
				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", getTakeOrderNumber);
				return mJsonObj.toString();

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailSupport(String cono, String divi, String orderno, String customerno, String line,
			String itemno, String desc1, String desc2, String qty, String unit, String remark, String status,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT \n" +
						"(TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+
						"VALUES('" + cono + "' \n" +
						", '" + divi + "' \n" +
						", '" + orderno + "' \n" +
						", '' \n" +
						", '' \n" +
						", (SELECT COALESCE(MAX(TDLINE),0) + 1 \n" +
						"FROM " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT \n" +
						"WHERE TDCONO = '" + cono + "' \n" +
						"AND TDDIVI = '" + divi + "' \n" +
						"AND TDORNO = '" + orderno + "') \n" +
						", '" + itemno + "' \n" +
						", '" + qty + "' \n" +
						", '" + unit + "' \n" +
						", '" + remark + "' \n" +
						", '' \n" +
						", '" + status + "' \n" +
						", CURRENT DATE \n" +
						", CURRENT TIME \n" +
						", '" + userid + "')";
				// System.out.println("addItemDetail\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailToSupport(String cono, String divi, String orderno) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT \n" +
						"(TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS ) \n"
						+
						"SELECT TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS  \n"
						+
						"FROM " + DBNAME + ".M3_TAKEORDERDETAIL \n" +
						"WHERE TDCONO = '" + cono + "' \n" +
						"AND TDDIVI = '" + divi + "' \n" +
						"AND TDORNO = '" + orderno + "'  \n" +
						"AND TDIQTY > 0";
				// System.out.println("addItemDetailSupport\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailHistoryV2(String cono, String divi, String orderno, String customerno, String day,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, MMUNMS, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" +
						"FROM  \n" +
						"(SELECT A.OACONO,A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS \n"
						+
						"FROM \n" +
						"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT \n"
						+
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE," + DBM3NAME + ".OCUSMA," + DBM3NAME
						+ ".MITMAS," + DBM3NAME + ".CSYTAB," + DBM3NAME + ".ODHEAD \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OACONO = UACONO \n" +
						"AND OAORNO = UAORNO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" +
						"AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2) || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - "
						+ day + " DAYS AND CURRENT_DATE \n" +
						"AND OACUNO = '" + customerno + "' \n" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO = 'SMCD') AS  A \n" +
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS) AS A \n"
						+
						"LEFT JOIN  \n" +
						"(SELECT MMCONO, MMITNO, CASE WHEN COALESCE(MUAUS2,0) = '1' THEN MUALUN ELSE MMPUUN END AS MMUNMS \n"
						+
						"FROM \n" +
						"(SELECT MMCONO, MMITNO,CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMPUUN \n" +
						"FROM " + DBM3NAME + ".MITMAS \n" +
						"WHERE MMCONO = '" + cono + "' \n" +
						"AND MMSTAT = '20'  \n" +
						"AND SUBSTRING(MMITNO,0,2) BETWEEN 'A' AND 'Z') AS A \n" +
						"LEFT JOIN \n" +
						"(SELECT DISTINCT MUCONO,MUITNO,MUALUN,MUCOFA,MUDMCF,MUAUS2 \n" +
						"FROM " + DBM3NAME + ".MITAUN \n" +
						"WHERE MUAUS2 = '1') AS B \n" +
						"ON B.MUCONO = A.MMCONO \n" +
						"AND B.MUITNO = A.MMITNO) AS B \n" +
						"ON B.MMCONO = A.OACONO \n" +
						"AND B.MMITNO = A.OBITNO \n" +
						"GROUP BY OBITNO,MMUNMS";
				// System.out.println("addItemDetailHistoryV2\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailHistoryV3(String cono, String divi, String orderno, String customerno, String day,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, OBALUN, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" +
						"FROM \n" +
						"(SELECT A.OACONO,A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS,A.OBALUN \n"
						+
						"FROM \n" +
						"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT,OBORST \n"
						+
						"FROM " + DBM3NAME + ".OOHEAD a," + DBM3NAME + ".OOLINE b," + DBM3NAME + ".OCUSMA c," + DBM3NAME
						+ ".MITMAS d," + DBM3NAME + ".CSYTAB e," + DBM3NAME + ".ODHEAD f \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OACONO = UACONO \n" +
						"AND OAORNO = UAORNO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','A87','F31','F11') \n" +
						"AND OBORST IN ('27','77') \n" +
						"AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2) || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - "
						+ day + " DAYS AND CURRENT_DATE \n" +
						"AND OACUNO = '" + customerno + "' \n" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO = 'SMCD' \n" +
						"AND MMITTY IN ('FG','SP','RM')) AS  A \n" +
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, OBALUN) AS A \n"
						+
						"LEFT JOIN  \n" +
						"(SELECT MMCONO, MMITNO, CASE WHEN COALESCE(MUAUS2,0) = '1' THEN MUALUN ELSE MMPUUN END AS MMUNMS \n"
						+
						"FROM \n" +
						"(SELECT MMCONO, MMITNO,CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMPUUN \n" +
						"FROM " + DBM3NAME + ".MITMAS \n" +
						"WHERE MMCONO = '" + cono + "' \n" +
						"AND MMSTAT = '20'  \n" +
						"AND SUBSTRING(MMITNO,0,2) BETWEEN 'A' AND 'Z') AS A \n" +
						"LEFT JOIN \n" +
						"(SELECT DISTINCT MUCONO,MUITNO,MUALUN,MUCOFA,MUDMCF,MUAUS2 \n" +
						"FROM " + DBM3NAME + ".MITAUN \n" +
						"WHERE MUAUS2 = '1') AS B \n" +
						"ON B.MUCONO = A.MMCONO \n" +
						"AND B.MUITNO = A.MMITNO) AS B \n" +
						"ON B.MMCONO = A.OACONO \n" +
						"AND B.MMITNO = A.OBITNO \n" +
						// "INNER JOIN \n" +
						// "(SELECT MLCONO,MLITNO \n" +
						// "FROM "+DBM3NAME+".MITLOC \n" +
						// "GROUP BY MLCONO,MLITNO) AS C \n" +
						// "ON C.MLCONO = A.OACONO \n" +
						// "AND C.MLITNO = A.OBITNO \n" +
						"GROUP BY OBITNO,OBALUN,MMUNMS";
				// System.out.println("addItemDetailHistoryV3\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailHistoryV4(String cono, String divi, String orderno, String customerno, String day,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, OBALUN, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" +
						"FROM \n" +
						"(SELECT A.OACONO,A.CHANEL,A.CUSTGROUP,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS,A.OBALUN \n"
						+
						"FROM \n" +
						"(SELECT OACONO, CHANEL, CUSTGROUP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, OBALUN \n"
						+ "FROM \n"
						+ "(SELECT A.OACONO,A.CHANEL,A.CUSTGROUP,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS--,A.OBALUN \n"
						+ ", CASE WHEN MMFUDS LIKE '%เลือด%' THEN 'PCS' WHEN MMFUDS LIKE '%เป็ดเชอร์รี่%' THEN 'PCS' ELSE A.OBALUN END AS OBALUN \n"
						+ "FROM \n"
						+ "(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT,OBORST \n"
						+ "FROM " + DBM3NAME + ".OOHEAD a," + DBM3NAME + ".OOLINE b," + DBM3NAME + ".OCUSMA c,"
						+ DBM3NAME + ".MITMAS d," + DBM3NAME + ".CSYTAB e," + DBM3NAME + ".ODHEAD f \n"
						+ "WHERE OACONO = OBCONO \n"
						+ "AND OAORNO = OBORNO \n"
						+ "AND OACONO = '" + cono + "' \n"
						+ "AND OADIVI = '" + divi + "' \n"
						+ "AND OACUNO = OKCUNO \n"
						+ "AND OACONO = OKCONO \n"
						+ "AND OACONO = MMCONO \n"
						+ "AND OBITNO = MMITNO \n"
						+ "AND CTCONO = OACONO \n"
						+ "AND OACONO = UACONO \n"
						+ "AND OAORNO = UAORNO \n"
						+ "AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','A87','F31','F11') \n"
						+ "AND OBORST IN ('27','77','79') \n"
						+ "AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2) || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - "
						+ day + " DAYS AND CURRENT_DATE \n"
						+ "AND OACUNO = '" + customerno + "' \n"
						+ "AND CTSTKY = OASMCD \n"
						+ "AND CTSTCO = 'SMCD' \n"
						+ "AND MMITTY IN ('FG','SP','RM')) AS  A \n"
						+ "GROUP BY OACONO, CHANEL, CUSTGROUP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, OBALUN) AS AA \n"
						+ "GROUP BY OACONO, CHANEL, CUSTGROUP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, OBALUN) AS  A \n"
						+
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, OBALUN) AS A \n"
						+
						"LEFT JOIN  \n" +
						"(SELECT MMCONO, MMITNO, CASE WHEN COALESCE(MUAUS2,0) = '1' THEN MUALUN ELSE MMPUUN END AS MMUNMS \n"
						+
						"FROM \n" +
						"(SELECT MMCONO, MMITNO,CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMPUUN \n" +
						"FROM " + DBM3NAME + ".MITMAS \n" +
						"WHERE MMCONO = '" + cono + "' \n" +
						"AND MMSTAT = '20'  \n" +
						"AND SUBSTRING(MMITNO,0,2) BETWEEN 'A' AND 'Z') AS A \n" +
						"LEFT JOIN \n" +
						"(SELECT DISTINCT MUCONO,MUITNO,MUALUN,MUCOFA,MUDMCF,MUAUS2 \n" +
						"FROM " + DBM3NAME + ".MITAUN \n" +
						"WHERE MUAUS2 = '1') AS B \n" +
						"ON B.MUCONO = A.MMCONO \n" +
						"AND B.MUITNO = A.MMITNO) AS B \n" +
						"ON B.MMCONO = A.OACONO \n" +
						"AND B.MMITNO = A.OBITNO \n" +
						"GROUP BY OBITNO,OBALUN,MMUNMS";
				// System.out.println("addItemDetailHistoryV3\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailHistory(String cono, String divi, String orderno, String customerno, String day,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, UNIT, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" + // MAX(QTY) AS QTYUNIT
						"FROM  \n" +
						"(SELECT A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OACONO,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS,COALESCE(A.QTY,0) AS QTY,COALESCE(INVQTY,0) AS INVQTY,COALESCE(INVCAWE,0) AS INVCAWE,A.UNIT \n"
						+
						"FROM \n" +
						"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN UNIT,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT \n"
						+
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE," + DBM3NAME + ".OCUSMA," + DBM3NAME
						+ ".MITMAS," + DBM3NAME + ".CSYTAB," + DBM3NAME + ".ODHEAD \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OACONO = UACONO \n" +
						"AND OAORNO = UAORNO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" +
						"AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2) || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - "
						+ day + " DAYS AND CURRENT_DATE \n" +
						"AND OACUNO = '" + customerno + "' \n" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO ='SMCD') A  \n" +
						"LEFT JOIN \n" +
						"(SELECT SUM(MTTRQT) AS INVQTY,SUM(MTCAWE) AS INVCAWE,MTRIDN,MTCONO,MTITNO \n" +
						"FROM " + DBM3NAME + ".MITTRA \n" +
						"GROUP BY MTRIDN,MTCONO,MTITNO) B \n" +
						"ON A.OACONO = B.MTCONO \n" +
						"AND A.OBITNO = B.MTITNO \n" +
						"AND A.OAORNO = B.MTRIDN) AS detail \n" +
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, UNIT";
				// System.out.println("addItemDetailHistory\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailCustomerV2(String cono, String divi, String orderno, String customerno,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, MMUNMS, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" +
						"FROM  \n" +
						"(SELECT A.OACONO,A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS \n"
						+
						"FROM \n" +
						"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT \n"
						+
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE," + DBM3NAME + ".OCUSMA," + DBM3NAME
						+ ".MITMAS," + DBM3NAME + ".CSYTAB," + DBM3NAME + ".ODHEAD \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OACONO = UACONO \n" +
						"AND OAORNO = UAORNO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" +
						// "AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2)
						// || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - "+day+" DAYS
						// AND CURRENT_DATE \n" +
						"AND OACUNO = '" + customerno + "' \n" +
						"AND OAORNO = (SELECT MAX(OAORNO) \n" +
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" +
						"AND OACUNO = '" + customerno + "')" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO = 'SMCD' \n" +
						"AND MMITTY IN ('FG','SP','RM')) AS A \n" +
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS) AS A \n"
						+
						"LEFT JOIN  \n" +
						"(SELECT MMCONO, MMITNO, CASE WHEN COALESCE(MUAUS2,0) = '1' THEN MUALUN ELSE MMPUUN END AS MMUNMS \n"
						+
						"FROM \n" +
						"(SELECT MMCONO, MMITNO,CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMPUUN \n" +
						"FROM " + DBM3NAME + ".MITMAS \n" +
						"WHERE MMCONO = '10' \n" +
						"AND MMSTAT = '20'  \n" +
						// "AND MMITTY = 'FG' \n" +
						"AND SUBSTRING(MMITNO,0,2) BETWEEN 'A' AND 'Z') AS A \n" +
						"LEFT JOIN \n" +
						"(SELECT DISTINCT MUCONO,MUITNO,MUALUN,MUCOFA,MUDMCF,MUAUS2 \n" +
						"FROM " + DBM3NAME + ".MITAUN \n" +
						"WHERE MUAUS2 = '1') AS B \n" +
						"ON B.MUCONO = A.MMCONO \n" +
						"AND B.MUITNO = A.MMITNO) AS B \n" +
						"ON B.MMCONO = A.OACONO \n" +
						"AND B.MMITNO = A.OBITNO \n" +
						"GROUP BY OBITNO,MMUNMS";
				// System.out.println("addItemDetailCustomerV2\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailCustomerV3(String cono, String divi, String orderno, String customerno,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, MMUNMS, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" +
						"FROM  \n" +
						"(SELECT A.OACONO,A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS \n"
						+
						"FROM \n" +
						"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN,OBSAPR PRICE,OBLNAM \n"
						+
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE," + DBM3NAME + ".OCUSMA," + DBM3NAME
						+ ".MITMAS," + DBM3NAME + ".CSYTAB \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','A87','F31','F11') \n" +
						"AND OAORNO = (SELECT MAX(OAORNO) \n" +
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" +
						"AND OACUNO = '" + customerno + "')" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO = 'SMCD' \n" +
						"AND MMITTY IN ('FG','SP','RM')) AS A \n" +
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS) AS A \n"
						+
						"LEFT JOIN  \n" +
						"(SELECT MMCONO, MMITNO, CASE WHEN COALESCE(MUAUS2,0) = '1' THEN MUALUN ELSE MMPUUN END AS MMUNMS \n"
						+
						"FROM \n" +
						"(SELECT MMCONO, MMITNO,CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMPUUN \n" +
						"FROM " + DBM3NAME + ".MITMAS \n" +
						"WHERE MMCONO = '10' \n" +
						"AND MMSTAT = '20'  \n" +
						// "AND MMITTY = 'FG' \n" +
						"AND SUBSTRING(MMITNO,0,2) BETWEEN 'A' AND 'Z') AS A \n" +
						"LEFT JOIN \n" +
						"(SELECT DISTINCT MUCONO,MUITNO,MUALUN,MUCOFA,MUDMCF,MUAUS2 \n" +
						"FROM " + DBM3NAME + ".MITAUN \n" +
						"WHERE MUAUS2 = '1') AS B \n" +
						"ON B.MUCONO = A.MMCONO \n" +
						"AND B.MUITNO = A.MMITNO) AS B \n" +
						"ON B.MMCONO = A.OACONO \n" +
						"AND B.MMITNO = A.OBITNO \n" +
						"GROUP BY OBITNO,MMUNMS";
				// System.out.println("addItemDetailCustomerV2\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailCustomerV4(String cono, String divi, String orderno, String customerno,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, MMUNMS, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" +
						"FROM  \n" +
						"(SELECT A.OACONO,A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS \n"
						+
						"FROM \n" +
						"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN,OBSAPR PRICE,OBLNAM \n"
						+
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE," + DBM3NAME + ".OCUSMA," + DBM3NAME
						+ ".MITMAS," + DBM3NAME + ".CSYTAB \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','A87','F31','F11') \n" +
						"AND OAORNO = (SELECT MAX(OAORNO) AS OAORNO \n"
						+ "FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE \n"
						+ "WHERE OACONO = OBCONO \n"
						+ "AND OAORNO = OBORNO \n"
						+ "AND OACONO = '" + cono + "' \n"
						+ "AND OADIVI = '" + divi + "' \n"
						+ "AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','A87','F31','F11') \n"
						+ "AND OACUNO = '" + customerno + "' \n"
						+ "AND OAORDT = (SELECT MAX(OAORDT) AS OAORDT \n"
						+ "FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE \n"
						+ "WHERE OACONO = OBCONO \n"
						+ "AND OAORNO = OBORNO \n"
						+ "AND OACONO = '" + cono + "' \n"
						+ "AND OADIVI = '" + divi + "' \n"
						+ "AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','A87','F31','F11') \n"
						+ "AND OACUNO = '" + customerno + "' \n"
						+ "AND OBORST IN ('27','77','79'))) \n" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO = 'SMCD' \n" +
						"AND MMITTY IN ('FG','SP','RM')) AS A \n" +
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS) AS A \n"
						+
						"LEFT JOIN  \n" +
						"(SELECT MMCONO, MMITNO, CASE WHEN COALESCE(MUAUS2,0) = '1' THEN MUALUN ELSE MMPUUN END AS MMUNMS \n"
						+
						"FROM \n" +
						"(SELECT MMCONO, MMITNO,CASE WHEN MMACTI = '2' THEN 'PCS' ELSE MMPUUN END AS MMPUUN \n" +
						"FROM " + DBM3NAME + ".MITMAS \n" +
						"WHERE MMCONO = '10' \n" +
						"AND MMSTAT = '20'  \n" +
						// "AND MMITTY = 'FG' \n" +
						"AND SUBSTRING(MMITNO,0,2) BETWEEN 'A' AND 'Z') AS A \n" +
						"LEFT JOIN \n" +
						"(SELECT DISTINCT MUCONO,MUITNO,MUALUN,MUCOFA,MUDMCF,MUAUS2 \n" +
						"FROM " + DBM3NAME + ".MITAUN \n" +
						"WHERE MUAUS2 = '1') AS B \n" +
						"ON B.MUCONO = A.MMCONO \n" +
						"AND B.MUITNO = A.MMITNO) AS B \n" +
						"ON B.MMCONO = A.OACONO \n" +
						"AND B.MMITNO = A.OBITNO \n" +
						"GROUP BY OBITNO,MMUNMS";
				// System.out.println("addItemDetailCustomerV4\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addItemDetailCustomer(String cono, String divi, String orderno, String customerno,
			String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO " + DBNAME
						+ ".M3_TAKEORDERDETAIL (TDCONO, TDDIVI, TDORNO, TDCONU, TDIVNO, TDLINE, TDITNO, TDIQTY, TDUNIT, TDREM1, TDREM2, TDSTAS, TDENDA, TDENTI, TDENUS) \n"
						+ "SELECT '" + cono + "', '" + divi + "', '" + orderno
						+ "','','',ROW_NUMBER() OVER(ORDER BY OBITNO) AS LINE, OBITNO, 0 AS QTYUNIT, UNIT, '', '', '00', CURRENT DATE, CURRENT TIME, '"
						+ userid + "' \n" + // MAX(QTY) AS QTYUNIT
						"FROM  \n" +
						"(SELECT A.CHANEL,A.CUSTGROUP,A.OAORTP,A.OACONO,A.OASMCD,A.CTTX40,A.OACUNO,A.OKCUNM,A.OBITNO,A.MMFUDS,COALESCE(A.QTY,0) AS QTY,COALESCE(INVQTY,0) AS INVQTY,COALESCE(INVCAWE,0) AS INVCAWE,A.UNIT \n"
						+
						"FROM \n" +
						"(SELECT OACONO,OKECAR AS CHANEL,OKCUCL AS CUSTGROUP,OAORTP,OAORNO,OAORDT,CTTX40,OASMCD,OACUNO,OKCUNM,OBITNO,MMFUDS,OBORQA QTY,OBALUN UNIT,OBSAPR PRICE,OBLNAM,UAIVNO,UAIVDT \n"
						+
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE," + DBM3NAME + ".OCUSMA," + DBM3NAME
						+ ".MITMAS," + DBM3NAME + ".CSYTAB," + DBM3NAME + ".ODHEAD \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OACONO = UACONO \n" +
						"AND OAORNO = UAORNO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" +
						// "AND DATE(SUBSTRING(CHAR(OAORDT),1,4) || '-' || SUBSTRING(CHAR(OAORDT),5,2)
						// || '-' || SUBSTRING(CHAR(OAORDT),7,2)) BETWEEN CURRENT_DATE - 7 DAYS AND
						// CURRENT_DATE \n" +
						"AND OACUNO = '" + customerno + "' \n" +
						"AND OAORNO = (SELECT MAX(OAORNO) \n" +
						"FROM " + DBM3NAME + ".OOHEAD," + DBM3NAME + ".OOLINE," + DBM3NAME + ".OCUSMA," + DBM3NAME
						+ ".MITMAS," + DBM3NAME + ".CSYTAB," + DBM3NAME + ".ODHEAD \n" +
						"WHERE OACONO = OBCONO \n" +
						"AND OAORNO = OBORNO \n" +
						"AND OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						"AND OACUNO = OKCUNO \n" +
						"AND OACONO = OKCONO \n" +
						"AND OACONO = MMCONO \n" +
						"AND OBITNO = MMITNO \n" +
						"AND CTCONO = OACONO \n" +
						"AND OACONO = UACONO \n" +
						"AND OAORNO = UAORNO \n" +
						"AND OAWHLO IN ('A11','A80','A81','A82','A83','A84','F31','F11') \n" +
						"AND OACUNO = '" + customerno + "' \n" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO ='SMCD') \n" +
						"AND CTSTKY = OASMCD \n" +
						"AND CTSTCO ='SMCD' \n" +
						"AND AND MMITTY = 'FG') A  \n" +
						"LEFT JOIN \n" +
						"(SELECT SUM(MTTRQT) AS INVQTY,SUM(MTCAWE) AS INVCAWE,MTRIDN,MTCONO,MTITNO \n" +
						"FROM " + DBM3NAME + ".MITTRA \n" +
						"GROUP BY MTRIDN,MTCONO,MTITNO) B \n" +
						"ON A.OACONO = B.MTCONO \n" +
						"AND A.OBITNO = B.MTITNO \n" +
						"AND A.OAORNO = B.MTRIDN) AS detail \n" +
						"GROUP BY OACONO, CHANEL, CUSTGROUP, OAORTP, OASMCD, CTTX40, OACUNO, OKCUNM, OBITNO, MMFUDS, UNIT";
				// System.out.println("addItemDetailCustomer\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

	public static String addUser(String cono, String divi, String salesup, String saleman, String channel)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();
		Connection conn = ConnectDB2.doConnect();
		String getSalemanDuplicate = null;
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				getSalemanDuplicate = SelectData.getSalesuport(cono, divi, saleman);
				if (getSalemanDuplicate.equals("n/a")) {

					String query = "INSERT INTO BRLDTA0100.SAL_SUPSALE \n" +
							"(S_CONO, S_SUPP, S_SALE, S_CHAN) \n" +
							"VALUES ('" + cono + "' \n" +
							", '" + salesup + "' \n" +
							", '" + saleman + "' \n" +
							", '" + channel + "')";
					// System.out.println("addGroupingUser\n" + query);
					stmt.execute(query);

					mJsonObj.put("result", "ok");
					mJsonObj.put("message", "Complete.");
					return mJsonObj.toString();
				}

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		mJsonObj.put("result", "nok");
		mJsonObj.put("message", "Already " + saleman + " belong to " + getSalemanDuplicate);
		return mJsonObj.toString();

	}

	public static String addLogSendEmail(String EDDOCUMENT, String EDREFNO, String ESENTTO, String ESENTCC,
			String ESENTFROM, String EDSUBJECT, String EDDETAIL, String EDSTATUSNO, String CREATEBY, String ECONO,
			String EDIVISION) throws Exception {

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				String query = "INSERT INTO BRLDTA0100.M3_STORAGEEMAILSEND \n"
						+ "(EDDOCUMENT, EDREFNO, ESENTTO, ESENTCC, ESENTFROM, EDSUBJECT, EDDETAIL, EDSTATUSNO, CREATEBY, ECONO, EDIVISION, SENTDATE, SENTTIME) \n"
						+ "VALUES ('" + EDDOCUMENT + "', '" + EDREFNO + "', '" + ESENTTO + "', '" + ESENTCC + "', '"
						+ ESENTFROM + "' \n" + ", '" + EDSUBJECT + "', '" + EDDETAIL + "', '" + EDSTATUSNO + "', '"
						+ CREATEBY + "', '" + ECONO + "', '" + EDIVISION + "', CURRENT DATE, CURRENT TIME) ";
				// System.out.println("addLogSendEmail\n" + query);
				stmt.execute(query);

			} else {
				System.err.println("Server can't connect.");
			}

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				conn.close();
			}
			throw e;
		} finally {
			if (conn != null) {
				conn.close();
			}
		}

		return null;

	}

}
