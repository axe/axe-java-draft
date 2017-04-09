package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorRect2i;

public class Rect2i implements Attribute<Rect2i>
{
	
	public int x, y, w, h;

	public Rect2i() 
	{
	}

	public Rect2i(Rect2i r) 
	{
		set(r);
	}

	public Rect2i(int x, int y) 
	{
		set(x, y);
	}
	
	public Rect2i(Vec2i p, Vec2i size)
	{
		set( p.x, p.y, size.x, size.y );
	}

	public Rect2i(int x, int y, int width, int height) 
	{
		set(x, y, width, height);
	}

	public void set(int x, int y) 
	{
		set(x, y, 0, 0);
	}
	
	public void set(int x, int y, int width, int height) 
	{
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
	}
	
	public boolean contains(int px, int py) 
	{
		return (px > x && py > y && px < x + w && py < y + h);
	}

	public boolean isNegative() 
	{
		return w < 0 || h < 0;
	}

	public int area() 
	{
		return w * h;
	}

	public int left() 
	{
		return x;
	}
	
	public int right() 
	{
		return x + w;
	}
	
	public int top() 
	{
		return y;
	}
	
	public int bottom() 
	{
		return y + h;
	}
	
	public int cx() 
	{
		return x + (w >> 1);
	}
	
	public int cy() 
	{
		return y + (h >> 1);
	}
	
	public int dx(float d) 
	{
		return x + (int)(w * d);
	}
	
	public int dy(float d) 
	{
		return y + (int)(h * d);
	}

	@Override
	public Rect2i get()
	{
		return this;
	}
	
	@Override
	public Rect2i clone() 
	{
		return new Rect2i( x, y, w, h );
	}
	
	@Override
	public CalculatorRect2i getCalculator()
	{
		return CalculatorRect2i.INSTANCE;
	}
	
	@Override
	public String toString() 
	{
		return "{x="+x+",y="+y+",w="+w+",h="+h+"}";
	}
	
	@Override
	public int hashCode()
	{
		return (int)(x + y * 215 + w * 46225 + h * 9938375);
	}

}
