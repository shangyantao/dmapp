package com.sap.data.ws;

/**
 * WebService常量定义.
 * 
 */
public class WsConstants {

	/**项目内统一的NameSpace定义, for SOAP.*/
	public static final String NS = "http://www.sap.com";

	/**项目内统一的XML charset定义, for REST*/
	public static final String CHARSET = ";charset=UTF-8";
	
	public static final int STATUS_NOT_ACCEPTABLE=4061;
	public static final String STATUS_NOT_ACCEPTABLE_MESSAGE="server have connected by another agent";
}
