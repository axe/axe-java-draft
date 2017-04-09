package com.axe.util;

import com.axe.core.Factory;
import com.axe.io.DataRegistry;


public class ReflectionFactory<T> implements Factory<T>
{

	public final Class<T> clazz;
	
	private ReflectionFactory( Class<T> clazz )
	{
		this.clazz = clazz;
	}
	
	@Override
	public T create()
	{
		try
		{
			return clazz.newInstance();
		}
		catch (Exception e)
		{
			return null;	
		}
	}
	
	public static <T> ReflectionFactory<T> fromClass( Class<T> clazz )
	{
		return new ReflectionFactory<T>( clazz );
	}
	
	public static <T> ReflectionFactory<T> fromName( String className )
	{
		try
		{
			return new ReflectionFactory<T>( (Class<T>)Class.forName( className ) );	
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public static <T> ReflectionFactory<T> fromRegistry( String name )
	{
		return new ReflectionFactory<T>( (Class<T>)DataRegistry.getClass( name ) );
	}

}
