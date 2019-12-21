package com.accredilink.bgv.util;

import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class ResponseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -107752139156246555L;

	private String message;
	private int code;
	private String token;
	private String sessionId;
	private int agencyId;
	private String agencyName;
	private String agencyLogo;

	public String getAgencyLogo() {
		return agencyLogo;
	}

	public void setAgencyLogo(String agencyLogo) {
		this.agencyLogo = agencyLogo;
	}

	public ResponseObject(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public ResponseObject(String message, int code, String sessionId) {
		this.message = message;
		this.code = code;
		this.sessionId = sessionId;
	}

	public ResponseObject(String message, int code, String sessionId, int agencyId, String agencyName,
			String agencyLogo) {
		this.message = message;
		this.code = code;
		this.sessionId = sessionId;
		this.agencyId = agencyId;
		this.agencyName = agencyName;
		this.agencyLogo = agencyLogo;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public static ResponseObject constructResponse(String message, int code) {
		return new ResponseObject(message, code);
	}

	public static ResponseObject constructResponse(String message, int code, String sessionId) {
		return new ResponseObject(message, code, sessionId);
	}

	public static ResponseObject constructResponse(String message, int code, String sessionId, int agencyId,
			String agencyName, String agencyLogo) {
		return new ResponseObject(message, code, sessionId, agencyId, agencyName, agencyLogo);
	}

	public ResponseObject() {
	}
}
