package com.axe.collect;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import com.axe.mem.Memory;
import com.axe.mem.MemoryPool;
import com.axe.util.Array;

public class ListArray<T> implements List<T> 
{

	public static int DEFAULT_INCREASE = 16;
	public static int DEFAULT_INITIAL_CAPACITY = 16;
	
	protected static final Memory<ListArrayItem> POOL = new MemoryPool<>(128, ListArrayItem.FACTORY, 1024, 16, false);
	
	public static <E> ListArray<E> create(E ... initial)
	{
		return new ListArray<E>( DEFAULT_INITIAL_CAPACITY, DEFAULT_INCREASE, false, initial );
	}
	
	public static <E> ListArray<E> create(int capacity, E ... initial)
	{
		return new ListArray<E>( capacity, DEFAULT_INCREASE, false, initial );
	}
	
	public static <E> ListArray<E> create(int capacity, int increase, E ... initial)
	{
		return new ListArray<E>( capacity, increase, false, initial );
	}
	
	public static <E> ListArray<E> compacted(E ... initial)
	{
		return new ListArray<E>( 0, 1, true, initial );
	}
	
	public ListArrayItem[] items;
	public T[] values;
	public int size;
	public int increase;
	public int effectiveSize;
	public boolean compact;
	
	protected ListArray(int capacity, int increase, boolean compact, T ... initial)
	{
		this.items = new ListArrayItem[ capacity ];
		this.values = Arrays.copyOf( initial, Math.max( initial.length, capacity ) );
		this.size = initial.length;
		this.effectiveSize = initial.length;
		this.increase = increase;
		this.compact = compact;
		
		for (int i = 0; i < initial.length; i++)
		{
			items[ i ] = POOL.alloc();
			items[ i ].set( this, i );
			items[ i ].register( values[ i ] );
		}
	}
	
	@Override
	public ListItem add(T value) 
	{
		Objects.requireNonNull(value);
		
		ListArrayItem item = POOL.alloc();
		
		assert !item.isLinked();
		
		item.set( this, size );
		item.register( value );
		
		values = Array.put( values, size, value, increase );
		items = Array.put( items, size, item, increase ); 
		size++;
		effectiveSize++;
		
		return item;
	}

	@Override
	public ListItem find(T value, BiPredicate<T, T> equals) 
	{
		for (int i = 0; i < size; i++)
		{
			if (values[ i ] != null && equals.test(values[i], value))
			{
				return items[ i ];
			}
		}
		
		return null;
	}

	@Override
	public void clear() 
	{
		for (int i = 0; i < size; i++)
		{
			ListArrayItem item = items[ i ];
			
			if (item != null)
			{
				POOL.free( item );
				items[ i ] = null;
				values[ i ] = null;
			}
		}
		
		size = 0;
		effectiveSize = 0;
	}
	
	@Override
	public void update()
	{
		int live = 0;
		
		for (int i = 0; i < size; i++)
		{
			if (values[ i ] != null)
			{
				values[ live ] = values[ i ];
				items[ live ] = items[ i ];
				items[ live ].index = live;
				live++;
			}
		}
		
		size = effectiveSize = live;
		
		if (compact)
		{
			values = Arrays.copyOf(values, size);
		}
	}

	@Override
	public int size() 
	{
		return effectiveSize;
	}

	@Override
	public int forEach(Predicate<T> predicate) 
	{
		int iterated = 0;
		
		for (int i = 0; i < size; i++)
		{
			if ( values[ i ] != null )
			{
				if ( predicate.test( values[ i ] ) )
				{
					iterated++;
				}
				else
				{
					break;
				}
			}
		}
		
		return iterated;
	}
	
	@Override
	public int forEach(BiPredicate<T, ListItem> predicate)
	{
		int iterated = 0;
		
		for (int i = 0; i < size; i++)
		{
			if ( values[ i ] != null && items[ i ] != null )
			{
				if ( predicate.test( values[ i ], items[ i ] ) )
				{
					iterated++;
				}
				else
				{
					break;
				}
			}
		}
		
		return iterated;
	}
	
	public void swap(int j, int k)
	{
		ListArrayItem jitem = items[ j ];
		T jvalue = values[ j ];
		
		items[ j ] = items[ k ];
		values[ j ] = values[ k ];
		items[ k ] = jitem;
		values[ k ] = jvalue;
		
		items[ j ].index = j;
		items[ k ].index = k;
	}

}
