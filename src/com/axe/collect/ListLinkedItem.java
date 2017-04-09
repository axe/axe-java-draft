package com.axe.collect;

import java.util.Objects;

import com.axe.core.Factory;

public class ListLinkedItem<T> implements ListItem 
{
	
	public static Factory<ListLinkedItem> FACTORY = () -> new ListLinkedItem();
	
	public ListLinkedItem<T> next;
	public ListLinkedItem<T> prev;
	public T value;

	public ListLinkedItem() 
	{
		this.next = this;
		this.prev = this;
	}
	
	public void set(T value)
	{
		this.value = value;
	}

	public void insertAfter(ListLinkedItem<T> after) 
	{
		after.next.prev = this;
		next = after.next;
		after.next = this;
		prev = after;
	}
	
	public void unlink()
	{
		next.prev = prev;
		prev.next = next;
		next = prev = this;
	}
	
	public void destroy()
	{
		if (isLinked())
		{
			ListLinkedItem<T> n = this.next;
			
			remove();
			
			n.destroy();
		}
	}
	
	public void remove()
	{
		unlink();
		value = null;
		
		ListLinked.POOL.free(this);
	}
	
	@Override
	public boolean isLinked()
	{
		return !(value == null || prev == this || next == this); 
	}
	
	@Override
	public boolean isFor(Object originalValue)
	{
		Objects.requireNonNull(originalValue);
		
		return value == originalValue;
	}
	
	@Override
	public void remove(Object originalValue) 
	{
		if (isFor(originalValue))
		{
			remove();
		}
	}

	@Override
	public void front(boolean very) 
	{
		if (!isLinked()) 
		{
			return;
		}
		
		ListLinkedItem<T> node = prev;
		
		if (very) 
		{
			while (node.value != null) 
			{
				node = node.prev;
			}
		}
		
		if (node != prev) 
		{
			unlink();
			insertAfter(node);
		}
	}

	@Override
	public void last(boolean very) 
	{
		if (!isLinked()) 
		{
			return;
		}
		
		ListLinkedItem<T> node = next;
		
		if (very) 
		{
			while (node.next.value != null) 
			{
				node = node.next;
			}
		}
		
		if (node != next) 
		{
			unlink();
			insertAfter(node);
		}
	}
	
}