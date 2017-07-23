package com.myself.hdap.server.command;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.myself.hdap.server.annotation.CmdParam;
import com.myself.hdap.server.context.ClassHandler;

public class CommandClassHandler implements ClassHandler{

	public boolean filter(Class<?> class1) {
		Class<?> cls = Command.class;
		for(Class<?> in : class1.getInterfaces()) {
			if(in.equals(cls)) {
				return true;
			}
		}
		
		Class<?> ss = class1.getSuperclass();
		while(ss!=null && !ss.isPrimitive() && !ss.equals(Object.class)) {
			if(ss.equals(cls)) {
				return true;
			}
			ss = ss.getSuperclass(); 
		}
		return false;
	}

	public void handler(Class<?> cls) throws Exception  {
		Command command = (Command) cls.newInstance();

		List<CommandParam> params = new ArrayList<CommandParam>();

		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(CmdParam.class)) {
				CmdParam cmdParam = field.getAnnotation(CmdParam.class);
				CommandParam param = new CommandParam();
				param.setRequired(cmdParam.require());
				param.setRegex(cmdParam.regex());
				param.setParam(field.getName().toLowerCase());
				
				if(params.contains(param)){
					System.out.println("command params duplicated ,"+command.getCommandKey()+" "+param.getParam());
					throw new RuntimeException("command params duplicated ,"+command.getCommandKey()+" "+param.getParam());
				}
				params.add(param);
			}
		}
		
		if(CommandRepository.getAllCommands().containsKey(command.getCommandKey())) {
			throw new RuntimeException("command duplicated "+command.getCommandKey());
		}else {
			CommandRepository.putCommand(command, params);
		}
	}

}
