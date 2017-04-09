package com.axe.util;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DefaultLogger implements Logger 
{

	private static final DateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY HH:mm:ss");
	
	public PrintStream out;
	
	public DefaultLogger()
	{
		this( System.out );
	}
	
	public DefaultLogger(PrintStream out)
	{
		this.out = out;
	}
	
	@Override
	public void log(Object message, Object... arguments) 
	{
		out.print( "[" );
		out.print( dateFormat.format(new Date()) );
		out.print( "] " );
		
		if (message instanceof Exception)
		{
			((Exception)message).printStackTrace();
		}
		else if (message instanceof String)
		{
			if (arguments.length == 0)
			{
				out.println( message );
			}
			else
			{
				out.format( message + "\n", arguments );
			}
		}
		else
		{
			out.println( message );
		}
	}

}
