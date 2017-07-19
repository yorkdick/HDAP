package com.myself.hdap.server.command;

public class CommandParam {
	private String param;
	private String regex=".*";
	private boolean required;
	
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null || ! (obj instanceof CommandParam)){
			return false;
		}
		return this.getParam().equals(((CommandParam)obj).getParam());
	}
	
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
}
