
package com.axe.util;

import java.util.Arrays;

public class Registry<T>
{
	private int identifier;
	private T[] items;

	public Registry( T... items )
	{
		this.items = items;
	}

	public int create()
	{
		return identifier++;
	}

	public void register( int identifier, T item )
	{
		if (identifier >= items.length)
		{
			items = Arrays.copyOf( items, identifier + 1 );
		}
		
		items[identifier] = item;
	}

	public <X extends T> X get( int identifier )
	{
		return (identifier >= items.length || identifier < 0 ? null : (X)items[identifier]);
	}

}
