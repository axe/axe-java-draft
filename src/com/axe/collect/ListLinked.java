package com.axe.collect;

import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.axe.mem.Memory;
import com.axe.mem.MemoryPool;

public class ListLinked<T> implements List<T>
{
	
	protected static final Memory<ListLinkedItem> POOL = new MemoryPool<>(128, ListLinkedItem.FACTORY, 1024, 16, false);
	
	public static <E> List<E> create() 
	{
		return new ListLinked<E>();
	}
	
	public final ListLinkedItem<T> head = new ListLinkedItem<>();

	public ListLinkedItem<T> add(T value) 
	{
		Objects.requireNonNull(value);
		
		ListLinkedItem<T> node = POOL.alloc(); 
		
		assert !node.isLinked();
		
		node.value = value;
		node.insertAfter(head.prev);
		node.register(value);
		
		return node;
	}

	@Override
	public void clear() 
	{
		head.next.destroy();
	}

	@Override
	public ListLinkedItem<T> find(T value, BiPredicate<T, T> equals) 
	{
		ListLinkedItem<T> node = head.next;
		ListLinkedItem<T> next = null;
		
		while (node != head) 
		{
			next = node.next;
			
			if (equals.test(node.value, value))
			{
				return node;
			}
			
			node = next;
		}
		
		return null;
	}

	@Override
	public int forEach(Predicate<T> predicate) 
	{
		ListLinkedItem<T> node = head.next;
		ListLinkedItem<T> next = null;
		int iterated = 0;
		
		while (node != head) 
		{
			next = node.next;
			
			if (predicate.test(node.value))
			{
				iterated++;
			}
			else
			{
				break;
			}
			
			node = next;
		}
		
		return iterated;
	}
	
	@Override
	public int forEach(BiPredicate<T, ListItem> predicate)
	{
		ListLinkedItem<T> node = head.next;
		ListLinkedItem<T> next = null;
		int iterated = 0;
		
		while (node != head) 
		{
			next = node.next;
			
			if (predicate.test(node.value, node))
			{
				iterated++;
			}
			else
			{
				break;
			}
			
			node = next;
		}
		
		return iterated;
	}

	@Override
	public int size() 
	{
		ListLinkedItem<T> node = head.next;
		int size = 0;
		
		while (node != head) 
		{
			size++;
			node = node.next;
		}
		
		return size;
	}
	
}