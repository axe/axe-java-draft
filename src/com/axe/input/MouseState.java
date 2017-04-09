package com.axe.input;

import com.axe.math.Vec2i;

public class MouseState 
{
	
	public int x, y, dx, dy, sdx, sdy, wheel, wheelSign;
	
	public boolean inside, insideChange;
	
	public final Vec2i position = new Vec2i();
	public final Vec2i delta = new Vec2i();
	public final Vec2i sign = new Vec2i();
	
	public void reset(int mx, int my)
	{
		x = position.x = mx;
		y = position.y = my;
		inside = insideChange = false;
		clear();
	}
	
	public void clear()
	{
		dx = dy = delta.x = delta.y = 0;
		sdx = sdy = sign.x = sign.y = 0;
		wheel = wheelSign = 0;
	}
	
	public void accumulatePosition(int mx, int my)
	{
		delta.x = dx += (mx - x);
		delta.y = dy += (my - y);

		position.x = x = mx;
		position.y = y = my;
		
		sign.x = sdx = Integer.signum( dx );
		sign.y = sdy = Integer.signum( dy );
	}
	
	public void accumulateScroll(int ticks)
	{
		wheel += ticks;
		wheelSign = Integer.signum( wheel );
	}
	
	public void updateInside(int windowWidth, int windowHeight)
	{
		boolean currentInside = !(x < 0 || x >= windowWidth || y < 0 || y >= windowHeight);
		
		insideChange = (currentInside != inside);
		inside = currentInside;
	}
	
}