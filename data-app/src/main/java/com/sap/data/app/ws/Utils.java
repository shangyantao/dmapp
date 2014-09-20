package com.sap.data.app.ws;

import java.util.HashMap;
import java.util.Map;

public final class Utils {

	public static Map<String,String> parasToMap(String paras){
		Map<String,String> mapParas=new HashMap<String,String>();
		String inputParas[]=paras.split(";");
		for (String elem : inputParas) {
			String elems[]=elem.split("=");
			if(elems.length>0)
			mapParas.put(elems[0], elems[1]);
		}
		return mapParas;
	}
}
