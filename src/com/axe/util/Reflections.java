
package com.axe.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


public class Reflections
{

	public static <T> T[] getStaticFieldArray( Class<T> type, boolean exact, Class<?> from )
	{
		List<T> fieldList = getStaticFieldCollection( type, exact, from, new ArrayList<T>() );

		return fieldList.toArray( (T[])Array.newInstance( type, fieldList.size() ) );
	}

	public static <T, C extends Collection<T>> C getStaticFieldCollection( Class<T> type, boolean exact, Class<?> from, C destination )
	{
		try
		{
			List<Field> fieldList = getStaticFields( type, exact, from );

			for (Field f : fieldList)
			{
				destination.add( (T)f.get( null ) );
			}

			return destination;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	public static <T, M extends Map<String, T>> M getStaticFieldMap( Class<T> type, boolean exact, Class<?> from, M destination )
	{
		try
		{
			List<Field> fieldList = getStaticFields( type, exact, from );

			for (Field f : fieldList)
			{
				destination.put( f.getName(), (T)f.get( null ) );
			}

			return destination;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	public static <T, M extends Map<T, String>> M getStaticFieldMapInversed( Class<T> type, boolean exact, Class<?> from, M destination )
	{
		try
		{
			List<Field> fieldList = getStaticFields( type, exact, from );

			for (Field f : fieldList)
			{
				destination.put( (T)f.get( null ), f.getName() );
			}

			return destination;
		}
		catch (RuntimeException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new RuntimeException( e );
		}
	}

	public static List<Field> getStaticFields( Class<?> type, boolean exact, Class<?> from )
	{
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fieldArray = from.getFields();

		for (Field f : fieldArray)
		{
			if ((f.getModifiers() & Modifier.STATIC) == 0)
			{
				continue;
			}

			if (exact)
			{
				if (type != f.getType())
				{
					continue;
				}
			}
			else
			{
				if (!type.isAssignableFrom( f.getType() ))
				{
					continue;
				}
			}

			fieldList.add( f );
		}

		return fieldList;
	}

	public static List<Field> getFields( Class<?> from, int avoidModifiers )
	{
		List<Field> fieldList = new ArrayList<Field>();
		Field[] fieldArray = from.getFields();

		for (Field f : fieldArray)
		{
			if (avoidModifiers != 0 && (f.getModifiers() & avoidModifiers) != 0)
			{
				continue;
			}

			fieldList.add( f );
		}

		return fieldList;
	}

	public static Field[] getFieldArray( Class<?> from, int avoidModifiers )
	{
		List<Field> fields = getFields( from, avoidModifiers );
		
		return fields.toArray( new Field[ fields.size() ] );
	}

}
