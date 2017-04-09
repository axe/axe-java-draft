package com.axe.mem;


public interface Memory<T>
{
	public T alloc();
	public void free(T item);
	public int size();
	public int capacity();
}
