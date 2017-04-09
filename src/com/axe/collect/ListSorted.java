package com.axe.collect;

import java.util.Comparator;


public class ListSorted<T> extends ListArray<T> 
{
	
	public static <E> ListSorted<E> create(Comparator<E> comparator, E ... initial)
	{
		return new ListSorted<E>( comparator, DEFAULT_INITIAL_CAPACITY, DEFAULT_INCREASE, false, initial );
	}
	
	public static <E> ListSorted<E> create(Comparator<E> comparator, int capacity, E ... initial)
	{
		return new ListSorted<E>( comparator, capacity, DEFAULT_INCREASE, false, initial );
	}
	
	public static <E> ListSorted<E> create(Comparator<E> comparator, int capacity, int increase, E ... initial)
	{
		return new ListSorted<E>( comparator, capacity, increase, false, initial );
	}
	
	public static <E> ListSorted<E> compacted(Comparator<E> comparator, E ... initial)
	{
		return new ListSorted<E>( comparator, 0, 1, true, initial );
	}
	
	public Comparator<T> comparator;
	
	protected ListSorted(Comparator<T> comparator, int capacity, int increase, boolean compact, T ... initial)
	{
		super( capacity, increase, compact, initial );
		
		this.comparator = comparator;
	}
	
	@Override
	public ListItem add(T value) 
	{
		ListItem item = super.add( value );
		
		sort();
		
		return item;
	}
	
	@Override
	public void update()
	{
		super.update();
		
		sort();
	}
	
	public void sort()
	{
		ListArrayItem tempItem;
		T tempValue;
		
		for (int i = 1; i < size; i++)
		{
			tempItem = items[ i ];
			tempValue = values[ i ];
			
			int j = i - 1;
			
			while (j >= 0 && comparator.compare( values[ j ], tempValue ) > 0)
			{
				items[ j + 1 ] = items[ j ];
				values[ j + 1 ] = values[ j ];
				j--;
			}
			
			items[ j + 1 ] = tempItem;
			values[ j + 1 ] = tempValue;
		}
		
		for (int i = 0; i < size; i++)
		{
			items[ i ].index = i;
		}
	}

}
