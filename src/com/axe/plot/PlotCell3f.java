package com.axe.plot;

import com.axe.math.Vec3f;
import com.axe.math.Vec3i;

public class PlotCell3f implements Plot<Vec3i>
{

	private final Vec3f size = new Vec3f();
	private final Vec3f off = new Vec3f();
	private final Vec3f pos = new Vec3f();
	private final Vec3f dir = new Vec3f();

	private final Vec3i index = new Vec3i();
	
	private final Vec3f delta = new Vec3f();
	private final Vec3i sign = new Vec3i();
	private final Vec3f max = new Vec3f();
	
	private int limit;
	private int plotted;
	
	public PlotCell3f(float offx, float offy, float offz, float width, float height, float depth)
	{
		off.set( offx, offy, offz );
		size.set( width, height, depth );
	}
	
	public void plot(Vec3f position, Vec3f direction, int cells) 
	{
		limit = cells;
		
		pos.set( position );
		dir.normal( direction );
		
		delta.set( size );
		delta.div( dir );
		
		sign.x = (dir.x > 0) ? 1 : (dir.x < 0 ? -1 : 0);
		sign.y = (dir.y > 0) ? 1 : (dir.y < 0 ? -1 : 0);
		sign.z = (dir.z > 0) ? 1 : (dir.z < 0 ? -1 : 0);

		reset();
	}
	
	@Override
	public boolean next() 
	{
		if (plotted++ > 0) 
		{
			float mx = sign.x * max.x;
			float my = sign.y * max.y;
			float mz = sign.z * max.z;
			
			if (mx < my && mx < mz) 
			{
				max.x += delta.x;
				index.x += sign.x;
			}
			else if (mz < my && mz < mx) 
			{
				max.z += delta.z;
				index.z += sign.z;
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
		index.z = (int)Math.floor((pos.z - off.z) / size.z);
		
		float ax = index.x * size.x + off.x;
		float ay = index.y * size.y + off.y;
		float az = index.z * size.z + off.z;

		max.x = (sign.x > 0) ? ax + size.x - pos.x : pos.x - ax;
		max.y = (sign.y > 0) ? ay + size.y - pos.y : pos.y - ay;
		max.z = (sign.z > 0) ? az + size.z - pos.z : pos.z - az;
		max.div( dir );
	}

	@Override
	public void end()
	{
		plotted = limit + 1;
	}
	
	@Override
	public Vec3i get() 
	{
		return index;
	}
	
	public Vec3f actual(Vec3f out) {
		out.x = index.x * size.x + off.x;
		out.y = index.y * size.y + off.y;
		out.z = index.z * size.z + off.z;
		return out;
	}
	
	public Vec3f actual() {
		return actual( new Vec3f() );
	}

	public Vec3f size() {
		return size;
	}
	
	public void size(float w, float h, float d) {
		size.set(w, h, d);
	}
	
	public Vec3f offset() {
		return off;
	}
	
	public void offset(float x, float y, float z) {
		off.set(x, y, z);
	}
	
	public Vec3f position() {
		return pos;
	}
	
	public Vec3f direction() {
		return dir;
	}
	
	public Vec3i sign() {
		return sign;
	}
	
	public Vec3f delta() {
		return delta;
	}
	
	public Vec3f max() {
		return max;
	}
	
	public int limit() {
		return limit;
	}
	
	public int plotted() {
		return plotted;
	}
	
	
	
}
