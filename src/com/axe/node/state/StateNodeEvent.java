package com.axe.node.state;

import com.axe.event.Event;

public class StateNodeEvent 
{

	public static final Event<StateNodeListener> Exiting = 
		new Event<>( 0, StateNodeListener.class );

	public static final Event<StateNodeListener> Exit = 
		new Event<>( 1, StateNodeListener.class );

	public static final Event<StateNodeListener> Entering = 
		new Event<>( 2, StateNodeListener.class );

	public static final Event<StateNodeListener> Enter = 
		new Event<>( 3, StateNodeListener.class );
	
}
