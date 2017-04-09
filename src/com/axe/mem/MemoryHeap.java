package com.axe.mem;

import com.axe.core.Factory;


public class MemoryHeap<T> implements Memory<T>
{
	
	private Factory<T> factory;

	public MemoryHeap( Factory<T> factory )
	{
		this.factory = factory;
	}
	
	@Override
	public T alloc()
	{
		return factory.create();
	}

	@Override
	public void free( T item )
	{
	}
	
	@Override
	public int size()
	{
		return 0;
	}
	
	@Override
	public int capacity()
	{
		return 0;
	}
	
}
