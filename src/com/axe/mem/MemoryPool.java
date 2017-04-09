package com.axe.mem;

import java.util.Arrays;

import com.axe.core.Factory;
import com.axe.util.Array;


public class MemoryPool<T> implements Memory<T>
{

	private Factory<T> factory;
	private T[] pool;
	private int size;
	private int maximum;
	private int increase;

	public MemoryPool( T[] pool, Factory<T> factory, int maximum, int increase, boolean fill )
	{
		this.pool = pool;
		this.factory = factory;
		this.maximum = maximum;
		this.increase = increase;
		
		if ( fill ) {
			Array.allocate( pool, factory );
		}
	}
	
	public MemoryPool( int poolSize, Factory<T> factory, int maximum, int increase, boolean fill, T ... poolBase )
	{
		this.pool = Arrays.copyOf( poolBase, poolSize );
		this.factory = factory;
		this.maximum = maximum;
		this.increase = increase;
		
		if ( fill ) {
			Array.allocate( pool, factory );
		}
	}
	
	
	@Override
	public T alloc()
	{
		return (size == 0 ? factory.create() : pool[--size]);
	}

	@Override
	public void free( T item )
	{
		if (size < pool.length) {
			pool[size++] = item;
		} else if (size < maximum) {
			pool = Arrays.copyOf( pool, size + increase );
			pool[size++] = item;
 		}
	}
	
	@Override
	public int size()
	{
		return size;
	}
	
	@Override
	public int capacity()
	{
		return maximum;
	}
	
}
