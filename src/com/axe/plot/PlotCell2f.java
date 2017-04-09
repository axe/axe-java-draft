package com.axe.plot;

import com.axe.math.Vec2f;
import com.axe.math.Vec2i;

public class PlotCell2f implements Plot<Vec2i>
{

	private final Vec2f size = new Vec2f();
	private final Vec2f off = new Vec2f();
	private final Vec2f pos = new Vec2f();
	private final Vec2f dir = new Vec2f();

	private final Vec2i index = new Vec2i();
	
	private final Vec2f delta = new Vec2f();
	private final Vec2i sign = new Vec2i();
	private final Vec2f max = new Vec2f();
	
	private int limit;
	private int plotted;
	
	public PlotCell2f(float offx, float offy, float width, float height)
	{
		off.set( offx, offy );
		size.set( width, height );
	}
	
	public void plot(Vec2f position, Vec2f direction, int cells) 
	{
		limit = cells;
		
		pos.set( position );
		dir.normal( direction );
		
		delta.set( size );
		delta.div( dir );
		
		sign.x = (dir.x > 0) ? 1 : (dir.x < 0 ? -1 : 0);
		sign.y = (dir.y > 0) ? 1 : (dir.y < 0 ? -1 : 0);
		
		reset();
	}
	
	@Override
	public boolean next() 
	{
		if (plotted++ > 0) 
		{
			float mx = sign.x * max.x;
			float my = sign.y * max.y;
			
			if (mx < my) 
			{
				max.x += delta.x;
				index.x += sign.x;
			}
			else 
			{
				max.y += delta.y;
				index.y += sign.y;
			}
		}
		return (plotted <= limit);
	}
	
	@Override
	public void reset() 
	{
		plotted = 0;
		
		index.x = (int)Math.floor((pos.x - off.x) / size.x);
		index.y = (int)Math.floor((pos.y - off.y) / size.y);
		
		float ax = index.x * size.x + off.x;
		float ay = index.y * size.y + off.y;

		max.x = (sign.x > 0) ? ax + size.x - pos.x : pos.x - ax;
		max.y = (sign.y > 0) ? ay + size.y - pos.y : pos.y - ay;		
		max.div( dir );
	}
	
	@Override
	public void end()
	{
		plotted = limit + 1;
	}
	
	@Override
	public Vec2i get() 
	{
		return index;
	}
	
	public Vec2f actual(Vec2f out) {
		out.x = index.x * size.x + off.x;
		out.y = index.y * size.y + off.y;
		return out;
	}
	
	public Vec2f actual() {
		return actual(new Vec2f());
	}
	
	public Vec2f size() {
		return size;
	}
	
	public void size(float w, float h) {
		size.set(w, h);
	}
	
	public Vec2f offset() {
		return off;
	}
	
	public void offset(float x, float y) {
		off.set(x, y);
	}
	
	public Vec2f position() {
		return pos;
	}
	
	public Vec2f direction() {
		return dir;
	}
	
	public Vec2i sign() {
		return sign;
	}
	
	public Vec2f delta() {
		return delta;
	}
	
	public Vec2f max() {
		return max;
	}
	
	public int limit() {
		return limit;
	}
	
	public int plotted() {
		return plotted;
	}
	
	
	
}
