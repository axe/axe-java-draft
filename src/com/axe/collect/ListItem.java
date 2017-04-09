package com.axe.collect;

public interface ListItem
{
	
	public boolean isLinked();

	public boolean isFor(Object originalValue);
	
	public void remove(Object originalValue);

	public void front(boolean very);
	
	public void last(boolean very);
	
	default void register(Object value)
	{
		if (value instanceof HasListItem)
		{
			((HasListItem)value).setListItem(this);
		}
	}
	
}
