package com.myself.hdap.server.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandParse {
	
	private static boolean error = false;
	
	public static void main(String[] args) {
		String cmd = "deploy  --name= \"444 asdf  \" --jar /apt/list.jar --namse=s4";
		List<String> cmdStrs = getStrs(cmd);
		Map<String,String> params = getParams(cmdStrs);
		if(params!=null){
			for(String key : params.keySet()){
				System.out.println(key+" "+params.get(key));
			}
		}
	}
	
	public static Command parse(String cmd){
		if(cmd.equals("") || cmd==null)
			return null;
		
		List<String> cmdStrs = getStrs(cmd);
		if(cmdStrs.size()<=0 || error==true){
			return null;
		}
		
		String cmdKey = cmdStrs.get(0);
		Command cmdTmp = CommandRepository.getCommand(cmdKey);
		if(cmdTmp==null){
			System.out.println("no command called "+cmdKey+" found");
			return null;
		}
		
		Map<String,String> params = getParams(cmdStrs);
		if(error==true){
			return null;
		}
		
		List<CommandParam> cmdParams = cmdTmp.getParams();
		
		if(!validatePrams(cmdParams,params,cmdTmp)){
			return null;
		}
		
		return cmdTmp;
	}

	private static boolean validatePrams(List<CommandParam> cmdParams, Map<String, String> params,Command cmdTmp) {
		for(CommandParam cmdParam : cmdParams ){
			if(cmdParam.isRequired()){
				if(!params.containsKey(cmdParam.getParam())){
					System.out.println("param "+cmdParam.getParam()+" is required");
					return false;
				}
			}
			String regex = cmdParam.getRegex();
			if(regex!=null && !(regex = regex.trim()).equals("")){
				try {
					if(!Pattern.compile(regex).matcher(params.get(cmdParam.getParam())).matches()){
						System.out.println("param "+cmdParam.getParam()+" not satisfy '+regex+'");
						return false;
					}
				} catch (Exception e) {
					System.out.println("param "+cmdParam.getParam()+" not satisfy '+regex+'");
					return false;
				}
			}
			if(params.containsKey(cmdParam.getParam())){
				try {
					setCommadParam(cmdParam.getParam(),params.get(cmdParam.getParam()),cmdTmp);
				} catch (Exception e) {
					throw new RuntimeException("setCommadParam error ,param "+cmdParam.getParam()+" "+e.getMessage());
				}
			}
		}
		return true;
	}

	private static void setCommadParam(String key, String value, Command cmdTmp) throws Exception {
		for( Field field : cmdTmp.getClass().getDeclaredFields()){
			if(field.getName().toLowerCase().equals(key)){
				field.setAccessible(true);
				field.set(cmdTmp, value);
			}
		}
	}

	private static Map<String, String> getParams(List<String> cmdStrs) {
		error = false;
		
		if(cmdStrs.size()<=1){
			return null;
		}
		
		Map<String, String> params = new HashMap<String, String>();
		
//		String dep
		for(int i=1;i< cmdStrs.size();){
			String param = cmdStrs.get(i);
			if(!param.startsWith("--") || getHSize(param)!=2){
				error = true;
				System.out.println("command param '"+param+"' format error, any param needs start with '--'");
				return null;
			}
			String value = "";
			if(i+1>=cmdStrs.size() || (value = cmdStrs.get(i+1)).startsWith("--")){
				error = true;
				System.out.println("command param '"+param+"' has no value");
				return null;
			}
			
			value = value.replaceAll("\"", "");
			if(params.containsKey(param)){
				error = true;
				System.out.println("command param '"+param+"' is duplicated");
				return null;
			}
			params.put(param.substring(2).toLowerCase(), value);
			i = i+2;
		}
		return params;
	}

	private static int getHSize(String param) {
		int size=0;
		for(int i = 0;i< param.length();i++) {
			if(param.substring(i,i+1).equals("-"))
				size++;
		}
		return size;
	}

	private static List<String> getStrs(String cmd) {
		error = false;
		
		List<String> strs = new ArrayList<String>();
		int start=0;
		String tmp = "";
		while(start<=cmd.length()-1){
			String str = cmd.substring(start, start+1);
			if(str.equals(" ")){
				if(!tmp.trim().equals("") && !tmp.trim().equals("=")){
					strs.add(tmp);
					tmp = "";
				}
				start++;
			}else if(str.equals("\"")){
				int next = cmd.indexOf("\"", start+1);
				if(next<=0){
					errorHandler(str,start);
					return null;
				}else{
					tmp = cmd.substring(start,next+1);
					strs.add(tmp);
					tmp = "";
					start = next+1;
				}
			}else if(str.equals("=")){
				if(judgeEqual(cmd,start)){
					errorHandler(str,start);
					return null;
				}else{
					strs.add(tmp);
					tmp = "";
					start++;
				}
			}else{
				tmp=tmp+str;
				start++;
			}
		}
		if(!tmp.trim().equals("")){
			strs.add(tmp);
		}
		return strs;
	}

	private static boolean judgeEqual(String cmd,int start) {
		if(start==0 || start==cmd.length()-1){
			return true;
		}
		String pre = cmd.substring(0, start).trim();
		String after = cmd.substring(start+1).trim();
		if(pre.equals("") || pre.endsWith("=")){
			return true;
		}
		if(after.equals("") || after.startsWith("=")){
			return true;
		}
		return false;
	}

	private static void errorHandler(String str, int start) {
		error = true;
		System.out.println("cmd format error "+str+" at "+start+"!");
	}
}
