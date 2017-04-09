package com.axe.event;

import java.util.Arrays;

import com.axe.collect.ListItem;

public class EventMap implements HasEvents 
{
	
	private EventListenerList[] lists = {};

	public EventMap getEvents() 
	{
		return this;
	}

	public <L, S extends Event<L>> ListItem on(S event, L listener) 
	{
		return getList(event).add(listener);
	}

	public <L, S extends Event<L>> L listeners(S event) 
	{
		return getList(event).proxy;
	}

	public <L, S extends Event<L>> void off(S event) 
	{
		getList(event).clear();
	}

	public <L, S extends Event<L>> boolean off(S event, L listener) 
	{
		return getList(event).remove(listener);
	}

	protected <L, S extends Event<L>> EventListenerList<L> getList(S event) 
	{
		if (lists.length <= event.id) 
		{
			lists = Arrays.copyOf(lists, event.id + 1);
		}
		
		if (lists[event.id] == null) 
		{
			lists[event.id] = new EventListenerList<>(event.listenerClass);
		}
		
		return lists[event.id];
	}
	
}