package com.axe.event;

public class Event<L> {
	public int id;
	public Class<L> listenerClass;

	public Event(int id, Class<L> listenerClass) {
		this.id = id;
		this.listenerClass = listenerClass;
	}
}