package com.myself.hdap.server.context;

public interface ClassHandler {
	boolean filter(Class<?> cls);
	void handler(Class<?> cls)  throws Exception ;
}
