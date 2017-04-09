package com.axe.input;

import java.util.ArrayDeque;
import java.util.Queue;

import com.axe.window.Window;


public abstract class AbstractMouseEngine implements MouseEngine 
{
	
	public static final int MAX_BUTTONS = 3;

	public InputState[] states;
	public MouseState mouse;
	public Queue<MouseButtonState> queue;
	public boolean grabbed;
	public boolean hidden;
	public float pressDelay;
	public float pressInterval;
	
	public AbstractMouseEngine()
	{
		this.states = new InputState[ MAX_BUTTONS ];
		this.mouse = new MouseState();
		
		for (int i = 0; i < MAX_BUTTONS; i++)
		{
			this.states[i] = new InputState(i);
		}
		
		this.queue = new ArrayDeque<>();
		
		this.pressDelay = DEFAULT_PRESS_DELAY;
		this.pressInterval = DEFAULT_PRESS_INTERVAL;
	}
	
	public void init(long startTime)
	{
		for (int i = 0; i < states.length; i++)
		{
			states[ i ].changeTime = startTime;
		}
	}
	
	public void tick(long currentTime)
	{
		for (int i = 0; i < states.length; i++)
		{
			states[ i ].tick( currentTime, pressDelay, pressInterval );
		}
	}

	public void queueButtonState(int button, boolean down, long inputTime, Window inputWindow)
	{				
		MouseButtonState state = new MouseButtonState( button );
		
		state.setDown( down, inputTime, inputWindow );
		state.x = inputWindow.getMouse().x;
		state.y = inputWindow.getMouse().y;
		
		queue.offer( state );
	}
	
	@Override
	public InputState getButton(int button) 
	{
		return states[ button ];
	}

	@Override
	public MouseState getMouse() 
	{
		return mouse;
	}
	
	@Override
	public Queue<MouseButtonState> getQueue()
	{
		return queue;
	}

	@Override
	public boolean isGrabbed() 
	{
		return grabbed;
	}
	
	@Override
	public boolean isHidden()
	{
		return hidden;
	}

	@Override
	public void setPressInterval(float pressInterval) 
	{
		this.pressInterval = pressInterval;
	}

	@Override
	public float getPressInterval() 
	{		
		return pressInterval;
	}

	@Override
	public void setPressDelay(float pressDelay) 
	{
		this.pressDelay = pressDelay;
	}

	@Override
	public float getPressDelay() 
	{
		return pressDelay;
	}

}
