package com.axe.input;

import java.util.Queue;

public interface KeyEngine 
{
	public static final float DEFAULT_PRESS_DELAY = 0.5f;
	public static final float DEFAULT_PRESS_INTERVAL = 0.1f;
	
	public KeyState getKey(Key key);

	public InputState getShift();

	public InputState getControl();

	public InputState getMeta();

	public InputState getCommand();

	public InputState getAlt();
	
	public Queue<KeyState> getQueue();
	
	public void setPressInterval(float pressInterval);
	
	public float getPressInterval();
	
	public void setPressDelay(float pressDelay);
	
	public float getPressDelay();
	
}