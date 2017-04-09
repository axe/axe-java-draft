package com.axe.util;

@FunctionalInterface
public interface Logger  
{

	public void log(Object message, Object ... arguments);
	
}
