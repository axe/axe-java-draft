package com.axe.collect;

public class ListItemIgnored implements ListItem
{
	
	public static final ListItemIgnored INSTANCE = new ListItemIgnored();

	@Override
	public boolean isLinked() 
	{
		return false;
	}

	@Override
	public boolean isFor(Object originalValue) 
	{
		return false;
	}

	@Override
	public void remove(Object originalValue) 
	{
		
	}

	@Override
	public void front(boolean very) 
	{
		
	}

	@Override
	public void last(boolean very) 
	{
		
	}
	
}