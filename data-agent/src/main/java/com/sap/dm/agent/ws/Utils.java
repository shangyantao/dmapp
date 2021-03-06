package com.sap.dm.agent.ws;

import java.util.HashMap;
import java.util.Map;

public final class Utils {

    public static Map<String,String> parasToMap(String paras){
        Map<String, String> mapParas = new HashMap<String, String>();
        String[] inputParas = paras.split(";");
        for (String elem : inputParas) {
        	int elemLeng = elem.length();
        	int splitIndex = elem.indexOf("=");
        	if(splitIndex > 0) {
        		String name = elem.substring(0, splitIndex).trim();
        		String value = elem.substring(splitIndex + 1, elemLeng);
        		mapParas.put(name, value);
        	}
        }
        return mapParas;
    }
    
}
