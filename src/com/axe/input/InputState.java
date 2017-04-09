package com.axe.input;

import com.axe.window.Window;

public class InputState 
{
	
	public Window window;
	public final int input;
	public boolean down;
	public int frames;
	public long time;
	public float seconds;
	public int presses;
	public boolean press;
	public long changeTime;
	
	public InputState(int input) 
	{
		this.input = input;
	}

	public boolean isDown() 
	{
		return down && frames == 0;
	}

	public boolean isUp() 
	{
		return !down && frames == 0;
	}

	public boolean isPress() 
	{
		return press;
	}

	public boolean isChange() 
	{
		return frames == 0;
	}
		
	public void setDown(boolean isDown, long inputTime, Window inputWindow)
	{
		if (down != isDown)
		{
			frames = 0;
			time = 0;
			seconds = 0;
			down = isDown;
			press = isDown;
			presses = isDown ? 1 : 0;
			changeTime = inputTime;
		}
		
		window = inputWindow;
	}
	
	public void tick(long currentTime, float delay, float interval)
	{
		time = (currentTime - changeTime);
		seconds = time * 0.001f;
		press = false;
		
		if (down)
		{
			int currentPresses = (int)Math.max(0, (seconds - delay) / interval) + 1;
			
			press = (currentPresses != presses);
			presses = currentPresses;
		}
	}
	
}
