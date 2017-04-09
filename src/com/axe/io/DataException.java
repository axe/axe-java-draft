package com.axe.io;

public class DataException extends RuntimeException
{
	private static final long serialVersionUID = 1L;

	public DataException(String message) 
	{
		super( message );
	}
	
	public DataException(Exception cause) 
	{
		super( cause );
	}
	
	public DataException(String format, Object ... args) 
	{
		super( String.format(format, args) );
	}
	
}