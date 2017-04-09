package com.axe.monitor;

import com.axe.event.HasEvents;

public interface MonitorSystem extends HasEvents
{

	public Monitor getPrimary();
	
	public Monitor[] getMonitors();
		
}
