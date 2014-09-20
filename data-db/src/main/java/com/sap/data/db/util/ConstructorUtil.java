/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.util;

import com.sap.data.db.dao.BapiDD03LDao;
import com.sap.data.db.pojo.BapiDD03LPojo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author C5178395
 */
public class ConstructorUtil {
    
    protected String className = "";
    protected Vector<FieldUtil> fields = new Vector<FieldUtil>();
    protected String tabWord = "\t";
    protected String nleWord = "\n";
    protected boolean keyFlag = false;
    
    public ConstructorUtil(String className) {
        this.className = className;
    }
    
    public void generate(boolean overWrite) throws FileNotFoundException, IOException {
        if(this.className != null && !this.className.trim().isEmpty() ) {
            String classpath = ConstructorUtil.class.getResource("/").getPath() + PropertyUtil.getHbPjResource();
            if(!classpath.endsWith(File.separator)) {
            	classpath += File.separator;
            }
            File file = new File(classpath + this.className + ".java");
            if(!file.getParentFile().exists()) {
            	file.getParentFile().mkdirs();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.generatePackage())
                .append(this.generateImport())
                .append(this.generateStruct())
                .append(this.generateAttribute())
                .append(this.generateNoParamConstructor());
            
            if(!overWrite) {
            	stringBuilder.append(this.generateConstructorWithDId());
            }
            
            stringBuilder.append(this.generateConstructor())
                .append(this.generateGSMethod());
            
            if(overWrite) {
                stringBuilder.append(this.generateHCMethod())
                .append(this.generateEqualsMethod());
            }
            
            stringBuilder.append(this.gnarl());
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(stringBuilder.toString().getBytes());
                out.close();
            }
        }
    } 
    
    private StringBuilder generatePackage() {
        return new StringBuilder().append("package ").append(PropertyUtil.getHbPjPkgn()).append(";").append(nleWord).append(nleWord);
    }
    
    private StringBuilder generateImport() {
        return new StringBuilder().append("import java.io.Serializable;").append(nleWord).append(nleWord);
    }
    
    private StringBuilder generateStruct() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("public class ");
        stringBuilder.append(className);
        stringBuilder.append(" implements Serializable");
        stringBuilder.append(" {");
        stringBuilder.append(nleWord).append(nleWord);
        return stringBuilder;
    }
    
    private StringBuilder generateAttribute() {
        StringBuilder stringBuilder = new StringBuilder();
        if(fields != null && !fields.isEmpty()) {
            for(FieldUtil field : fields) {
                if(field.getFieldName().startsWith(".")) {
                    continue;
                }
                stringBuilder.append(tabWord).append("private").append(" ");
                stringBuilder.append(field.getFieldType()).append(" ");
                stringBuilder.append(field.getFieldName()).append(";").append(nleWord);
            }
            stringBuilder.append(nleWord);
        }
        return stringBuilder;
    }
    
    private StringBuilder generateNoParamConstructor() {
        StringBuilder method = new StringBuilder();
        method.append(tabWord).append("public ").append(className).append("(){").append("\n\t").append("}").append("\n\n");
        return method;
    }
    
    private StringBuilder generateConstructorWithDId() {
        StringBuilder method = new StringBuilder();
        if(fields != null && !fields.isEmpty() && fields.size() > 1) {
            StringBuilder methodHead = new StringBuilder();
            StringBuilder methodBody = new StringBuilder();
            methodHead.append("\t").append("public ").append(className).append(" (");
            
            for(FieldUtil field : fields) {
                if("DId".equals(field.getFieldName())) {
                	methodHead.append(field.getFieldType()).append(" ").append(field.getFieldName()).append(") {");
                    methodBody.append("\t\t").append("this.").append(field.getFieldName()).append(" = ")
                        .append(field.getFieldName()).append(";").append("\n\t").append("}");
                    break;
                }
            }
            method.append(methodHead).append("\n").append(methodBody).append("\n\n");
        }
        return method;
    }
    
    private StringBuilder generateConstructor() {
        StringBuilder method = new StringBuilder();
        if(fields != null && !fields.isEmpty()) {
            StringBuilder methodHead = new StringBuilder();
            StringBuilder methodBody = new StringBuilder();
            methodHead.append("\t").append("public ").append(className).append(" (");
            
            int i = 0;
            for(FieldUtil field : fields) {
                if(field.getFieldName().startsWith(".")) {
                    i++;
                    continue;
                }
                if(i == (fields.size() - 1)) {
                    methodHead.append(field.getFieldType()).append(" ").append(field.getFieldName()).append(") {");
                    methodBody.append("\t\t").append("this.").append(field.getFieldName()).append(" = ")
                        .append(field.getFieldName()).append(";").append("\n\t").append("}");
                } else {
                    methodHead.append(field.getFieldType()).append(" ").append(field.getFieldName()).append(", ");
                    methodBody.append("\t\t").append("this.").append(field.getFieldName()).append(" = ")
                        .append(field.getFieldName()).append(";").append("\n");
                }
                i++;
            }
            method.append(methodHead).append("\n").append(methodBody).append("\n\n");
        }
        return method;
    }
    
    private StringBuilder generateGSMethod() {
        StringBuilder stringBuilder = new StringBuilder();
        for(FieldUtil field : fields) {
            if(field.getFieldName().startsWith(".")) {
                continue;
            }
            
            stringBuilder.append("\t").append("public void set").append(field.getFieldName()).append("(");
            stringBuilder.append(field.getFieldType()).append(" ").append(field.getFieldName()).append(") {").append("\n");
                
            stringBuilder.append("\t\t").append("this.").append(field.getFieldName()).append(" = ")
            .append(field.getFieldName()).append(";").append("\n\t").append("}").append("\n\n");
            
            stringBuilder.append("\t").append("public").append(" ").append(field.getFieldType()).append(" ").append("get");
            stringBuilder.append(field.getFieldName()).append("() {").append("\n")
                .append("\t\t").append("return ").append(field.getFieldName()).append(";").append("\n\t").append("}").append("\n\n");
        }
        return stringBuilder;
    }
    
    private StringBuilder generateHCMethod() {
        StringBuilder stringBuilder = new StringBuilder();
        if(fields != null && !fields.isEmpty()) {
            stringBuilder.append("\t").append("@Override").append("\n\t")
                .append("public int hashCode() {").append("\n\t\t")
                .append("int hash = 7;").append("\n\t\t");
            for(FieldUtil field : fields) {
                if(field.getFieldName().startsWith(".")) {
                    continue;
                }
                stringBuilder.append("hash = 37 * hash + (this.get")
                    .append(field.getFieldName()).append("() == null ? 0 : this.get")
                    .append(field.getFieldName()).append("().hashCode());").append("\n\t\t");
            }
            stringBuilder.append("return hash;").append("\n\t").append("}").append("\n");
        }
        stringBuilder.append("\n");
        return stringBuilder;
    }
    
    private StringBuilder generateEqualsMethod() {
        StringBuilder stringBuilder = new StringBuilder();
        if(fields != null && !fields.isEmpty()) {
            stringBuilder.append("\t").append("@Override").append("\n\t")
                .append("public boolean equals(Object obj) {").append("\n\t\t")
                .append("if (obj == null) {").append("\n\t\t\t")
                .append("return false;").append("\n\t\t")
                .append("}").append("\n\t\t")
                .append("if (this == obj) {").append("\n\t\t\t")
                .append("return true;").append("\n\t\t")
                .append("}").append("\n\t\t")
                .append("if (!(obj instanceof ").append(className).append(")) {").append("\n\t\t\t")
                .append("return false;").append("\n\t\t")
                .append("}").append("\n\t\t")
                .append(className).append(" other = (").append(className).append(") obj;").append("\n\t\t")
                .append("return ");
            int i = 1;
            for(FieldUtil field : fields) {
                if(field.getFieldName().startsWith(".")) {
                    i++;
                    continue;
                }
                if(i == 1) {
                    stringBuilder.append("(this.get").append(field.getFieldName()).append("() != null && other.get")
                        .append(field.getFieldName()).append("() != null && this.get").append(field.getFieldName()).append("().equals(other.get")
                        .append(field.getFieldName()).append("())) ");
                } else {
                    stringBuilder.append("\t\t\t").append("&& ").append("(this.get").append(field.getFieldName()).append("() != null && other.get")
                        .append(field.getFieldName()).append("() != null && this.get").append(field.getFieldName()).append("().equals(other.get")
                        .append(field.getFieldName()).append("())) ");
                    
                }
                if(i == fields.size()) {
                    stringBuilder.append(";").append("\n");
                } else {
                    stringBuilder.append("\n");
                }
                i++;
            }
            stringBuilder.append("\t").append("}").append("\n");
        }
        return stringBuilder;
    }
    
    private StringBuilder gnarl() {
        return new StringBuilder().append("}");
    }
    
    public ConstructorUtil addField(String structure) throws NotFoundException {
        FieldUtil fieldId = new FieldUtil(this.className + "Id", "DId");
        this.fields.add(fieldId);
        if(!this.className.startsWith("E0015")) {
        	Vector<FieldUtil> fieldUtils = ThreadLocalUtil.getFieldType();
        	for(FieldUtil fieldUtil : fieldUtils) {
        		if("field".equals(fieldUtil.getFieldType())) {
        			FieldUtil field = new FieldUtil("String", fieldUtil.getFieldName().toUpperCase());
        			this.fields.add(field);
        		}
        	}
        }
        List<BapiDD03LPojo> list = new BapiDD03LDao().selectTabFields(structure);
        for (BapiDD03LPojo pojo : list) { 
            if(pojo.getId().getFIELDNAME().startsWith(".")
                    || (null != pojo.getDd03ldm() && "X".equalsIgnoreCase(pojo.getDd03ldm().getDM_EXCLUDE_FLAG()))) {
                continue;
            }
            if(!"X".equalsIgnoreCase(pojo.getKEYFLAG()) 
                    && (null == pojo.getDd03ldm() || !"X".equalsIgnoreCase(pojo.getDd03ldm().getDM_KEYFLAG()))) {
                FieldUtil field = new FieldUtil("String", pojo.getId().getFIELDNAME().toUpperCase());
                this.fields.add(field);
            }
        }
        
        return this;
    }
    
    public ConstructorUtil addKeyField(String structure) throws NotFoundException {
        if(!this.className.startsWith("E0015")) {
        	Vector<FieldUtil> fieldUtils = ThreadLocalUtil.getFieldType();
        	for(FieldUtil fieldUtil : fieldUtils) {
        		if("key".equals(fieldUtil.getFieldType())) {
        			FieldUtil field = new FieldUtil("String", fieldUtil.getFieldName().toUpperCase());
        			this.fields.add(field);
        		}
        	}
        }
        List<BapiDD03LPojo> list = new BapiDD03LDao().selectTabFields(structure);
        for (BapiDD03LPojo pojo : list) { 
            if(pojo.getId().getFIELDNAME().startsWith(".")
                    || (null != pojo.getDd03ldm() && "X".equalsIgnoreCase(pojo.getDd03ldm().getDM_EXCLUDE_FLAG()))) {
                continue;
            }
            if("X".equalsIgnoreCase(pojo.getKEYFLAG()) 
                    || (null != pojo.getDd03ldm() && "X".equalsIgnoreCase(pojo.getDd03ldm().getDM_KEYFLAG()))) {
                FieldUtil field = new FieldUtil("String", pojo.getId().getFIELDNAME().toUpperCase());
                this.fields.add(field);
                this.keyFlag = true;
            }
        }
        
        return this;
    }
    
    public boolean getKeyFlag() {
        return this.keyFlag;
    }
    
}
