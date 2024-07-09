package com.br.data;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.br.connection.ConnectDB2;
import com.br.utility.Constant;
import com.br.utility.ConvertString;
import com.br.utility.FileUtillity;
import com.br.utility.SendEmail;

public class UpdateData {

	private static final Logger logger = LogManager.getLogger(UpdateData.class);

	protected static String DBNAME = Constant.DBNAME;
	protected static String DBM3NAME = Constant.DBM3NAME;

	static DecimalFormat df0 = new DecimalFormat("0");
	static DecimalFormat df2 = new DecimalFormat("#.##");
	static DecimalFormat df3 = new DecimalFormat("#.###");
	static DecimalFormat df4 = new DecimalFormat("#.####");

	public static String updateMARHead(String cono, String divi, String marno, String date, String postdate,
			String month, String type, String prefix, String bu, String costcenter, String accountant, String requestor,
			String remark, String purpose, String approve1, String approve2, String approve3, String approve4,
			String approve5, String acknoict, String acknocio, String status, String submit, String username)
			throws Exception {
		logger.info("updateMARHead");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			String getYear4 = month.substring(0, 4);
			String getYear2 = month.substring(2, 4);
			remark = ConvertString.convertApostrophe(remark);

			String query = "UPDATE BRLDTA0100.M3_MARHEAD \n"
					+ "SET MHTYPE = '" + type + "' \n"
					+ ", MHBU = '" + bu + "' \n"
					+ ", MHCOCE = '" + costcenter + "' \n"
					+ ", MHACCT = '" + accountant + "' \n"
					+ ", MHAPP1 = '" + approve1 + "' \n"
					+ ", MHAPP2 = '" + approve2 + "' \n"
					+ ", MHAPP3 = '" + approve3 + "' \n"
					+ ", MHAPP4 = '" + approve4 + "' \n"
					+ ", MHAPP5 = '" + approve5 + "' \n"
					+ ", MHREM1 = '" + remark + "' \n"
					+ ", MHREM2 = '" + purpose + "' \n"
//					+ ", MHACCRE = '" +  + "' \n"
//					+ ", MHAPRE1 = '" +  + "' \n"
//					+ ", MHAPRE2 = '" +  + "' \n"
//					+ ", MHAPRE3 = '" +  + "' \n"
//					+ ", MHAPRE4 = '" +  + "' \n"
//					+ ", MHAPRE5 = '" +  + "' \n"
//					+ ", MHICTRE = '" +  + "' \n"
//					+ ", MHCIORE = '" +  + "' \n"
					+ ", MHENDA = CURRENT DATE \n"
					+ ", MHENTI = CURRENT TIME \n"
					+ ", MHENUS = '" + username + "' \n"
					+ "WHERE MHCONO = '" + cono + "' \n"
					+ "AND MHDIVI = '" + divi + "' \n"
					+ "AND MHPREF || '-' || MHORNO = '" + marno + "'";
			// System.out.println("updateMARHead\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	

	
	
	public static String updateemp(String vID, String vEmployeedialog , String vEmail ,String getLocation,String vRemark)
			throws Exception {
		logger.info("updateSWRfile");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

		
			String query = "　UPDATE  BRLDTABK01.VISITOR_HEAD\r\n"
					+ "　SET H_COMPANYNAME2 = '"+vEmployeedialog.trim()+"' , H_STATUS = '15' , H_Email = '"+vEmail.trim()+"', H_REMARK1 = '"+vRemark.trim()+"' \r\n"
					+ "　WHERE H_ID  =  '"+vID.trim()+"' AND H_LOCATION = '"+getLocation.trim()+"'";
			 System.out.println("updateEmployee\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	
	
	
	
	public static String updateEmail(String ordno)
			throws Exception {
		logger.info("updateEmail");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

		
			String query = "　UPDATE  BRLDTABK01.VISITOR_HEAD\r\n"
					+ "　SET H_STATUS = '20' \r\n"
					+ "　WHERE H_ID  =  '"+ordno.trim()+"'";
			 System.out.println("updateEmail\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	
	
	public static String updateroomcard(String vID, String vRoom, String vCard, String getLocation)
			throws Exception {
		logger.info("updateSWRfile");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

		
			String query = "　UPDATE  BRLDTABK01.VISITOR_HEAD\r\n"
					+ "　SET H_ROOMNO = '"+vRoom.trim()+"' , H_CARDNO = '"+vCard.trim()+"' \r\n"
					+ "　WHERE H_ID  =  '"+vID.trim()+"' AND H_LOCATION = '"+getLocation.trim()+"'"	;	

			
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	
	
	
	public static String updatefollower(String vID, String H_SURNAME, String oldH_SURNAME,String vCONO,String vDIVI,String vLocaton)
			throws Exception {
		logger.info("updateSWRfile");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();


			String query = "　UPDATE  BRLDTABK01.FOLLOWER_HEAD\r\n"
					+ "　SET H_SURNAME = '"+H_SURNAME+"' \r\n"
					+ "　WHERE F_ID  =  '"+vID.trim()+"' AND H_SURNAME = '"+oldH_SURNAME.trim()+"' AND  H_CONO = '"+vCONO.trim()+"' AND H_DIVI = '"+vDIVI.trim()+"' AND H_LOCATION = '"+vLocaton.trim()+"' ";
			 System.out.println("updateSWRfile\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	
	
	public static String checkout1(String vID, String sts, String getLocation ,String room)
			throws Exception {
		logger.info("updateSWRfile");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();


			String query = "　UPDATE  BRLDTABK01.VISITOR_HEAD\r\n"
					+ "　SET H_STATUS = '"+sts+"',  H_Timeout = VARCHAR_FORMAT(CURRENT TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS') \r\n"
					+ "　WHERE H_ID  =  '"+vID+"' AND H_LOCATION = '"+getLocation.trim()+"'";
			 System.out.println("updateSWRfile\n" + query);
			 
			 String query2 = "　UPDATE  BRLDTABK01.FOLLOWER_HEAD\r\n"
						+ "　SET H_STATUS = '"+sts+"' \r\n"
						+ "　WHERE F_ID  =  '"+vID+"' AND H_LOCATION = '"+getLocation.trim()+"'";
				 System.out.println("updateSWRfile\n" + query);
				 
	
					 
					 String query3 = "　UPDATE  BRLDTABK01.VISITOR_HEAD\r\n"
								+ "　SET H_ROOMNO = '"+room+"' \r\n"
								+ "　WHERE H_ID  =  '"+vID+"' AND H_LOCATION = '"+getLocation.trim()+"'";
					
					
				
				 
				 
			logger.debug(query);
			stmt.execute(query);
			
			logger.debug(query2);
			stmt.execute(query2);
			
			 
			 logger.debug(query3);
				stmt.execute(query3);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	public static String checkout(String vID, String sts, String getLocation)
			throws Exception {
		logger.info("updateSWRfile");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();


			String query = "　UPDATE  BRLDTABK01.VISITOR_HEAD\r\n"
					+ "　SET H_STATUS = '"+sts+"',  H_Timeout = VARCHAR_FORMAT(CURRENT TIMESTAMP, 'YYYY-MM-DD HH24:MI:SS') \r\n"
					+ "　WHERE H_ID  =  '"+vID+"' AND H_LOCATION = '"+getLocation.trim()+"'";
			 System.out.println("updateSWRfile\n" + query);
			 
			 String query2 = "　UPDATE  BRLDTABK01.FOLLOWER_HEAD\r\n"
						+ "　SET H_STATUS = '"+sts+"' \r\n"
						+ "　WHERE F_ID  =  '"+vID+"' AND H_LOCATION = '"+getLocation.trim()+"'";
				 System.out.println("updateSWRfile\n" + query);
				 
				 
			
				 
				 
			logger.debug(query);
			stmt.execute(query);
			
			logger.debug(query2);
			stmt.execute(query2);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	
	
	
	public static String addvisitorheader(String vID, String vIMG, String vTimein,
			String vTimeout, String vLicense, String vName, String vSurname,
			String vTel, String vReason, String vEmployee, 
			String vStatus,String vCono,String vCompany,String vMeetdate,String vMeettime, String vMail,
			InputStream imagefile , String imagename , String vCONO , String  vDIVI , String vLocation)
			throws Exception {
		logger.info("updateSWRfile");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			String setImageName = "visitor" + "_" + vID + ".png";


			String query = "　UPDATE  BRLDTABK01.VISITOR_HEAD\r\n"
					+ "　SET H_LICENSE = '"+vLicense+"' , H_NAME = '"+vName+"', H_SURNAME = '"+vSurname+"' , H_TEL = '"+vTel+"', \r\n"
					+ "　H_REASON = '"+vReason+"', H_COMPANYNAME2 = '"+vEmployee+"',H_STATUS = '"+vStatus+"' ,H_REMARK1 = '"+vCono+"' , H_COMPANYNAME= '"+vCompany+"' , H_MEETDATE = '"+vMeetdate+"' , H_MEETTIME = '"+vMeettime+"', H_IMG ='"+setImageName+"'\r\n"
					+ "　WHERE H_ID  =  '"+vID+"' AND H_CONO = '"+vCONO.trim()+"' AND H_DIVI = '"+vDIVI.trim()+"' AND H_LOCATION = '"+vLocation.trim()+"' ";
			 System.out.println("updateSWRfile\n" + query);
			logger.debug(query);
			stmt.execute(query);
			
			String query2 = "　UPDATE  BRLDTABK01.FOLLOWER_HEAD\r\n"
					+ "　SET H_STATUS = '10' \r\n"
					+ "　WHERE F_ID  =  '"+vID+"'";
			 System.out.println("updateSWRfile\n" + query);
			logger.debug(query2);
			stmt.execute(query2);
			
			

			
			
			if (imagename != null) {
				String uploadFilePath = null;
				// String filePath = httpServletRequest.getRealPath("/") + "WEB-INF\\image\\";
				String filePath = "D:\\files\\api_project\\visitor_file\\";
				// System.out.println("filePath: " + filePath + setImageName);
				uploadFilePath = FileUtillity.writeToFileServer(imagefile, setImageName, filePath);

			}

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	

	public static String updateSWRFile(String oldSFORNO, String oldSFCONO, String oldSFDIVI,
			String newSFORNO, String newSFPREF, String newSFLINE, String newSFNAME,
			String newSFTYPE, String newSFREM1, String newSFREM2, 
			String getUsername)
			throws Exception {
		logger.info("updateSWRfile");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();


			String query = "UPDATE  BRLDTABK01.M3_SWRFILE\r\n"
					+ "			SET SFPREF = '"+newSFPREF+"' ,SFLINE = '"+newSFLINE+"',  SFNAME = '"+newSFNAME+"',SFTYPE ='"+newSFTYPE+"',SFREM1 = '"+newSFREM1+"' , SFREM2 = '"+newSFREM2+"' WHERE SFORNO = '"+oldSFORNO+"' AND SFCONO= '"+oldSFCONO+"' AND SFDIVI = '"+oldSFDIVI+"'";
			 System.out.println("updateSWRfile\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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
	
	public static String updateStatusMARHead(String cono, String divi, String marno, String status, String submit, String username)
			throws Exception {
		logger.info("updateStatusMARHead {}", submit);

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();


			String query = "UPDATE BRLDTA0100.M3_MARHEAD \n"
					+ "SET MHSTAT = '" + status + "' \n"
					+ ", MHENDA = CURRENT DATE \n"
					+ ", MHENTI = CURRENT TIME \n"
					+ ", MHENUS = '" + username + "' \n"
					+ "WHERE MHCONO = '" + cono + "' \n"
					+ "AND MHDIVI = '" + divi + "' \n"
					+ "AND MHPREF || '-' || MHORNO = '" + marno + "'";
			// System.out.println("updateMARHead\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");

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

	public static String updateMARDetail(String cono, String divi, String marno, String prefix, String refno,
			String typeadjust, String line, String item, String name, String fac, String whs, String loc,
			String lotno, String date, String unit, String qty, String price, String amt, String remark1,
			String remark2, String status, String username)
			throws Exception {
		logger.info("updateMARDetail");

		JSONObject mJsonObj = new JSONObject();
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = ConnectDB2.doConnect();
			stmt = conn.createStatement();

			remark1 = ConvertString.convertApostrophe(remark1);
			remark2 = ConvertString.convertApostrophe(remark2);

			String query = "UPDATE BRLDTA0100.M3_MARDETAIL \n"
					+ "SET MLREM1 = '" + remark1 + "' \n"
					+ ", MLREM2 = '" + remark2 + "' \n"
					+ ", MLENDA = CURRENT DATE \n"
					+ ", MLENTI = CURRENT TIME \n"
					+ ", MLENUS = '" + username + "' \n"
					+ "WHERE MLCONO = '" + cono + "' \n"
					+ "AND MLDIVI = '" + divi + "' \n"
					+ "AND MLLINE = '" + line + "' \n"
					+ "AND MLITNO = '" + item + "' \n"
					+ "AND MLPREF || '-' || MLORNO = '" + marno + "'";
			// System.out.println("updateMARDetail\n" + query);
			logger.debug(query);
			stmt.execute(query);

			mJsonObj.put("result", "ok");
			mJsonObj.put("message", "Update complete.");
			logger.info("Update complete.");
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

	public static String updateADRHead(String cono, String divi, String adrno, String date, String month, String type,
			String prefix, String boi, String bu, String costcenter, String vat, String accountant, String requestor,
			String remark1, String approve1, String approve2, String approve3, String approve4, String status,
			String submit, String username) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();

		remark1 = ConvertString.convertApostrophe(remark1);

		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE BRLDTA0100.M3_ADRHEAD \n"
						+ "SET ADTYPE = '" + type + "' \n"
						+ ", ADBOI = '" + boi + "' \n"
						+ ", ADBU = '" + bu + "' \n"
						+ ", ADCOCE = '" + costcenter + "' \n"
						+ ", ADACCT = '" + accountant + "' \n"
						+ ", ADREM1 = '" + remark1 + "' \n"
						+ ", ADENDA = CURRENT DATE \n"
						+ ", ADENTI = CURRENT TIME \n"
						+ ", ADENUS = '" + username + "' \n"
						+ "WHERE ADCONO = '" + cono + "' \n"
						+ "AND ADDIVI = '" + divi + "' \n"
						+ "AND ADPREF || '-' || ADORNO = '" + adrno + "'";
				// System.out.println("updateADRHead\n" + query);
				stmt.execute(query);

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", "Update complete.");
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

	public static String updateADRDetail(@Context HttpServletRequest httpServletRequest, String cono, String divi,
			String adrno, String prefix, String line, String itemno, String sbno, String costcenter, String date,
			String assetcost, String netvalue, String qty, String price, String remark, InputStream imagefile,
			String imagename, String status, String submit, String username) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();

		remark = ConvertString.convertApostrophe(remark);
		String setImageName = adrno + "_" + line + ".png";

		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE BRLDTA0100.M3_ADRDETAIL \n"
						+ "SET ALQTY = '" + qty + "' \n"
						+ ", ALPRIC = '" + price + "' \n"
						+ ", ALREM1 = '" + remark + "' \n"
						+ ", ALIMAG = '" + setImageName + "' \n"
						+ ", ALENDA = CURRENT DATE \n"
						+ ", ALENTI = CURRENT TIME \n"
						+ ", ALENUS = '" + username + "' \n"
						+ "WHERE ALCONO = '" + cono + "' \n"
						+ "AND ALDIVI = '" + divi + "' \n"
						+ "AND ALLINE = '" + line + "' \n"
						+ "AND ALITNO = '" + itemno + "' \n"
						+ "AND ALPREF || '-' || ALORNO = '" + adrno + "'";
				// System.out.println("updateADRDetail\n" + query);
				stmt.execute(query);

				// System.out.println("imagefile: " + imagefile);
				// System.out.println("imagename: " + imagename);

				if (imagename != null) {
					String uploadFilePath = null;
					// String filePath = httpServletRequest.getRealPath("/") + "WEB-INF\\image\\";
					String filePath = "D:\\files\\api_project\\adr_images\\";
					// System.out.println("filePath: " + filePath + setImageName);
					uploadFilePath = FileUtillity.writeToFileServer(imagefile, setImageName, filePath);

				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", "Update complete.");
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

	public static String updateADRImage(@Context HttpServletRequest httpServletRequest, String cono, String divi,
			String adrno, InputStream imagefile, String imagename, String username) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();

		String setImageName = adrno + ".png";

		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE BRLDTA0100.M3_ADRHEAD \n"
						+ "SET ADIMAG = '" + setImageName + "' \n"
						+ ", ADENDA = CURRENT DATE \n"
						+ ", ADENTI = CURRENT TIME \n"
						+ ", ADENUS = '" + username + "' \n"
						+ "WHERE ADCONO = '" + cono + "' \n"
						+ "AND ADDIVI = '" + divi + "' \n"
						+ "AND ADPREF || '-' || ADORNO = '" + adrno + "'";
				// System.out.println("updateADRImage\n" + query);
				stmt.execute(query);

				// System.out.println("imagefile: " + imagefile);
				// System.out.println("imagename: " + imagename);

				if (imagename != null) {
					String uploadFilePath = null;
					// String filePath = httpServletRequest.getRealPath("/") + "WEB-INF\\image\\";
					String filePath = "D:\\files\\api_project\\adr_images\\";
					// System.out.println("filePath: " + filePath + setImageName);
					uploadFilePath = FileUtillity.writeToFileServer(imagefile, setImageName, filePath);

				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", "Update complete.");
				return mJsonObj.toString();

			} else {
				System.err.println("Server can't connect.");
			}

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

	public static String updateStatusADRHead(@Context HttpServletRequest httpServletRequest, String cono, String divi,
			String adrno, String tostatus, String submit, String username)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				String setStatus = "", setDate = "";
				String getResultSendEmail;
				JSONObject mJResult = new JSONObject();

				if (tostatus.equals("10")) { // Accountant
					setStatus = "10";
					getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus,
							"ADR", "PRD");
					// System.out.println("getResultSendEmail\n" + getResultSendEmail);
					mJResult = new JSONObject(getResultSendEmail);

				} else if (tostatus.equals("15")) { // Approve1
					setStatus = "15";
					getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus,
							"ADR", "PRD");
					// System.out.println("getResultSendEmail\n" + getResultSendEmail);
					mJResult = new JSONObject(getResultSendEmail);

				} else if (tostatus.equals("20")) { // Approve2
					setStatus = "20";
					setDate = ", ADAPDA1 = CURRENT DATE \n";
					getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus,
							"ADR", "PRD");
					// System.out.println("getResultSendEmail\n" + getResultSendEmail);
					mJResult = new JSONObject(getResultSendEmail);

				} else if (tostatus.equals("25")) { // Approve3
					double getADRAssetCost = SelectData.getADRAmount(cono, divi, adrno);
					if (getADRAssetCost >= 50000) {
						setStatus = "25";
						setDate = ", ADAPDA2 = CURRENT DATE \n";
						getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno,
								setStatus, "ADR", "PRD");
						// System.out.println("getResultSendEmail\n" + getResultSendEmail);
						mJResult = new JSONObject(getResultSendEmail);

					} else {
						setStatus = "30";
						setDate = ", ADAPDA2 = CURRENT DATE";
						// mJResult = new JSONObject("{\"result\":\"ok\"}");

						getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno,
								setStatus, "ADR", "PRD");
						// System.out.println("getResultSendEmail\n" + getResultSendEmail);
						mJResult = new JSONObject(getResultSendEmail);

					}

				} else if (tostatus.equals("30")) { // Requester
					setStatus = "30";
					setDate = ", ADAPDA3 = CURRENT DATE \n";
					getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus,
							"ADR", "PRD");
					// System.out.println("getResultSendEmail\n" + getResultSendEmail);
					mJResult = new JSONObject(getResultSendEmail);

				} else if (tostatus.equals("35")) { // Accountant
					setStatus = "35";
					setDate = ", ADAPDA4 = CURRENT DATE \n";
					getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus,
							"ADR", "PRD");
					// System.out.println("getResultSendEmail\n" + getResultSendEmail);
					mJResult = new JSONObject(getResultSendEmail);

				} else if (tostatus.equals("40")) { // Complete
					setStatus = "40";
					getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus,
							"ADR", "PRD");
					// System.out.println("getResultSendEmail\n" + getResultSendEmail);
					mJResult = new JSONObject(getResultSendEmail);

				} else {
					mJResult = new JSONObject("{\"result\":\"ok\"}");

				}

				if (!mJResult.getString("result").equals("ok")) {
					mJsonObj.put("result", "nok");
					mJsonObj.put("message", submit + " not complete.");
					return mJsonObj.toString();

				}

				Statement stmt = conn.createStatement();
				String query = "UPDATE BRLDTA0100.M3_ADRHEAD \n"
						+ "SET ADSTAT = '" + setStatus + "' \n"
						+ "" + setDate + ""
						+ ", ADENDA = CURRENT DATE \n"
						+ ", ADENTI = CURRENT TIME \n"
						+ ", ADENUS = '" + username + "' \n"
						+ "WHERE ADCONO = '" + cono + "' \n"
						+ "AND ADDIVI = '" + divi + "' \n"
						+ "AND ADPREF || '-' || ADORNO = '" + adrno + "'";
				// System.out.println("updateStatusADRHead\n" + query);
				stmt.execute(query);

				if (setStatus.equals("10")) {
					updateStatusADRDetail(cono, divi, adrno, "10", submit, username);

				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", submit + " complete.");
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

	public static String updateStatusADRDetail(String cono, String divi, String adrno, String status, String submit,
			String username)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE BRLDTA0100.M3_ADRDETAIL \n"
						+ "SET ALSTAT = '" + status + "' \n"
						+ ", ALENDA = CURRENT DATE \n"
						+ ", ALENTI = CURRENT TIME \n"
						+ ", ALENUS = '" + username + "' \n"
						+ "WHERE ALCONO = '" + cono + "' \n"
						+ "AND ALDIVI = '" + divi + "' \n"
						+ "AND ALPREF || '-' || ALORNO = '" + adrno + "'";
				// System.out.println("updateStatusADRDetail\n" + query);
				stmt.execute(query);

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", submit + " complete.");
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

	public static String updateRejectADRHead(@Context HttpServletRequest httpServletRequest, String cono, String divi,
			String adrno, String remark, String accremark, String appremark1, String appremark2, String appremark3,
			String appremark4, String fromstatus, String submit, String username) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();

		String setStatus = "05", setRemark = "", setReason = "";
		String getResultSendEmail;
		JSONObject mJResult = new JSONObject();

		if (fromstatus.equals("10")) { // Requester
			accremark = ConvertString.convertApostrophe(accremark);
			setRemark = ", ADACRE1 = '" + accremark + "' \n";
			setReason = "from Accountant, reason: " + accremark;

			getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus, "ADR",
					setReason);
			// System.out.println("getResultSendEmail\n" + getResultSendEmail);
			mJResult = new JSONObject(getResultSendEmail);

		} else if (fromstatus.equals("15")) { // Approve1
			appremark1 = ConvertString.convertApostrophe(appremark1);
			setRemark = ", ADAPRE1 = '" + appremark1 + "' \n";
			setReason = "from Approve1, reason: " + appremark1;

			getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus, "ADR",
					setReason);
			// System.out.println("getResultSendEmail\n" + getResultSendEmail);
			mJResult = new JSONObject(getResultSendEmail);

		} else if (fromstatus.equals("20")) { // Approve2
			appremark2 = ConvertString.convertApostrophe(appremark2);
			setRemark = ", ADAPRE2 = '" + appremark2 + "' \n";
			setReason = "from Approve2, reason: " + appremark2;

			getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus, "ADR",
					setReason);
			// System.out.println("getResultSendEmail\n" + getResultSendEmail);
			mJResult = new JSONObject(getResultSendEmail);

		} else if (fromstatus.equals("25")) { // Approve3
			appremark3 = ConvertString.convertApostrophe(appremark3);
			setRemark = ", ADAPRE3 = '" + appremark3 + "' \n";
			setReason = "from Approve3, reason: " + appremark3;

			getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus, "ADR",
					setReason);
			// System.out.println("getResultSendEmail\n" + getResultSendEmail);
			mJResult = new JSONObject(getResultSendEmail);

		} else if (fromstatus.equals("30")) { // Requester
			appremark4 = ConvertString.convertApostrophe(appremark4);
			setRemark = ", ADAPRE4 = '" + appremark4 + "' \n";
			setReason = "from Approve4, reason: " + appremark4;

			getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus, "ADR",
					setReason);
			// System.out.println("getResultSendEmail\n" + getResultSendEmail);
			mJResult = new JSONObject(getResultSendEmail);

		} else if (fromstatus.equals("35")) { // Accountant
			accremark = ConvertString.convertApostrophe(accremark);
			setRemark = ", ADACRE1 = '" + accremark + "' \n";
			setReason = "from Accountant, reason: " + accremark;

			getResultSendEmail = SendEmail.prepareSendEmail(httpServletRequest, cono, divi, adrno, setStatus, "ADR",
					setReason);
			// System.out.println("getResultSendEmail\n" + getResultSendEmail);
			mJResult = new JSONObject(getResultSendEmail);

			setStatus = "30";

		} else {
			mJResult = new JSONObject("{\"result\":\"ok\"}");

		}

		if (!mJResult.getString("result").equals("ok")) {
			mJsonObj.put("result", "nok");
			mJsonObj.put("message", submit + " not complete.");
			return mJsonObj.toString();

		}

		try {
			if (conn != null) {
				Statement stmt = conn.createStatement();
				String query = "UPDATE BRLDTA0100.M3_ADRHEAD \n"
						+ "SET ADSTAT = '" + setStatus + "' \n"
						+ "" + setRemark + ""
						+ ", ADENDA = CURRENT DATE \n"
						+ ", ADENTI = CURRENT TIME \n"
						+ ", ADENUS = '" + username + "' \n"
						+ "WHERE ADCONO = '" + cono + "' \n"
						+ "AND ADDIVI = '" + divi + "' \n"
						+ "AND ADPREF || '-' || ADORNO = '" + adrno + "'";
				// System.out.println("updateRejectADRHead\n" + query);
				stmt.execute(query);

				if (setStatus.equals("05")) {
					updateStatusADRDetail(cono, divi, adrno, "00", submit, username);

				}

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", submit + " complete.");
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

	public static String updateOrderHead(String cono, String divi, String orderno, String orderdate, String delidate,
			String round, String pricelist, String ordertype, String whs, String status, String type, String remark,
			String pono, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();

		remark = ConvertString.convertApostrophe(remark);
		pono = ConvertString.convertApostrophe(pono);

		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
						+ "SET THORDA = '" + orderdate + "' \n"
						+ ", THDEDA = '" + delidate + "' \n"
						+ ", THROUN = '" + round + "' \n"
						+ ", THPRIC = '" + pricelist + "' \n"
						+ ", THORTY = '" + ordertype + "' \n"
						+ ", THWHLO = '" + whs + "' \n"
						+ ", THREM1 = '" + remark + "' \n"
						+ ", THREM2 = '" + pono + "' \n"
						+ ", THSTAS = '" + status + "' \n"
						+ ", THENDA = CURRENT DATE \n"
						+ ", THENTI = CURRENT TIME \n"
						+ ", THENUS = '" + userid + "' \n"
						+ "WHERE THCONO = '" + cono + "' \n"
						+ "AND THDIVI = '" + divi + "' \n"
						+ "AND THORNO = '" + orderno + "'";
				// System.out.println("updateOrderHead\n" + query);
				stmt.execute(query);

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", type + " complete.");
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

	public static String updateOrderHead_bac211220(String cono, String divi, String orderno, String orderdate,
			String delidate,
			String round, String pricelist, String ordertype, String whs, String status, String type, String remark,
			String pono, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				String query = ("UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
						+ "SET THORDA = ? \n"
						+ ", THDEDA = ? \n"
						+ ", THROUN = ? \n"
						+ ", THPRIC = ? \n"
						+ ", THORTY = ? \n"
						+ ", THWHLO = ? \n"
						+ ", THREM1 = ? \n"
						+ ", THREM2 = ? \n"
						+ ", THSTAS = ? \n"
						+ ", THENDA = CURRENT DATE \n"
						+ ", THENTI = CURRENT TIME \n"
						+ ", THENUS = ? \n"
						+ "WHERE THCONO = ? \n"
						+ "AND THDIVI = ? \n"
						+ "AND THORNO = ? ");

				PreparedStatement pstmt = conn.prepareStatement(query);

				pstmt.setDate(1, Date.valueOf(orderdate));
				pstmt.setDate(2, Date.valueOf(delidate));
				pstmt.setString(3, round);
				pstmt.setString(4, pricelist);
				pstmt.setString(5, ordertype);
				pstmt.setString(6, whs);
				pstmt.setString(7, remark);
				pstmt.setString(8, pono);
				pstmt.setString(9, status);
				pstmt.setString(10, userid);
				pstmt.setString(11, cono);
				pstmt.setString(12, divi);
				pstmt.setString(13, orderno);

				// System.out.println("updateOrderHead\n" + pstmt.toString());
				pstmt.executeQuery();

				mJsonObj.put("result", "ok");
				mJsonObj.put("message", type + " complete.");
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

	public static String updateStatusOrderHead(String cono, String divi, String orderno, String status, String userid)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
						+ "SET THSTAS = '" + status + "' \n"
						+ ", THENDA = CURRENT DATE \n"
						+ ", THENTI = CURRENT TIME \n"
						+ ", THENUS = '" + userid + "' \n"
						+ "WHERE THCONO = '" + cono + "' \n"
						+ "AND THDIVI = '" + divi + "' \n"
						+ "AND THORNO = '" + orderno + "'";
				// System.out.println("updateOrderHead\n" + query);
				stmt.execute(query);
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

	public static String updateStatusOrderHeadPrintCO(String cono, String divi, String fromco, String toco,
			String updatestatus, String fromstatus, String tostatus, String credit, String auth, String userid)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				if (auth.equals("1")) {

					String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
							+ "SET THSTAS = '" + updatestatus + "' \n"
							+ ", THENDA = CURRENT DATE \n"
							+ ", THENTI = CURRENT TIME \n"
							+ ", THENUS = '" + userid + "' \n"
							+ "WHERE THORNO IN (SELECT THORNO \n"
							+ "FROM " + DBNAME + ".M3_TAKEORDERHEAD a, " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT b, "
							+ DBM3NAME + ".OOHEAD c \n"
							+ "WHERE THCONO = '" + cono + "' \n"
							+ "AND THDIVI = '" + divi + "'  \n"
							+ "AND b.TDCONO = THCONO \n"
							+ "AND b.TDDIVI = THDIVI \n"
							+ "AND b.TDORNO = THORNO \n"
							+ "AND b.TDCONU BETWEEN '" + fromco + "' AND '" + toco + "' \n"
							+ "AND (c.OAORST = '" + fromstatus + "' AND c.OAORSL = '" + tostatus + "') \n"
							+ "AND c.OAOBLC IN (" + credit + ") \n"
							+ "AND c.OACONO = THCONO \n"
							+ "AND c.OAORNO = b.TDCONU \n"
							// + "AND c.OARESP = '"+userid+"' \n"
							+ "GROUP BY THORNO)";
					// System.out.println("updateOrderHead\n" + query);
					stmt.execute(query);

				} else {

					String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
							+ "SET THSTAS = '" + updatestatus + "' \n"
							+ ", THENDA = CURRENT DATE \n"
							+ ", THENTI = CURRENT TIME \n"
							+ ", THENUS = '" + userid + "' \n"
							+ "WHERE THORNO IN (SELECT THORNO \n"
							+ "FROM " + DBNAME + ".M3_TAKEORDERHEAD a, " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT b, "
							+ DBM3NAME + ".OOHEAD c \n"
							+ "WHERE THCONO = '" + cono + "' \n"
							+ "AND THDIVI = '" + divi + "' \n"
							+ "AND b.TDCONO = THCONO \n"
							+ "AND b.TDDIVI = THDIVI \n"
							+ "AND b.TDORNO = THORNO \n"
							+ "AND b.TDCONU BETWEEN '" + fromco + "' AND '" + toco + "' \n"
							+ "AND (c.OAORST = '" + fromstatus + "' AND c.OAORSL = '" + tostatus + "') \n"
							+ "AND c.OAOBLC IN (" + credit + ") \n"
							+ "AND c.OACONO = THCONO \n"
							+ "AND c.OAORNO = b.TDCONU \n"
							+ "AND c.OARESP = '" + userid + "' \n"
							+ "GROUP BY THORNO)";
					// System.out.println("updateOrderHead\n" + query);
					stmt.execute(query);

				}

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

	public static String updateStatusOrderHeadPrintCOV2(String cono, String divi, String fromco, String toco,
			String updatestatus, String fromstatus, String tostatus, String credit, String auth, String userid)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();

				if (auth.equals("1")) {

					String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
							+ "SET THSTAS = '" + updatestatus + "' \n"
							+ ", THENDA = CURRENT DATE \n"
							+ ", THENTI = CURRENT TIME \n"
							+ ", THENUS = '" + userid + "' \n"
							+ "WHERE THORNO IN (SELECT THORNO \n"
							+ "FROM " + DBNAME + ".M3_TAKEORDERHEAD a, " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT b, "
							+ DBM3NAME + ".OOHEAD c \n"
							+ "WHERE THCONO = '" + cono + "' \n"
							+ "AND THDIVI = '" + divi + "'  \n"
							+ "AND THSTAS = '92' \n"
							+ "AND b.TDCONO = THCONO \n"
							+ "AND b.TDDIVI = THDIVI \n"
							+ "AND b.TDORNO = THORNO \n"
							+ "AND b.TDCONU BETWEEN '" + fromco + "' AND '" + toco + "' \n"
							+ "AND (c.OAORST = '" + fromstatus + "' AND c.OAORSL = '" + tostatus + "') \n"
							+ "AND c.OAOBLC IN (" + credit + ") \n"
							+ "AND c.OACONO = THCONO \n"
							+ "AND c.OAORNO = b.TDCONU \n"
							// + "AND c.OARESP = '"+userid+"' \n"
							+ "GROUP BY THORNO)";
					// System.out.println("updateOrderHeadV2\n" + query);
					stmt.execute(query);

				} else {

					String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
							+ "SET THSTAS = '" + updatestatus + "' \n"
							+ ", THENDA = CURRENT DATE \n"
							+ ", THENTI = CURRENT TIME \n"
							+ ", THENUS = '" + userid + "' \n"
							+ "WHERE THORNO IN (SELECT THORNO \n"
							+ "FROM " + DBNAME + ".M3_TAKEORDERHEAD a, " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT b, "
							+ DBM3NAME + ".OOHEAD c \n"
							+ "WHERE THCONO = '" + cono + "' \n"
							+ "AND THDIVI = '" + divi + "' \n"
							+ "AND THSTAS = '92' \n"
							+ "AND b.TDCONO = THCONO \n"
							+ "AND b.TDDIVI = THDIVI \n"
							+ "AND b.TDORNO = THORNO \n"
							+ "AND b.TDCONU BETWEEN '" + fromco + "' AND '" + toco + "' \n"
							+ "AND (c.OAORST = '" + fromstatus + "' AND c.OAORSL = '" + tostatus + "') \n"
							+ "AND c.OAOBLC IN (" + credit + ") \n"
							+ "AND c.OACONO = THCONO \n"
							+ "AND c.OAORNO = b.TDCONU \n"
							+ "AND c.OARESP = '" + userid + "' \n"
							+ "GROUP BY THORNO)";
					// System.out.println("updateOrderHeadV2\n" + query);
					stmt.execute(query);

				}

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

	public static String updateStatusOrderHeadInvNumber(String cono, String divi, String orderno, String status)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
						+ "SET THSTAS = '" + status + "' \n"
						+ ", THENDA = CURRENT DATE \n"
						+ ", THENTI = CURRENT TIME \n"
						+ "WHERE THCONO = '" + cono + "' \n"
						+ "AND THDIVI = '" + divi + "' \n"
						+ "AND THORNO = '" + orderno + "'";
				// System.out.println("updateOrderHead\n" + query);
				stmt.execute(query);
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

	public static String updateStatusOrderDetail(String cono, String divi, String orderno, String status)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERDETAIL \n"
						+ "SET TDSTAS = '" + status + "' \n"
						+ ", TDENDA = CURRENT DATE \n"
						+ ", TDENTI = CURRENT TIME \n"
						+ "WHERE TDCONO = '" + cono + "' \n"
						+ "AND TDDIVI = '" + divi + "' \n"
						+ "AND TDORNO = '" + orderno + "' \n"
						+ "AND TDIQTY > 0";
				// System.out.println("updateStatusOrderDetail\n" + query);
				stmt.execute(query);

				if (status.equals("10")) {
					InsertData.addItemDetailToSupport(cono, divi, orderno);
				}

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

	public static String updateItemDetail(String cono, String divi, String orderno, String line, String qty,
			String unit, String remark, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				if (remark == null) {
					remark = "";
				}

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERDETAIL \n"
						+ "SET TDIQTY = '" + qty + "' \n"
						+ ", TDUNIT = '" + unit + "' \n"
						+ ", TDREM1 = '" + remark + "' \n"
						+ ", TDENDA = CURRENT DATE \n"
						+ ", TDENTI = CURRENT TIME \n"
						+ ", TDENUS = '" + userid + "' \n"
						+ "WHERE TDCONO = '" + cono + "' \n"
						+ "AND TDDIVI = '" + divi + "' \n"
						+ "AND TDORNO = '" + orderno + "' \n"
						+ "AND TDLINE = '" + line + "'";
				// System.out.println("updateOrderDatail\n" + query);
				stmt.execute(query);
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

	public static String updateItemDetailUpdate(String cono, String divi, String orderno, String line, String qty,
			String remark, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				if (qty == null) {
					qty = "0";
				}

				if (remark == null) {
					remark = "";
				}

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERDETAIL \n"
						+ "SET TDIQTY = '" + qty + "' \n"
						+ ", TDREM1 = '" + remark + "' \n"
						+ ", TDENDA = CURRENT DATE \n"
						+ ", TDENTI = CURRENT TIME \n"
						+ ", TDENUS = '" + userid + "' \n"
						+ "WHERE TDCONO = '" + cono + "' \n"
						+ "AND TDDIVI = '" + divi + "' \n"
						+ "AND TDORNO = '" + orderno + "' \n"
						+ "AND TDLINE = '" + line + "'";
				// System.out.println("updateItemDetailUpdate\n" + query);
				stmt.execute(query);
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

	public static String updateItemDetailSupport(String cono, String divi, String orderno, String line, String qty,
			String unit, String remark, String remark2, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				if (remark == null) {
					remark = "";
				}

				if (remark2 == null) {
					remark2 = "";
				}

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT \n"
						+ "SET TDIQTY = '" + qty + "' \n"
						+ ", TDUNIT = '" + unit + "' \n"
						// + ", TDREM1 = '" + remark + "' \n"
						+ ", TDREM1 = UPPER('" + remark + "') \n"
						+ ", TDREM2 = UPPER('" + remark2 + "') \n"
						+ ", TDENDA = CURRENT DATE \n"
						+ ", TDENTI = CURRENT TIME \n"
						+ ", TDENUS = '" + userid + "' \n"
						+ "WHERE TDCONO = '" + cono + "' \n"
						+ "AND TDDIVI = '" + divi + "' \n"
						+ "AND TDORNO = '" + orderno + "' \n"
						+ "AND TDLINE = '" + line + "'";
				// System.out.println("updateOrderDatail\n" + query);
				stmt.execute(query);
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

	// public static String updateCOOrderDetail(String cono, String divi, String
	// orderno, String conumber, String status)
	// throws Exception {
	//
	// JSONObject mJsonObj = new JSONObject();
	//
	// Connection conn = ConnectDB2.doConnect();
	// try {
	// if (conn != null) {
	//
	// Statement stmt = conn.createStatement();
	// String query = "UPDATE "+DBNAME+".M3_TAKEORDERDETAIL \n"
	// + "SET TDCONU = '" + conumber + "' \n"
	// + ", TDSTAS = '" + status + "' \n"
	// + "WHERE TDCONO = '" + cono + "' \n"
	// + "AND TDDIVI = '" + divi + "' \n"
	// + "AND TDORNO = '" + orderno + "' \n"
	// + "AND TDSTAS = '10'";
	// System.out.println("updateOrderDatail\n" + query);
	// stmt.execute(query);
	// return mJsonObj.toString();
	//
	// } else {
	// System.err.println("Server can't connect.");
	// }
	//
	// } catch (SQLException sqle) {
	// throw sqle;
	// } catch (Exception e) {
	// e.printStackTrace();
	// if (conn != null) {
	// conn.close();
	// }
	// throw e;
	// } finally {
	// if (conn != null) {
	// conn.close();
	// }
	// }
	//
	// return null;
	//
	// }

	public static String updateCONumberItemDetailSupport(String cono, String divi, String orderno, String conumber,
			String status)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT \n"
						+ "SET TDCONU = '" + conumber + "' \n"
						+ ", TDSTAS = '" + status + "' \n"
						+ ", TDENDA = CURRENT DATE \n"
						+ ", TDENTI = CURRENT TIME \n"
						+ "WHERE TDCONO = '" + cono + "'  \n"
						+ "AND TDDIVI = '" + divi + "'  \n"
						+ "AND TDORNO = '" + orderno + "' \n"
						+ "AND TDSTAS = '10'";
				// System.out.println("updateCOOrderDetailSupport\n" + query);
				stmt.execute(query);
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

	public static String updateInvNumberItemDetailSupport(String cono, String divi) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				String orderno = "", conumber = "", invnumber = "", statushead = "97", statusdetail = "20";
				List<String> getListInvNumber = SelectData.getListInvNumber(cono, divi);

				for (int i = 0; i < getListInvNumber.size(); i++) {
					String[] getDataInvNumber = getListInvNumber.get(i).split(";");
					// System.out.println(getDataInvNumber.get(i));

					orderno = getDataInvNumber[0].trim();
					conumber = getDataInvNumber[1].trim();
					invnumber = getDataInvNumber[3].trim();

					// System.out.println(orderno + " : " + conumber + " : " + invnumber);

					updateStatusOrderHeadInvNumber(cono, divi, orderno, statushead);

					Statement stmt = conn.createStatement();
					String query = "UPDATE " + DBNAME + ".M3_TAKEORDERDETAILSUPPORT \n"
							+ "SET TDIVNO = '" + invnumber + "' \n"
							+ ", TDSTAS = '" + statusdetail + "' \n"
							+ ", TDENDA = CURRENT DATE \n"
							+ ", TDENTI = CURRENT TIME \n"
							+ "WHERE TDCONO = '" + cono + "'  \n"
							+ "AND TDDIVI = '" + divi + "'  \n"
							+ "AND TDORNO = '" + orderno + "' \n"
							+ "AND TDCONU = '" + conumber + "' \n"
							+ "AND TDSTAS = '15'";
					// System.out.println("updateInvNumberItemDetailSupport\n" + query);
					stmt.execute(query);

					mJsonObj.put("result", "ok");

				}

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

	public static String updateUser(String cono, String divi, String username, String status) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE M3FDBPRD.CMNUSR \n"
						+ "SET JUFRF8 = '" + status + "' \n"
						+ "WHERE JUUSID = '" + username + "' \n"
						+ "AND JUFRF7 = 'SALESUP' ";
				// System.out.println("updateOrderDatail\n" + query);
				stmt.execute(query);
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

	public static String updateOrderHeadCO(String cono, String divi, String orderno, String delidate, String userid)
			throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();

		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBNAME + ".M3_TAKEORDERHEAD \n"
						+ "SET THDEDA = '" + delidate + "' \n"
						+ ", THENDA = CURRENT DATE \n"
						+ ", THENTI = CURRENT TIME \n"
						+ ", THENUS = '" + userid + "' \n"
						+ "WHERE THCONO = '" + cono + "' \n"
						+ "AND THDIVI = '" + divi + "' \n"
						+ "AND THORNO = '" + orderno + "'";
				// System.out.println("updateOrderHead\n" + query);
				stmt.execute(query);

				mJsonObj.put("result", "ok");
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

	public static String resetAuthTakeorder(String cono, String divi, String status) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE M3FDBPRD.CMNUSR \n" +
						"SET JUFRF8 = '" + status + "' \n" +
						"WHERE JUFRF7 = 'SALESUP'";
				// System.out.println("resetAuthTakeorder\n" + query);
				stmt.execute(query);
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

	public static String updateODHEAD(String cono, String divi, String orderno, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBM3NAME + ".ODHEAD a \n" +
						"SET UACHID = '" + userid + "' \n" +
						"WHERE UACONO = '" + cono + "' \n" +
						"AND UADIVI = '" + divi + "' \n" +
						"AND UAORNO = '" + orderno + "'";
				// System.out.println("updateODHEAD\n" + query);
				stmt.execute(query);
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

	public static String updateOOHEAD(String cono, String divi, String orderno, String userid) throws Exception {

		JSONObject mJsonObj = new JSONObject();

		Connection conn = ConnectDB2.doConnect();
		try {
			if (conn != null) {

				Statement stmt = conn.createStatement();
				String query = "UPDATE " + DBM3NAME + ".OOHEAD \n" +
						"SET OACHID = '" + userid + "' \n" +
						"WHERE OACONO = '" + cono + "' \n" +
						"AND OADIVI = '" + divi + "' \n" +
						// "AND OARESP = '"+userid+"' \n" +
						"AND OAORNO = '" + orderno + "'";
				// System.out.println("updateOOHEAD\n" + query);
				stmt.execute(query);
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

}
