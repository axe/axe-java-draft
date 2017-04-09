package com.axe.util;

import java.util.Arrays;


public class IntStack
{
	
	public static final int DEFAULT_INITIAL_CAPACITY = 16;
	
	private int size;
	private int[] stack;
	
	public IntStack( )
	{
		this( DEFAULT_INITIAL_CAPACITY );
	}
	
	public IntStack( int initialCapacity )
	{
		this.size = 0;
		this.stack = new int[ initialCapacity ];
	}
	
	public int peek()
	{
		return peek( -1 );
	}
	
	public int peek( int returnOnEmpty )
	{
		return ( size == 0 ? returnOnEmpty : stack[ size - 1 ] );
	}
	
	public int pop()
	{
		return pop( -1 );
	}
	
	public int pop( int returnOnEmpty )
	{
		return ( size == 0 ? returnOnEmpty : stack[ --size ] );
	}
	
	public void push( int x )
	{
		if ( size == stack.length )
		{
			stack = Arrays.copyOf( stack, size << 1 );
		}
		
		stack[ size++ ] = x;
	}
	
	public int get( int i )
	{
		return stack[ i ];
	}
	
	public boolean hasInts()
	{
		return ( size > 0 );
	}
	
	public int size()
	{
		return size;
	}
	
}
