package com.axe.input;

import java.util.Queue;

public interface MouseEngine 
{
	
	public static final float DEFAULT_PRESS_DELAY = 0.5f;
	public static final float DEFAULT_PRESS_INTERVAL = 0.1f;

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int MIDDLE = 2;

	public InputState getButton(int button);

	public MouseState getMouse();
	
	public Queue<MouseButtonState> getQueue();
	
	public void setGrabbed(boolean grabbed);

	public boolean isGrabbed();
	
	public void setHidden(boolean hidden);

	public boolean isHidden();

	public void setPressInterval(float pressInterval);

	public float getPressInterval();

	public void setPressDelay(float pressDelay);

	public float getPressDelay();

}