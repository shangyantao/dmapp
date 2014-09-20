/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.dao;

import com.sap.data.db.util.FieldUtil;
import com.sap.data.db.util.NotFoundException;
import com.sap.data.db.util.PropertyUtil;
import com.sap.data.db.util.ThreadLocalUtil;
import com.sap.data.db.pojo.BapiDD03LPojo;
import com.sap.data.db.pojo.FieldConvertPojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.xml.sax.SAXException;

/**
 *
 * @author C5178395
 */
public class StructureUtil {
    
    private final Object lockhu = new Object();
    private final String NONVALIDATING_DTD = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
    private final String HIBERNATE_MAPPING = "hibernate-mapping";
    private final String HIBERNATE_MAPPING_EN = "-//Hibernate/Hibernate Mapping DTD 3.0//EN";
    private final String HIBERNATE_MAPPING_DTD = "http://hibernate.sourceforge.net/hibernate-mapping-3.0.dtd";
    protected Hashtable<String, String> fieldConvertMap = new Hashtable<String, String>();
    
    public void addStructure(String entity, String structure) throws DocumentException, FileNotFoundException, SAXException, IOException, NotFoundException {
        synchronized (lockhu){
            if(entity != null && !entity.trim().isEmpty()) {
                Document document = this.getDocument(PropertyUtil.getHbDtoXml());
                Element rootElement = document.getRootElement();
                rootElement.addAttribute("package", PropertyUtil.getHbPjPkgn());
                String comment = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                rootElement.addComment(comment + " " + entity);
                this.addStructure(rootElement.addElement("class"), entity, structure);
                String classpath = StructureUtil.class.getResource("/").getPath() + PropertyUtil.getHbPjResource();
                this.save(document, classpath + entity + ".dto.xml");
            }
        }
    }
    
    public static boolean exist(String entity) {
    	String classpath = StructureUtil.class.getResource("/").getPath() + PropertyUtil.getHbPjResource();
    	File file = new File(classpath + entity + ".dto.xml");
    	if(file.exists()) {
    		return true;
    	} else {
    		return false;
    	}
    }

    private Document getDocument(String filePath) throws DocumentException, FileNotFoundException, SAXException {
    	InputStream inputStream = null;
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	if(classLoader != null) {
    		inputStream = classLoader.getResourceAsStream(filePath);
    	}
        SAXReader reader = new SAXReader();
        reader.setFeature(this.NONVALIDATING_DTD, false);
        Document document = reader.read(inputStream);
        return document;
    }
    
    private Element addStructure(Element element, String entityName, String structure) throws NotFoundException {
        List<BapiDD03LPojo> list = new BapiDD03LDao().selectTabFields(structure);
        if(list.size() > 0) {
            String type = "java.lang.String";
            element.addAttribute("entity-name", entityName);
            element.addAttribute("name", entityName);
            element.addAttribute("table", entityName);
            Element idElement = element.addElement("composite-id");
            idElement.addAttribute("name", "DId");
            idElement.addAttribute("class", entityName + "Id");
            if(!entityName.startsWith("E0015")) {
            	Vector<FieldUtil> fieldUtils = ThreadLocalUtil.getFieldType();
            	for(FieldUtil fieldUtil : fieldUtils) {
            		if("key".equals(fieldUtil.getFieldType())) {
            			this.addElement(idElement, "key-property", type, fieldUtil.getFieldName(), fieldUtil.getFieldLeng());
            		} else if("field".equals(fieldUtil.getFieldType())){
            			this.addElement(element, "property", type, fieldUtil.getFieldName(), fieldUtil.getFieldLeng());
            		}
            	}
            }
            
            for(int i = 0; i < list.size(); i++) {
                BapiDD03LPojo pojo = list.get(i);
                final String fieldName = pojo.getId().getFIELDNAME().toUpperCase();
                if(fieldName.startsWith(".") || (null != pojo.getDd03ldm() && "X".equalsIgnoreCase(pojo.getDd03ldm().getDM_EXCLUDE_FLAG()))) {
                    continue;
                }
                final String fieldLength = this.getFieldLength(pojo.getLENG());
                if("X".equalsIgnoreCase(pojo.getKEYFLAG()) 
                    || (null != pojo.getDd03ldm() && "X".equalsIgnoreCase(pojo.getDd03ldm().getDM_KEYFLAG()))) {
                    this.addElement(idElement, "key-property", type, fieldName, fieldLength);
                } else {
                    this.addElement(element, "property", type, fieldName, fieldLength);
                }
            }
        }
        return element;
    }
    
    private void save(Document document, String filepath) throws IOException {
        document.addDocType(this.HIBERNATE_MAPPING, this.HIBERNATE_MAPPING_EN, this.HIBERNATE_MAPPING_DTD);
        OutputFormat format = OutputFormat.createPrettyPrint();
        format.setEncoding("UTF-8");
        
        XMLWriter writer = new XMLWriter(new FileWriter(new File(filepath)), format);
        writer.write(document);
        writer.close();
    } 
    
    private void addElement(Element element, String attributeName, String attributeType, String fieldName, String fieldLength) throws NotFoundException {
        Element fieldElement = element.addElement(attributeName);
        fieldElement.addAttribute("name", fieldName.toUpperCase());
        fieldElement.addAttribute("type", attributeType);
        
        Element fieldNameElement = fieldElement.addElement("column");
        fieldNameElement.addAttribute("name", this.fieldConvert(fieldName.toUpperCase()));
        fieldNameElement.addAttribute("length", fieldLength);
    }
    private String fieldConvert(String fieldName) throws NotFoundException {
    	if(null == this.fieldConvertMap || this.fieldConvertMap.isEmpty()) {
    		FieldConvertDao dao = new FieldConvertDao();
        	List<FieldConvertPojo> list = dao.selectConvertField();
        	for(FieldConvertPojo pojo : list) {
        		this.fieldConvertMap.put(pojo.getFIELDNAME(), pojo.getDM_FIELDNAME());
        	}
    	}
    	String convertFieldName = this.fieldConvertMap.get(fieldName);
    	if(null == convertFieldName) {
    		return fieldName;
    	} else {
    		return convertFieldName;
    	}   
    }
       
    private String getFieldLength(Integer length) {
        return String.valueOf(length * 2);
    }
    
}
