package com.axe.game;

import com.axe.event.Event;

public class GameEvent 
{
	
	public static final Event<GameEventListener> Start = 
		new Event<>(0, GameEventListener.class);
	
	public static final Event<GameEventListener> DrawStart = 
		new Event<>(1, GameEventListener.class);
	
	public static final Event<GameEventListener> DrawEnd = 
		new Event<>(2, GameEventListener.class);
	
	public static final Event<GameEventListener> UpdateStart = 
		new Event<>(3, GameEventListener.class);
	
	public static final Event<GameEventListener> UpdateEnd = 
		new Event<>(4, GameEventListener.class);
	
	public static final Event<GameEventListener> Stop = 
		new Event<>(5, GameEventListener.class);
	
}