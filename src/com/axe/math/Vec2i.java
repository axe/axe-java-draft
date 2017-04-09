package com.axe.math;

import com.axe.core.Attribute;
import com.axe.core.Factory;
import com.axe.math.calc.CalculatorVec2i;

public class Vec2i implements Attribute<Vec2i>
{
	
	public static final Factory<Vec2i> FACTORY = new Factory<Vec2i>() {
		public Vec2i create() {
			return new Vec2i();
		}
	};
	
	public static final Vec2i UP = new Vec2i(0, 1);
	public static final Vec2i DOWN = new Vec2i(0, -1);
	public static final Vec2i RIGHT = new Vec2i(1, 0);
	public static final Vec2i LEFT = new Vec2i(-1, 0);
	public static final Vec2i ONE = new Vec2i(1);
	public static final Vec2i ZERO = new Vec2i(0);
	
	public int x, y;

	public Vec2i() 
	{
	}

	public Vec2i(int x, int y) 
	{
		set(x, y);
	}

	public Vec2i(int v) 
	{
		x = y = v;
	}

	public Vec2i(Vec2i v) 
	{
		set(v);
	}
	
	public Vec2i(Vec2i v, float scale) 
	{
		set( v, scale );
	}
	
	public void set(Vec2i v, float scale) 
	{
		x = (int)(v.x * scale);
		y = (int)(v.y * scale);
	}
	
	public void set( int v ) 
	{
		x = y = v;
	}
	
	public void set(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}

	public float normal(int x, int y) 
	{
		set(x, y); 
		return normal();
	}
	
	public void tangent() 
	{
		int t = x;
		x = y;
		y = t;
	}
	
	public void angle(float radians, float magnitude) 
	{
		x = (int)(Math.cos( radians ) * magnitude);
		y = (int)(Math.sin( radians ) * magnitude);
	}
	
	public float angle() 
	{
		float a = (float)StrictMath.atan2( y, x );
		
		if ( a < 0) 
		{		
			a = Numbers.PI2 + a;
		}
		
		return a;
	}
	
	public float angleTo(Vec2i to)
	{
		float a = (float)StrictMath.atan2( to.y - y, to.x - x );
		
		if ( a < 0 ) 
		{
			a = Numbers.PI2 + a;
		}
		
		return a;
	}
	
	public void rotate( float cos, float sin ) 
	{
		int ox = x;
		int oy = y;
		x = (int)(cos * ox + sin * oy);
		y = (int)(cos * oy - sin * ox);
	}
	
	public void rotate( float angle ) 
	{
		rotate( (float)StrictMath.cos(angle), -(float)StrictMath.sin(angle) );
	}
	
	public int cross(Vec2i v) 
	{
		return (y * v.x) - (x * v.y);
	}
	
	public int cross(int vx, int vy) 
	{
		return (y * vx) - (x * vy);
	}
	
	public void clamp( int minX, int maxX, int minY, int maxY ) 
	{
		if ( x < minX ) x = minX;
		if ( x > maxX ) x = maxX;
		if ( y < minY ) y = minY;
		if ( y > maxY ) y = maxY;
	}
	
	public void average(Vec2i[] v) 
	{
		if (v.length > 0) {
			x = y = 0;
			for (int i = 0; i < v.length; i++) {
				add(v[i]);
			}
			divs(v.length);
		}
	}
	
	@Override
	public Vec2i get() 
	{
		return this;
	}

	@Override
	public Vec2i clone() 
	{
		return new Vec2i( x, y );
	}
	
	@Override
	public CalculatorVec2i getCalculator()
	{
		return CalculatorVec2i.INSTANCE;
	}
	
	@Override
	public String toString() 
	{
		return String.format("{%d, %d}", x, y);
	}
	
	@Override
	public boolean equals(Object o) 
	{
		if (o instanceof Vec2i) 
		{
			return equals((Vec2i)o);
		}
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(x + y * 46340);
	}

	public static Vec2i inter(Vec2i s, Vec2i e, float delta)
	{
		return new Vec2i(
			(int)((e.x - s.x) * delta) + s.x,
			(int)((e.y - s.y) * delta) + s.y);
	}
	
}