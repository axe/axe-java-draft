package com.axe.event;

import com.axe.collect.ListItem;

public interface HasEvents 
{
	
	public EventMap getEvents();
	
	default public <L, S extends Event<L>> ListItem on(S event, L listener) 
	{
		return getEvents().on( event, listener );
	}
	
	default public <L, S extends Event<L>> void off(S event) 
	{
		getEvents().off( event );
	}
	
	default public <L, S extends Event<L>> boolean off(S event, L listener) 
	{
		return getEvents().off( event, listener );
	}
	
	default public <L, S extends Event<L>> L listeners(S event) 
	{
		return getEvents().listeners( event );
	}
	
}