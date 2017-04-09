package com.axe.monitor;

import com.axe.event.Event;

public class MonitorEvent 
{

	public static final Event<MonitorEventListener> Connected = 
		new Event<>(0, MonitorEventListener.class);

	public static final Event<MonitorEventListener> Disconnected = 
		new Event<>(1, MonitorEventListener.class);
	
}
