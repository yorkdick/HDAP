package com.myself.hdap.server.deployment.hotdeploy;

import com.myself.hdap.common.CommonInstant;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DeployMethod {
	private Method method;
	private Object obj;
	private String deployId;
	private String jarPath;
	private ClassLoader loader;
	private String primaryKey;
	
	public DeployMethod(String jarPath,String deployId,Method method,Object obj,ClassLoader loader){
		this.method = method;
		this.obj = obj;
		this.deployId = deployId;
		this.jarPath = jarPath;
		this.loader = loader;
		setPrimaryKey();
	}

	public void invoke(String[] args) {
		try {
			Object[] args2 = resolve(args);
			invoke(args2);
		} catch (Exception e) {
			System.out.println("invoke method "+obj.toString()+"["+method.getName()+"] failed");
			System.out.println(e.getMessage());
		}
	}

	public void invoke(Object[] args) {
		try {
			method.invoke(obj, args);
		} catch (Exception e) {
			System.out.println("invoke method "+obj.toString()+"["+method.getName()+"] failed");
			System.out.println(e.getMessage());
		}
	}
	
	private Object[] resolve(String[] args) {
		Parameter[] ps = method.getParameters();
		if(ps!=null && ps.length>0) {
			if(args==null || args.length!=ps.length) {
				throw new RuntimeException("resolve args erro , args size not correct");
			}
			Object[] arg = new Object[ps.length];
			for(int i=0;i<ps.length;i++) {
				if(ps[i].getType().isPrimitive() || ps[i].getType().equals(String.class)) {
					arg[i]=valueOf(ps[i],args[i]);
				}else {
					throw new RuntimeException("resolve args erro , must be primitive or String");
				}
			}
			return arg;
		}
		return null;
	}

	private Object valueOf(Parameter ps, String str) {
		Class<?> cls = ps.getType();
		if(cls.equals(char.class)) {
			if(str==null || str.length()<1 || str.toCharArray().length>1) {
				resolveParamerror(ps,cls);
			}
			return str.charAt(0);
		}
		if(cls.equals(short.class)) {
			try {
				return Short.valueOf(str);
			} catch (Exception e) {
				resolveParamerror(ps,cls);
			}
		}
		if(cls.equals(int.class)) {
			try {
				return Integer.valueOf(str);
			} catch (Exception e) {
				resolveParamerror(ps,cls);
			}
		}
		if(cls.equals(long.class)) {
			try {
				return Long.valueOf(str);
			} catch (Exception e) {
				resolveParamerror(ps,cls);
			}
		}
		if(cls.equals(float.class)) {
			try {
				return Float.valueOf(str);
			} catch (Exception e) {
				resolveParamerror(ps,cls);
			}
		}
		if(cls.equals(double.class)) {
			try {
				return Double.valueOf(str);
			} catch (Exception e) {
				resolveParamerror(ps,cls);
			}
		}
		if(cls.equals(byte.class)) {
			try {
				return Byte.valueOf(str);
			} catch (Exception e) {
				resolveParamerror(ps,cls);
			}
		}
		if(cls.equals(boolean.class)) {
			try {
				return Boolean.valueOf(str);
			} catch (Exception e) {
				resolveParamerror(ps,cls);
			}
		}
		if(cls.equals(String.class)) {
			return str;
		}
		return null;
	}
	
	public void unDeploy() {
		this.method = null;
		this.deployId = null;
		this.obj = null;
		this.loader = null;
		this.jarPath = null;
		this.primaryKey = null;
	}

	private void setPrimaryKey() {
		primaryKey = CommonInstant.getDeployMethodId(deployId,method);
	}

	@Override
	public int hashCode() {
		return primaryKey.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		boolean bool = super.equals(obj);
		if(bool){
			return bool;
		}else{
			return obj!=null && obj instanceof DeployMethod && this.toString().equals(obj.toString());
		}
	}

	@Override
	public String toString() {
		return primaryKey;
	}

	private void resolveParamerror(Parameter ps, Class<?> cls) {
		throw new RuntimeException("resolve args erro ,"+ps.getName()+" is "+cls.getName());
	}

	public Method getMethod() {
		return method;
	}

	public Object getObj() {
		return obj;
	}

	public String getDeployId() {
		return deployId;
	}
	
	public String getJarPath() {
		return jarPath;
	}

	public ClassLoader getLoader() {
		return loader;
	}
}
