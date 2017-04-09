package com.axe.math;

import java.io.Serializable;

import com.axe.core.Attribute;
import com.axe.core.Factory;
import com.axe.math.calc.CalculatorVec2f;

public class Vec2f implements Attribute<Vec2f>, Vec<Vec2f>, Serializable
{
	
	private static final long serialVersionUID = 5839854881676932911L;

	public static Factory<Vec2f> FACTORY = new Factory<Vec2f>() {
		public Vec2f create() {
			return new Vec2f();
		}
	};
	
	public static final Vec2f UP = new Vec2f(0, 1);
	public static final Vec2f DOWN = new Vec2f(0, -1);
	public static final Vec2f RIGHT = new Vec2f(1, 0);
	public static final Vec2f LEFT = new Vec2f(-1, 0);
	public static final Vec2f ONE = new Vec2f(1);
	public static final Vec2f ZERO = new Vec2f(0);

	public float x, y;

	public Vec2f() 
	{
	}

	public Vec2f(float x, float y) 
	{
		set(x, y);
	}

	public Vec2f(float v) 
	{
		x = y = v;
	}

	public Vec2f(Vec2f v) 
	{
		set(v);
	}
	
	public Vec2f(Vec2f v, float scale) 
	{
		set( v, scale );
	}
	
	public void set(Vec2f v, float scale) 
	{
		x = v.x * scale;
		y = v.y * scale;
	}
	
	public void set(float x, float y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public float normal(float x, float y) 
	{
		set(x, y); 
		return normal();
	}
	
	/** Rotates 90 */
	public void tangent() {
		float t = x;
		x = -y;
		y = t;
	}
	
	public void turn(int times) 
	{
		turn(times, this);
	}
	
	public void turn(int times, Vec2f v) 
	{
		float ox = v.x;
		float oy = v.y;
		switch(times & 3) {
		case 1:
			x = -oy;
			y = +ox;
			break;
		case 2:
			x = -ox;
			y = -oy;
			break;
		case 3:
			x = +oy;
			y = -ox;			
			break;
		}
	}
	
	public void angle(float radians, float magnitude) 
	{
		x = (float)Math.cos(radians) * magnitude;
		y = (float)Math.sin(radians) * magnitude;
	}
	
	public float angle() 
	{
		float a = (float)StrictMath.atan2( y, x );
		
		if ( a < 0 ) 
		{
			a = Numbers.PI2 + a;
		}
		return a;
	}
	
	public float angleTo( Vec2f to ) 
	{
		float a = (float)StrictMath.atan2( to.y - y, to.x - x );
		
		if ( a < 0 ) 
		{
			a = Numbers.PI2 + a;
		}
		return a;
	}
	
	public void add(float vx, float vy) 
	{
		x += vx; 
		y += vy;
	}
	
	public void rotate( float cos, float sin ) 
	{
		float ox = x;
		float oy = y;
		x = ox * cos - oy * sin; 
		y = ox * sin + oy * cos;
	}
	
	public void rotate( float angle ) 
	{
		rotate( (float)StrictMath.cos(angle), -(float)StrictMath.sin(angle) );
	}
	
	// point is in front of this vector (1=front, 0=back)
	// public float dot(Vec2f v) 
	
	public float dot(float vx, float vy) 
	{
		return (x * vx) + (y * vy);
	}
	
	// point is on a side of this vector (0=on, 1=left, -1=right)
	public float cross(Vec2f v) 
	{
		return (y * v.x) - (x * v.y);
	}
	
	public float cross(float vx, float vy) 
	{
		return (y * vx) - (x * vy);
	}
	
	public void clamp( float minX, float maxX, float minY, float maxY ) 
	{
		if ( x < minX ) x = minX;
		if ( x > maxX ) x = maxX;
		if ( y < minY ) y = minY;
		if ( y > maxY ) y = maxY;
	}
	
	public void average(Vec2f[] v) 
	{
		if (v.length > 0) 
		{
			x = y = 0f;
			
			for (int i = 0; i < v.length; i++) {
				add(v[i]);
			}
			
			divs( v.length );
		}
	}
	
	public boolean isEqual(float vx, float vy) 
	{
		return (x == vx && y == vy);
	}
	
	@Override
	public Vec2f get() 
	{
		return this;
	}

	@Override
	public Vec2f clone() 
	{
		return new Vec2f(x, y);
	}

	@Override
	public String toString() 
	{
		return String.format("{%.2f, %.2f}", x, y);
	}

	@Override
	public final CalculatorVec2f getCalculator()
	{
		return CalculatorVec2f.INSTANCE;
	}
	
	@Override
	public int hashCode()
	{
		return (int)(x + y * 46340);
	}

	public static Vec2f inter(Vec2f s, Vec2f e, float delta)
	{
		return new Vec2f(
			(e.x - s.x) * delta + s.x,
			(e.y - s.y) * delta + s.y);
	}

	public Vec2f rotate(Vec2f n) 
	{
		rotate(n.x, n.y);
		return this;
	}
	
	public void rotater(Vec2f n) 
	{
		rotate(-n.x, -n.y);
	}
	
	// TODO below

	@Override
	public Vec2f rotate(Vec2f out, Vec2f cossin) 
	{
		return null;
	}

	@Override
	public Vec2f rotaten(Vec2f cossin) 
	{
		return null;
	}

	@Override
	public Vec2f unrotate(Vec2f cossin) 
	{
		return null;
	}

	@Override
	public Vec2f unrotate(Vec2f out, Vec2f cossin) 
	{
		return null;
	}

	@Override
	public Vec2f unrotaten(Vec2f cossin) 
	{
		return null;
	}
	
}