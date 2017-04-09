package com.axe.collect;

import java.util.Objects;

import com.axe.core.Factory;

public class ListArrayItem implements ListItem
{
	
	public static Factory<ListArrayItem> FACTORY = () -> new ListArrayItem(); 
	
	public ListArray source;
	public int index;

	public ListArrayItem()
	{
		
	}
	
	public void set(ListArray source, int index)
	{
		this.source = source;
		this.index = index;
	}
	
	@Override
	public boolean isLinked() 
	{
		return source != null && source.items[ index ] == this;
	}
	
	@Override
	public boolean isFor(Object originalValue)
	{
		Objects.requireNonNull(originalValue);
		
		return isLinked() && originalValue == source.values[ index ];
	}

	@Override
	public void remove(Object originalValue) 
	{
		if (isFor(originalValue))
		{
			source.values[ index ] = null;
			source.items[ index ] = null;
			source.effectiveSize--;
			
			if (source.compact)
			{
				source.update();
			}
			
			ListArray.POOL.free(this);
		}
	}

	@Override
	public void front(boolean very) 
	{
		if (index > 0)
		{
			if (very)
			{
				while (index > 0)
				{
					source.swap( index, index - 1 );
				}
			}
			else
			{
				source.swap( index, index - 1 );
			}
		}
	}

	@Override
	public void last(boolean very) 
	{
		int last = source.size - 1;
		
		if (index < last)
		{
			if (very)
			{
				while (index < last)
				{
					source.swap( index, index + 1 );
				}
			}
			else
			{
				source.swap( index, index + 1 );
			}
		}
	}
	
}