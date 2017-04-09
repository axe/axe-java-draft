package com.axe.math;

import com.axe.core.Attribute;
import com.axe.math.calc.CalculatorRect2f;

public class Rect2f implements Attribute<Rect2f>
{
	
	public float x, y, w, h;
	
	public Rect2f() 
	{
	}
	
	public Rect2f(Rect2f r) 
	{
		set(r);
	}
	
	public Rect2f(float x, float y) 
	{
		set(x, y);
	}
	
	public Rect2f(float x, float y, float width, float height) 
	{
		set(x, y, width, height);
	}
	
	public void set(float x, float y) 
	{
		set(x, y, 0, 0);
	}
	
	public void set(float x, float y, float width, float height) 
	{
		this.x = x;
		this.y = y;
		this.w = width;
		this.h = height;
	}
	
	public float area() 
	{
		return w * h;
	}

	public boolean isNegative() 
	{
		return w < 0 || h < 0;
	}

	@Override
	public Rect2f get() 
	{
		return this;
	}
	
	@Override
	public Rect2f clone() 
	{
		return new Rect2f( x, y, w, h );
	}
	
	@Override
	public CalculatorRect2f getCalculator()
	{
		return CalculatorRect2f.INSTANCE;
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
