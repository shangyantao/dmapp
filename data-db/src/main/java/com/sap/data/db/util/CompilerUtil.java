/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sap.data.db.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

/**
 *
 * @author C5178395
 */
public class CompilerUtil {
	
    public synchronized void compiler(String className) throws NotFoundException, IOException {
    	String classpath = CompilerUtil.class.getResource("/").getPath();
        List<String> path = Arrays.asList(classpath + PropertyUtil.getHbPjResource() + className +".java");
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, null, null);
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(path);
        
        JavaCompiler.CompilationTask task = null;
		if("true".equals(PropertyUtil.getWebApplication())) {
	        List<String> options = Arrays.asList("-classpath", classpath);
	        task = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
        } else {
        	task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
        }
		String diagnosticCode = null;
        for(Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
        	diagnosticCode = diagnostic.getCode();
        }
        fileManager.close();
        boolean success = task.call();
        if(!success) {
        	throw new NotFoundException("Code:" + diagnosticCode + ":" + path.get(0));
        }
    }
    
}
